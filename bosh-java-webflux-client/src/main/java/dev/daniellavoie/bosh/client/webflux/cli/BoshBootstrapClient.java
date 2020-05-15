package dev.daniellavoie.bosh.client.webflux.cli;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import dev.daniellavoie.bosh.client.model.DirectorConfig;
import dev.daniellavoie.bosh.client.model.DirectorCredentials;
import dev.daniellavoie.bosh.client.model.DirectorInfo;
import dev.daniellavoie.bosh.client.webflux.util.JacksonUtil;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class BoshBootstrapClient {
	private static final Logger LOGGER = LoggerFactory.getLogger(BoshBootstrapClient.class);

	private final String cliPath;

	public BoshBootstrapClient(String cliPath) {
		this.cliPath = cliPath;

		LOGGER.info("Testing Bosh CLI.");

		try {
			runProcess(Runtime.getRuntime().exec(cliPath + " environments"),
					log -> LOGGER.info("Test bosh cli environment event : {}", log)).block();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private DirectorInfo buildDirectorCredentials(String environmentName, DirectorConfig directorConfig,
			String directorCredentialsFile, String directorStateFile) {
		try {
			return new DirectorInfo(directorConfig.getIp(),
					JacksonUtil.readYaml(new File(directorCredentialsFile), DirectorCredentials.class),
					Files.lines(Paths.get(directorCredentialsFile)).collect(Collectors.joining("\n")));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void cleanFileSilently(String varFile) {
		try {
			Files.deleteIfExists(Path.of(varFile));
		} catch (IOException e) {
			LOGGER.error("Could not clean up var file " + varFile, e);
		}
	}

	private void cleanVarFiles(Map<String, String> variableFilesNames) {
		variableFilesNames.entrySet().stream().map(Entry::getValue).forEach(this::cleanFileSilently);
	}

	private void copyFile(String filepath, String stateDir) {
		try {
			Path filePath = Path.of(stateDir, filepath);

			Files.createDirectories(filePath.getParent());

			Files.copy(new BufferedInputStream(new ClassPathResource(filepath).getInputStream()), filePath,
					StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void copyBytes(byte[] bytes, String path) {
		try {
			Files.copy(new ByteArrayInputStream(bytes), Path.of(path));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void copyVariableFile(String variableFile, String variableValue) {
		try {
			Path filePath = Path.of(variableFile);

			Files.createDirectories(filePath.getParent());

			Files.copy(new ByteArrayInputStream(variableValue.getBytes()), filePath,
					StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public Mono<DirectorInfo> createEnvironment(String environmentName, String stateDir, DirectorConfig directorConfig,
			DirectorInfo existingDirectorInfo, String manifest, List<String> operators, Map<String, String> variables,
			Map<String, String> variableFiles) {
		String directorCredentialsFile = stateDir + "/director-credentials.yml";
		String directorStateFile = stateDir + "/director-state.yml";

		try {
			copyFile(manifest, stateDir);

			operators.stream().forEach(operator -> copyFile(operator, stateDir));

			var variableFileIndex = new AtomicInteger();
			var variableFilesNames = variableFiles.entrySet().stream().collect(Collectors.toMap(Entry::getKey,
					entry -> stateDir + "/var-files/" + variableFileIndex.getAndIncrement() + ".var"));

			variableFiles.keySet().stream().forEach(variableKey -> copyVariableFile(variableFilesNames.get(variableKey),
					variableFiles.get(variableKey)));

			Optional.ofNullable(existingDirectorInfo.getDirectorCredentials())
					.ifPresent(directorCredentials -> copyBytes(JacksonUtil.write(directorCredentials).getBytes(),
							directorCredentialsFile));
			Optional.ofNullable(existingDirectorInfo.getDirectorState())
					.ifPresent(directorState -> copyBytes(directorState.getBytes(), directorStateFile));

			var directorVariables = Map.of("director_name", environmentName, "internal_ip", directorConfig.getIp(),
					"internal_gw", directorConfig.getGateway(), "internal_cidr", directorConfig.getCidr());

			String commandLine = cliPath + " create-env " + stateDir + "/" + manifest + " \n  --state "
					+ directorStateFile + " \n  --vars-store " + directorCredentialsFile + " \n  "
					+ (operators.size() != 0 ? format(stateDir, operators) + " \n  " : "")
					+ (variables.size() != 0 ? format(variables) + " \n  " : "")
					+ (variableFilesNames.size() != 0 ? formatVarFiles(variableFilesNames) + " \n  " : "")
					+ format(directorVariables) + " \n  -n";

			LOGGER.info("Executing command \n\n{}", commandLine);

			return runProcess(Runtime.getRuntime().exec(commandLine),
					log -> LOGGER.info("Create environment update : {}", log))

							.then(Mono.defer(() -> Mono.just(buildDirectorCredentials(environmentName, directorConfig,
									directorCredentialsFile, directorStateFile))))

							.doOnTerminate(() -> cleanVarFiles(variableFilesNames));
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			cleanFileSilently(directorCredentialsFile);
			cleanFileSilently(directorStateFile);
		}
	}

	private String format(Map<String, String> variables) {
		return variables.entrySet().stream().map(entry -> "-v " + entry.getKey() + "=" + entry.getValue())
				.collect(Collectors.joining(" \n  "));
	}

	private String format(String stateDir, List<String> operators) {
		return operators.stream().map(operator -> "-o " + stateDir + "/" + operator)
				.collect(Collectors.joining(" \n  "));
	}

	private String formatVarFiles(Map<String, String> variableFilesNames) {
		return variableFilesNames.entrySet().stream()
				.map(entry -> "--var-file " + entry.getKey() + "=" + entry.getValue())
				.collect(Collectors.joining(" \n  "));
	}

	private Mono<Void> runProcess(Process process, Consumer<String> progressSink) {
		var errorsBuffer = new ArrayList<String>();

		Flux.fromStream(new BufferedReader(new InputStreamReader(process.getInputStream())).lines())

				.doOnNext(progressSink::accept)

				.doOnError(throwable -> LOGGER.error("Failed to process output from process.", throwable))

				.subscribeOn(Schedulers.elastic())

				.log()

				.subscribe();

		Flux.fromStream(new BufferedReader(new InputStreamReader(process.getErrorStream())).lines())

				.doOnNext(errorsBuffer::add)

				.doOnError(throwable -> LOGGER.error("Failed to process error output from process.", throwable))

				.subscribeOn(Schedulers.elastic())

				.log()

				.subscribe();

		return Mono.fromFuture(process.onExit())
				.flatMap(completedProcess -> completedProcess.exitValue() == 0 ? Mono.empty()
						: Mono.error(new RuntimeException("Failed to update Bosh environment. Cause : \n"
								+ errorsBuffer.stream().collect(Collectors.joining("\n")))));
	}
}
