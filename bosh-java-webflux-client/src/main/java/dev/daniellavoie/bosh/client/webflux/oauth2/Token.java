package dev.daniellavoie.bosh.client.webflux.oauth2;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Token {
	private final String accessToken;
	private final int expiresIn;
	private final String scope;

	@JsonCreator
	public Token(@JsonProperty("access_token") String accessToken, @JsonProperty("expires_in") int expiresIn,
			@JsonProperty("scope") String scope) {
		this.accessToken = accessToken;
		this.expiresIn = expiresIn;
		this.scope = scope;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public int getExpiresIn() {
		return expiresIn;
	}

	public String getScope() {
		return scope;
	}
}
