package dev.daniellavoie.bosh.client.webflux;

import java.nio.file.Path;
import java.time.Duration;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.codec.ClientCodecConfigurer;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import dev.daniellavoie.bosh.client.api.UpdateConfigRequest;
import dev.daniellavoie.bosh.client.api.UploadReleaseRequest;
import dev.daniellavoie.bosh.client.api.UploadStemcellRequest;
import dev.daniellavoie.bosh.client.model.Config;
import dev.daniellavoie.bosh.client.model.Deployment;
import dev.daniellavoie.bosh.client.model.Release;
import dev.daniellavoie.bosh.client.model.Stemcell;
import dev.daniellavoie.bosh.client.model.Task;
import dev.daniellavoie.bosh.client.model.Task.State;
import dev.daniellavoie.bosh.client.model.TaskEvent;
import dev.daniellavoie.bosh.client.webflux.oauth2.ClientCredentialTokenRefreshFilter;
import dev.daniellavoie.bosh.client.webflux.util.JacksonUtil;
import dev.daniellavoie.bosh.client.webflux.util.SslUtil;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.ByteBufFlux;
import reactor.netty.http.client.HttpClient;

public class BoshWebFluxClient {
	private static final Logger LOGGER = LoggerFactory.getLogger(BoshWebFluxClient.class);

	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
			.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE).setSerializationInclusion(Include.NON_NULL)
			.findAndRegisterModules();
	private static final ObjectMapper YAML_MAPPER = new ObjectMapper(new YAMLFactory())
			.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE).setSerializationInclusion(Include.NON_NULL)
			.findAndRegisterModules();

	private final WebClient webClient;

	public BoshWebFluxClient(String environment, String clientId, String clientSecret, String tokenUri, byte[] boshCA) {
		var httpClientConnector = new ReactorClientHttpConnector(
				HttpClient.create().secure(t -> t.sslContext(SslUtil.createSSLContext(boshCA))));

		webClient = WebClient.builder()

				.baseUrl(environment)

				.clientConnector(httpClientConnector)

				.filter(new ClientCredentialTokenRefreshFilter(clientId, clientSecret, tokenUri, httpClientConnector))

				.exchangeStrategies(ExchangeStrategies.builder().codecs(this::acceptedCodecs).build())

				.build();
	}

	private void acceptedCodecs(ClientCodecConfigurer clientCodecConfigurer) {
		clientCodecConfigurer.customCodecs().register(new Jackson2JsonDecoder(OBJECT_MAPPER, MediaType.TEXT_HTML));
		clientCodecConfigurer.customCodecs()
				.register(new Jackson2JsonDecoder(OBJECT_MAPPER, MediaType.APPLICATION_JSON));
		clientCodecConfigurer.customCodecs()
				.register(new Jackson2JsonEncoder(OBJECT_MAPPER, MediaType.APPLICATION_JSON));
		clientCodecConfigurer.customCodecs()
				.register(new Jackson2JsonEncoder(YAML_MAPPER, new MediaType("text", "yaml")));
	}

	public Mono<Integer> deleteDeployment(String deployment) {
		return webClient.delete().uri("/deployments/{deployment}", deployment).exchange()
				.flatMap(response -> handleTaskResponse(response));
	}

	public Mono<Integer> deploy(String manifest) {
		return webClient.post().uri("/deployments").contentType(new MediaType("text", "yaml")).bodyValue(manifest)
				.exchange().flatMap(response -> handleTaskResponse(response));
	}

	public Mono<List<Config>> getConfigs() {
		return webClient.get().uri("/configs?latest=true").accept(MediaType.TEXT_HTML).exchange()
				.flatMap(response -> handleMonoResponse(response, String.class)
						.map(value -> JacksonUtil.read(value, new TypeReference<List<Config>>() {
						})));
	}

	public Mono<Deployment> getDeployment(String name) {
		return webClient.get().uri("/deployments/{name}", name).exchange()
				.flatMap(response -> handleMonoResponse(response, Deployment.class));
	}

	public Flux<Release> getReleases() {
		return webClient.get().uri("/releases").exchange().flatMapMany(response -> response.bodyToFlux(Release.class));
	}

	public Flux<Stemcell> getStemcells() {
		return webClient.get().uri("/stemcells").exchange()
				.flatMapMany(response -> response.bodyToFlux(Stemcell.class));
	}

	public Mono<Task> getTask(int id) {
		return webClient.get().uri("/tasks/{id}", id).exchange().flatMap(response -> response.bodyToMono(Task.class));
	}

	public Flux<TaskEvent> getTaskEvents(int taskId) {
		var context = new TaskEventsContext();

		return Flux.range(0, Integer.MAX_VALUE)

				.delayUntil(index -> index == 0 ? Mono.empty() : Mono.delay(Duration.ofSeconds(2)))

				.flatMap(index -> getTask(taskId))

				.doOnNext(task -> context
						.setCompleted(task.getState().equals(State.done) || task.getState().equals(State.cancelled)
								|| task.getState().equals(State.error) || task.getState().equals(State.timeout)))

				.flatMap(task -> getTaskEventsOutput(taskId))

				.flatMap(eventsLines -> Flux.fromStream(eventsLines.lines()))

				.map(eventLine -> JacksonUtil.read(eventLine, TaskEvent.class))

				.filter(taskEvent -> !context.contains(taskEvent))

				.doOnNext(taskEvent -> context.add(taskEvent))

				.takeUntil(taskEvent -> context.isCompleted());
	}

	private Mono<String> getTaskEventsOutput(int id) {
		return webClient.get().uri("/tasks/{id}/output?type=event", id).exchange()
				.flatMap(response -> response.bodyToMono(String.class));
	}

	public Mono<String> getTaskOutputDebug(int id) {
		return webClient.get().uri("/tasks/{id}/output?type=debug", id).exchange()
				.flatMap(response -> response.bodyToMono(String.class));
	}

	public Flux<Task> getTaskUpdates(int taskId) {
		TaskUpdateContext taskUpdateContext = new TaskUpdateContext();

		return Flux.range(0, Integer.MAX_VALUE)

				.delayUntil(index -> index == 0 ? Mono.empty() : Mono.delay(Duration.ofSeconds(5)))

				.flatMap(index -> getTask(taskId))

				.filter(task -> !task.equals(taskUpdateContext.getLatestTask()))

				.doOnNext(task -> taskUpdateContext.setLatestTask(task))

				.doOnNext(task -> taskUpdateContext
						.setCompleted(task.getState().equals(State.done) || task.getState().equals(State.cancelled)
								|| task.getState().equals(State.error) || task.getState().equals(State.timeout)))

				.takeUntil(task -> taskUpdateContext.isCompleted());
	}

	private <T> Mono<T> handleMonoResponse(ClientResponse response, Class<T> bodyType) {
		if (response.statusCode().equals(HttpStatus.NOT_FOUND)) {
			return response.releaseBody().then(Mono.empty());
		}

		return response.bodyToMono(bodyType);
	}

	private Mono<Integer> handleTaskResponse(ClientResponse response) {
		String[] paths = response.headers().asHttpHeaders().getLocation().getPath().split("/");

		return response.releaseBody().thenReturn(Integer.parseInt(paths[paths.length - 1]));
	}

	public Mono<Config> updateConfig(UpdateConfigRequest request) {
		return webClient.post().uri("/configs").bodyValue(request).exchange()
				.flatMap(response -> handleMonoResponse(response, String.class)
						.map(value -> JacksonUtil.read(value, Config.class)));
	}

	public Mono<Integer> uploadRelease(UploadReleaseRequest uploadReleaseRequest) {
		return webClient.post().uri("/releases")

				.bodyValue(uploadReleaseRequest).exchange()

				.doOnSubscribe(subscription -> LOGGER.info("Uploading {} to bosh director.",
						uploadReleaseRequest.getLocation()))

				.flatMap(response -> handleTaskResponse(response))

				.doOnSuccess(task -> LOGGER.info("Successfully uploaded {} to bosh director.", uploadReleaseRequest));
	}

	public Mono<Integer> uploadRelease(Path release) {
		return webClient.post().uri("/releases").body(ByteBufFlux.fromPath(release), ByteBufFlux.class).exchange()
				.flatMap(response -> handleTaskResponse(response));
	}

	public Mono<Integer> uploadStemcell(UploadStemcellRequest uploadStemcellRequest) {
		return webClient.post().uri("/stemcells").bodyValue(uploadStemcellRequest).exchange()

				.doOnSubscribe(subscription -> LOGGER.info("Uploading {} to bosh director.",
						uploadStemcellRequest.getLocation()))

				.flatMap(response -> handleTaskResponse(response))

				.doOnSuccess(task -> LOGGER.info("Successfully uploaded {} to bosh director.", uploadStemcellRequest));
	}
}
