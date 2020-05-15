package dev.daniellavoie.bosh.client.webflux.oauth2;

import java.time.LocalDateTime;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.annotation.JsonProperty;

import reactor.core.publisher.Mono;

public class ClientCredentialTokenRefreshFilter implements ExchangeFilterFunction {
	private final TokenRequest tokenRequest;

	private final WebClient webClient;

	private String accessToken;
	private LocalDateTime tokenExpiration;

	public ClientCredentialTokenRefreshFilter(String clientId, String clientSecret, String tokenUri,
			ClientHttpConnector clientHttpConnector) {
		this.tokenRequest = new TokenRequest(clientId, clientSecret);

		this.webClient = WebClient.builder()

				.baseUrl(tokenUri).clientConnector(clientHttpConnector).build();
	}

	@Override
	public Mono<ClientResponse> filter(ClientRequest request, ExchangeFunction next) {
		return Mono.justOrEmpty(accessToken)

				.filter(accessToken -> LocalDateTime.now().isAfter(tokenExpiration.minusMinutes(1)))

				.switchIfEmpty(refreshToken())

				.then(Mono.defer(() -> next.exchange(ClientRequest.from(request)
						.header(HttpHeaders.AUTHORIZATION, "bearer " + accessToken).build())));
	}

	public Mono<String> refreshToken() {
		return webClient.post()

				.bodyValue("client_id=" + tokenRequest.getClientId() + "&client_secret="
						+ tokenRequest.getClientSecret() + "&grant_type=client_credentials")

				.accept(MediaType.APPLICATION_JSON)

				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)

				.exchange()

				.flatMap(response -> response.bodyToMono(Token.class))

				.map(this::updateToken);
	}

	private String updateToken(Token token) {
		this.accessToken = token.getAccessToken();
		this.tokenExpiration = LocalDateTime.now().plusSeconds(token.getExpiresIn());

		return this.accessToken;
	}

	private class TokenRequest {
		private final String clientId;
		private final String clientSecret;
		private final String grantType = "client_credentials";
		private final String tokenFormat = "opaque";

		public TokenRequest(String clientId, String clientSecret) {
			this.clientId = clientId;
			this.clientSecret = clientSecret;
		}

		@JsonProperty("client_id")
		public String getClientId() {
			return clientId;
		}

		@JsonProperty("client_secret")
		public String getClientSecret() {
			return clientSecret;
		}

		@JsonProperty("grant_type")
		public String getGrantType() {
			return grantType;
		}

		@JsonProperty("token_format")
		public String getTokenFormat() {
			return tokenFormat;
		}
	}
}
