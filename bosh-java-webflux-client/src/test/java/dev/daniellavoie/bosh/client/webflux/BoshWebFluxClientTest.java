package dev.daniellavoie.bosh.client.webflux;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import dev.daniellavoie.bosh.client.api.UpdateConfigRequest;
import dev.daniellavoie.bosh.client.api.UploadReleaseRequest;
import dev.daniellavoie.bosh.client.api.UploadStemcellRequest;
import dev.daniellavoie.bosh.client.model.Config;
import dev.daniellavoie.bosh.client.model.Deployment;
import dev.daniellavoie.bosh.client.model.DirectorConfig;
import dev.daniellavoie.bosh.client.model.DirectorInfo;
import dev.daniellavoie.bosh.client.model.Task;
import dev.daniellavoie.bosh.client.webflux.cli.BoshBootstrapClient;

@SpringBootTest
public class BoshWebFluxClientTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(BoshWebFluxClientTest.class);

	private static final String DEPLOYMENT_NAME = "pinot-cluster";

	final ObjectMapper OBJECTMAPPER = new ObjectMapper(new YAMLFactory())
			.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE).findAndRegisterModules();

	private BoshBootstrapClient boshBootstrapClient = new BoshBootstrapClient("/usr/local/bin/bosh");
	private BoshWebFluxClient boshClient;

	private String getStateDir(String environmentName) {
		return System.getProperty("user.home") + "/.bosh-env/" + environmentName;
	}

	private void cleanUpDeployment() {
		LOGGER.info("Cleaning up deployment {}.", DEPLOYMENT_NAME);

		Deployment deployment = getBoshClient().getDeployment("pinot-cluster").block();

		if (deployment == null) {
			LOGGER.info("Deployment {} coud not be found. Skipping cleanup.", DEPLOYMENT_NAME);

			return;
		}
		int taskId = getBoshClient().deleteDeployment("pinot-cluster").block();

		Task deleteDeploymentTask = getBoshClient().getTaskUpdates(taskId).blockLast(Duration.ofMinutes(5));

		Assertions.assertEquals(Task.State.done, deleteDeploymentTask.getState());

		LOGGER.info("Deployment {} successfully cleaned up.", DEPLOYMENT_NAME);
	}

	private BoshWebFluxClient getBoshClient() {
		if (boshClient == null) {
			var operators = List.of("bosh-manifests/cpi/virtualbox.yml",
					"bosh-manifests/cpi/virtualbox-outbound-network.yml", "bosh-manifests/bosh-lite.yml",
					"bosh-manifests/bosh-lite-runc.yml", "bosh-manifests/uaa.yml", "bosh-manifests/credhub.yml");

			var variables = Map.of("outbound_network_name", "NatNetwork");

			var directorInfo = boshBootstrapClient.createEnvironment("bosh-lite", getStateDir("bosh-lite"),
					new DirectorConfig("192.168.50.6", "192.168.50.0/24", "192.168.50.1"),
					new DirectorInfo(null, null, null), "bosh-manifests/bosh.yml", operators, variables, Map.of())

					.block();

			boshClient = new BoshWebFluxClient("https://" + directorInfo.getEnvironment() + ":25555", "admin",
					directorInfo.getDirectorCredentials().getAdminPassword(),
					"https://" + directorInfo.getEnvironment() + ":8443/oauth/token",
					directorInfo.getDirectorCredentials().getDirectorSsl().getCa().getBytes());
		}

		return boshClient;
	}

	@Test
	public void assertDeployment() throws IOException {
		List<Config> configs = getBoshClient().getConfigs().block();

		Assertions.assertNotNull(configs);

		String cloudConfig = new BufferedReader(
				new InputStreamReader(new ClassPathResource("cloud-config/boshlite.yml").getInputStream())).lines()
						.collect(Collectors.joining("\n"));

		getBoshClient().updateConfig(new UpdateConfigRequest("default", "cloud", cloudConfig)).block();

		String manifest = new BufferedReader(
				new InputStreamReader(new ClassPathResource("manifest/manifest-1.yml").getInputStream())).lines()
						.collect(Collectors.joining("\n"));

		cleanUpDeployment();

		int taskId = getBoshClient().uploadRelease(new UploadReleaseRequest(
				"https://s3.ca-central-1.amazonaws.com/apache-pinot-bosh-release/distribution/apache-pinot-bosh-release-0.3.0.tar.gz",
				null)).block();

		getBoshClient().getTaskUpdates(taskId)

				.doOnNext(task -> LOGGER.info("Received new update for : {}", task))

				.blockLast(Duration.ofMinutes(5));

		taskId = getBoshClient().uploadRelease(new UploadReleaseRequest(
				"https://github.com/bosh-prometheus/prometheus-boshrelease/releases/download/v26.2.0/prometheus-26.2.0.tgz",
				null)).block();

		getBoshClient().getTaskUpdates(taskId)

				.doOnNext(task -> LOGGER.info("Received new update for : {}", task))

				.blockLast(Duration.ofMinutes(5));

		taskId = getBoshClient().uploadRelease(new UploadReleaseRequest(
				"https://s3.amazonaws.com/bosh-compiled-release-tarballs/bpm-1.1.8-ubuntu-xenial-621.71-20200422-220353-760960221-20200422220355.tgz",
				null)).block();

		getBoshClient().getTaskUpdates(taskId)

				.doOnNext(task -> LOGGER.info("Received new update for : {}", task))

				.blockLast(Duration.ofMinutes(5));

		taskId = getBoshClient().uploadRelease(new UploadReleaseRequest(
				"https://github.com/bosh-prometheus/node-exporter-boshrelease/releases/download/v4.2.0/node-exporter-4.2.0.tgz",
				null)).block();

		getBoshClient().getTaskUpdates(taskId)

				.doOnNext(task -> LOGGER.info("Received new update for : {}", task))

				.blockLast(Duration.ofMinutes(5));

		taskId = getBoshClient()
				.uploadStemcell(new UploadStemcellRequest(
						"https://bosh.io/d/stemcells/bosh-warden-boshlite-ubuntu-xenial-go_agent?v=621.71", null))
				.block();

		getBoshClient().getTaskUpdates(taskId)

				.doOnNext(task -> LOGGER.info("Received new update for : {}", task))

				.blockLast(Duration.ofMinutes(5));

		taskId = getBoshClient().deploy(manifest).block();

		LOGGER.info("Deployment running through task {}.", taskId);

		getBoshClient().getTaskEvents(taskId)

				.doOnNext(taskEvent -> LOGGER.info("Received task event : {}", taskEvent))

				.subscribe();

		Task deploymentTask = getBoshClient().getTaskUpdates(taskId)

				.doOnNext(task -> LOGGER.info("Received new update for : {}", task))

				.blockLast(Duration.ofMinutes(20));

		Assertions.assertEquals(Task.State.done, deploymentTask.getState());

		Task task = boshClient.getTask(taskId).block();

		Assertions.assertNotNull(task);

		cleanUpDeployment();

	}
}
