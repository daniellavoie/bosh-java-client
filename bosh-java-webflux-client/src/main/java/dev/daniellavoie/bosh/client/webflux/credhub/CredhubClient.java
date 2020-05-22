package dev.daniellavoie.bosh.client.webflux.credhub;

import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

import dev.daniellavoie.bosh.client.webflux.oauth2.ClientCredentialTokenRefreshFilter;
import dev.daniellavoie.bosh.client.webflux.util.SslUtil;
import reactor.netty.http.client.HttpClient;

public class CredhubClient {
	private final WebClient webClient;

	public CredhubClient(String credhubUrl, String clientId, String clientSecret, String tokenUri, byte[] credhubCA) {
		var clientHttpConnector = new ReactorClientHttpConnector(
				HttpClient.create().secure(t -> t.sslContext(SslUtil.createSSLContext(credhubCA))));

		this.webClient = WebClient.builder()

				.baseUrl(credhubUrl)

				.clientConnector(clientHttpConnector)

				.filter(new ClientCredentialTokenRefreshFilter(clientId, clientSecret, tokenUri, clientHttpConnector))

				.build();
	}
}
