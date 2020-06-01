package dev.daniellavoie.bosh.client.webflux.cli;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
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

import dev.daniellavoie.bosh.client.model.DirectorInfo;
import dev.daniellavoie.bosh.client.webflux.util.JacksonUtil;
import reactor.core.publisher.Mono;

public class BoshEnvironmentClient {
	private static final Logger LOGGER = LoggerFactory.getLogger(BoshEnvironmentClient.class);

	private final BoshCli boshCli;

	public BoshEnvironmentClient(BoshCli boshCli) {
		this.boshCli = boshCli;
	}

	private static String calcSHA1(File file) {
		try {
			byte[] buffer = new byte[8192];
			int count;
			MessageDigest digest;
			digest = MessageDigest.getInstance("SHA-256");
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
			while ((count = bis.read(buffer)) > 0) {
				digest.update(buffer, 0, count);
			}
			bis.close();

			byte[] hash = digest.digest();

			return new String(Base64.getEncoder().encode(hash));
		} catch (NoSuchAlgorithmException | IOException e) {
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
			Files.copy(new ByteArrayInputStream(bytes), Path.of(path), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private String readFile(String filePath) {
		try {
			return Files.readString(Path.of(filePath));
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

	public Mono<DirectorInfo> createEnvironment(EnvironmentRequest request, Consumer<String> stdOutConsumer,
			Consumer<String> stdErrConsumer) {
		return environmentCommand("create-env", request, stdOutConsumer, stdErrConsumer)

				.flatMap(commandResult -> Mono.just(new DirectorInfo(request.getDirectorConfig().getIp(),
						commandResult.getDirectorCredentials(), commandResult.getDirectorState())));
	}

	public Mono<Void> deleteEnvironment(EnvironmentRequest request, Consumer<String> stdOutConsumer,
			Consumer<String> stdErrConsumer) {
		return environmentCommand("delete-env", request, stdOutConsumer, stdErrConsumer).then();
	}

	private Mono<EnvironmentCommandResult> environmentCommand(String commandType, EnvironmentRequest request,
			Consumer<String> stdOutConsumer, Consumer<String> stdErrConsumer) {
		var directorCredentialsFile = new File(request.getStateDir() + "/director-credentials.yml");
		var directorStateFile = new File(request.getStateDir() + "/director-state.json");

		Optional.ofNullable(request.getExistingDirectorInfo())
				.flatMap(directorInfo -> Optional.ofNullable(directorInfo.getDirectorCredentials()))
				.ifPresent(directorCredentials -> copyBytes(directorCredentials.getBytes(),
						directorCredentialsFile.getPath()));
		Optional.ofNullable(request.getExistingDirectorInfo())
				.flatMap(directorInfo -> Optional.ofNullable(directorInfo.getDirectorState()))
				.ifPresent(directorState -> copyBytes(directorState.getBytes(), directorStateFile.getPath()));

		traceFileSha256(directorCredentialsFile);
		traceFileSha256(directorStateFile);

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

		String arguments = "--tty " + commandType + " "

				+ request.getStateDir() + "/" + request.getManifest() + " "

				+ "--state " + directorStateFile.getPath() + " "

				+ "--vars-store " + directorCredentialsFile.getPath() + " "

				+ (request.getOperators().size() != 0 ? format(request.getStateDir(), request.getOperators()) : "")
				+ " "

				+ (request.getVariables().size() != 0 ? format(request.getVariables()) + " " : "")

				+ (variableFilesNames.size() != 0 ? formatVarFiles(variableFilesNames) + " " : "")

				+ format(directorVariables) + " "

				+ "-n";

		return boshCli.runCliCommand(arguments, stdOutConsumer::accept, stdErrConsumer::accept)

				.doOnSuccess(voidValue -> LOGGER.info("Resulting state file sha256 {} : {}",
						directorStateFile.getPath(), calcSHA1(directorStateFile)))

				.doOnSuccess(voidValue -> LOGGER.info("Resulting credentials file sha256 {} : {}",
						directorCredentialsFile.getPath(), calcSHA1(directorCredentialsFile)))

				.doOnTerminate(() -> cleanVarFiles(variableFilesNames))

				.thenReturn(new EnvironmentCommandResult(readFile(directorCredentialsFile.getPath()),
						directorStateFile.exists() ? readFile(directorStateFile.getPath()) : null));
	}

	private String format(Map<String, String> variables) {
		return variables.entrySet().stream().map(entry -> "-v " + entry.getKey() + "=" + entry.getValue())
				.collect(Collectors.joining(" "));
	}

	private String format(String stateDir, List<String> operators) {
		return operators.stream().map(operator -> "-o " + stateDir + "/" + operator).collect(Collectors.joining(" "));
	}

	private String formatVarFiles(Map<String, String> variableFilesNames) {
		return variableFilesNames.entrySet().stream()
				.map(entry -> "--var-file " + entry.getKey() + "=" + entry.getValue()).collect(Collectors.joining(" "));
	}

	private void traceFileSha256(File file) {
		if (file.exists()) {
			LOGGER.info("Sha256 from credential file {} : {}", file.getPath(), calcSHA1(file));
		}
	}
}
