package dev.daniellavoie.bosh.client.webflux.credhub;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

import dev.daniellavoie.bosh.client.webflux.oauth2.ClientCredentialTokenRefreshFilter;
import dev.daniellavoie.bosh.client.webflux.util.SslUtil;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

public class CredhubClient {
	private final WebClient webClient;

	public CredhubClient(CredhubConfiguration credhubConfiguration) {
		var clientHttpConnector = new ReactorClientHttpConnector(HttpClient.create()
				.secure(t -> t.sslContext(SslUtil.createSSLContext(credhubConfiguration.getCaChain()))));

		this.webClient = WebClient.builder()

				.baseUrl(credhubConfiguration.getUrl())

				.clientConnector(clientHttpConnector)

				.filter(new ClientCredentialTokenRefreshFilter(credhubConfiguration.getClientId(),
						credhubConfiguration.getClientSecret(), credhubConfiguration.getTokenUri(),
						clientHttpConnector))

				.build();
	}

	public Mono<CertificateCredential> getCertificate(String name) {
		return webClient.get().uri("/api/v1/data?name={name}&current=true", name).retrieve()

				.bodyToMono(new ParameterizedTypeReference<GetCredentialResponse<CertificateCredential>>() {
				})

				.map(response -> response.getData().get(0));

	}

	public Mono<PasswordCredential> getPassword(String name) {
		return webClient.get().uri("/api/v1/data?name={name}&current=true", name).retrieve()

				.bodyToMono(new ParameterizedTypeReference<GetCredentialResponse<PasswordCredential>>() {
				})

				.map(response -> response.getData().get(0));

	}
}
