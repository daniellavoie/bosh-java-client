package dev.daniellavoie.bosh.client.webflux.cli;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import dev.daniellavoie.bosh.client.model.DirectorConfig;
import dev.daniellavoie.bosh.client.model.DirectorInfo;
import dev.daniellavoie.bosh.client.webflux.util.ClasspathUtil;

public class BoshEnvironmentClientTest {
	private final BoshEnvironmentClient boshEnvironmentClient = new BoshEnvironmentClient(new MockedBoshCli());

	@Test
	public void createNewEnvironment() throws IOException {
		var stdOutputs = new ArrayList<>();
		var errOutputs = new ArrayList<>();

		var environment = "192.168.50.6";
		Path stateDirPath = Files.createTempDirectory("state-dir");

		var request = new EnvironmentRequest("unit-test-environment", stateDirPath.toString(), false,
				new DirectorConfig(environment, "192.168.50.0/24", "192.168.50.1"), null, "manifest",
				List.of("environment/operators/operator-1.yml", "environment/operators/operator-2.yml"),
				Map.of("var-1", "var-2"), Map.of("var-file-1", "var-file-1", "var-file-2", "var-file-2"));

		var expectedDirectorInfo = buildDirectorConfig(environment);

		Files.write(stateDirPath.resolve("director-credentials.yml"),
				expectedDirectorInfo.getDirectorCredentials().getBytes(), StandardOpenOption.CREATE_NEW);
		Files.write(stateDirPath.resolve("director-state.json"), expectedDirectorInfo.getDirectorState().getBytes(),
				StandardOpenOption.CREATE_NEW);

		var directorInfo = boshEnvironmentClient.createEnvironment(request, stdOutputs::add, errOutputs::add).block();

		Assertions.assertEquals(expectedDirectorInfo.getEnvironment(), directorInfo.getEnvironment());
		Assertions.assertEquals(expectedDirectorInfo.getDirectorCredentials(), directorInfo.getDirectorCredentials());
		Assertions.assertEquals(expectedDirectorInfo.getEnvironment(), directorInfo.getEnvironment());
	}

	private DirectorInfo buildDirectorConfig(String environment) {
		return new DirectorInfo(environment, ClasspathUtil.readContent("environment/credentials.yml"),
				ClasspathUtil.readContent("environment/state.json"));
	}
}
