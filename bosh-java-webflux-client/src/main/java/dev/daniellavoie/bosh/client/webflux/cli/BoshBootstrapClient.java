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

		runProcess(cliPath + " environments", log -> LOGGER.info("Test bosh cli environment event : {}", log),
				log -> LOGGER.info("Test bosh cli environment error : {}", log)).block();
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

	public Mono<DirectorInfo> createEnvironment(CreateEnvironmentRequest request, Consumer<String> stdOutConsumer,
			Consumer<String> stdErrConsumer) {
		String directorCredentialsFile = request.getStateDir() + "/director-credentials.yml";
		String directorStateFile = request.getStateDir() + "/director-state.yml";

		Optional.ofNullable(request.getExistingDirectorInfo().getDirectorCredentials())
				.ifPresent(directorCredentials -> copyBytes(JacksonUtil.writeYaml(directorCredentials).getBytes(),
						directorCredentialsFile));
		Optional.ofNullable(request.getExistingDirectorInfo().getDirectorState())
				.ifPresent(directorState -> copyBytes(directorState.getBytes(), directorCredentialsFile));

		copyFile(request.getManifest(), request.getStateDir());

		request.getOperators().stream().forEach(operator -> copyFile(operator, request.getStateDir()));

		var variableFileIndex = new AtomicInteger();
		var variableFilesNames = request.getVariableFiles().entrySet().stream().collect(Collectors.toMap(Entry::getKey,
				entry -> request.getStateDir() + "/var-files/" + variableFileIndex.getAndIncrement() + ".var"));

		request.getVariableFiles().entrySet().stream()
				.forEach(entry -> copyVariableFile(variableFilesNames.get(entry.getKey()), entry.getValue()));

		var directorVariables = Map.of("director_name", request.getEnvironmentName(), "internal_ip",
				request.getDirectorConfig().getIp(), "internal_gw", request.getDirectorConfig().getGateway(),
				"internal_cidr", request.getDirectorConfig().getCidr());

		String commandLine = cliPath + " create-env " + request.getStateDir() + "/" + request.getManifest()
				+ " \n  --state " + directorStateFile + " \n  --vars-store " + directorCredentialsFile + " \n  "
				+ (request.getOperators().size() != 0 ? format(request.getStateDir(), request.getOperators()) + " \n  "
						: "")
				+ (request.getVariables().size() != 0 ? format(request.getVariables()) + " \n  " : "")
				+ (variableFilesNames.size() != 0 ? formatVarFiles(variableFilesNames) + " \n  " : "")
				+ format(directorVariables) + " \n  -n";

		LOGGER.info("Executing command \n\n{}", commandLine);

		return runProcess(commandLine, stdOutConsumer::accept, stdErrConsumer::accept)

				.then(Mono.defer(() -> Mono.just(buildDirectorCredentials(request.getEnvironmentName(),
						request.getDirectorConfig(), directorCredentialsFile, directorStateFile))))

				.doOnTerminate(() -> cleanVarFiles(variableFilesNames));
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

	private Mono<Void> runProcess(String commandLine, Consumer<String> stdSink, Consumer<String> errSink) {
		try {
			var errorsBuffer = new ArrayList<String>();

			Process process = Runtime.getRuntime().exec(commandLine);

			Flux.fromStream(new BufferedReader(new InputStreamReader(process.getInputStream())).lines())

					.doOnNext(stdSink::accept)

					.doOnError(throwable -> LOGGER.error("Failed to process output from process.", throwable))

					.subscribeOn(Schedulers.newSingle("create-env-std"))

					.log()

					.subscribe();

			Flux.fromStream(new BufferedReader(new InputStreamReader(process.getErrorStream())).lines())

					.doOnNext(errSink::accept)

					.doOnNext(errorsBuffer::add)

					.doOnError(throwable -> LOGGER.error("Failed to process error output from process.", throwable))

					.subscribeOn(Schedulers.newSingle("create-env-err"))

					.log()

					.subscribe();

			return Mono.fromFuture(process.onExit())
					.flatMap(completedProcess -> completedProcess.exitValue() == 0 ? Mono.empty()
							: Mono.error(new RuntimeException("Failed to update Bosh environment. Cause : \n"
									+ errorsBuffer.stream().collect(Collectors.joining("\n")))));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

//	private Mono<Void> uploadDirectorCredentials(DirectorInfo directorInfo) {
//		var credhubClient = CredhubClientFactory.newCredHubClient("https://" + directorInfo.getEnvironment() + ":8433",
//				"credhub-admin", directorInfo.getDirectorCredentials().getCredhubAdminClientSecret(),
//				"https://" + directorInfo.getEnvironment() + ":8443/oauth/token",
//				directorInfo.getDirectorCredentials().getCredhubCa().getCa().getBytes());
//
//		credhubClient.credentials().generate(
//				CertificateParametersRequest.builder().name(new SimpleCredentialName(""))
//						.parameters(CertificateParameters.builder().certificateAuthority(true)
//								.certificateAuthorityCredential(
//										directorInfo.getDirectorCredentials().getCredhubCa().getCa())
//								.build())
//						.build(),
//				CredentialType.CERTIFICATE.getClass());
//	}
}
