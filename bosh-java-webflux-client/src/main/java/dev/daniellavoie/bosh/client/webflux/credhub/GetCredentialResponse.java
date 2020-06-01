package dev.daniellavoie.bosh.client.webflux.credhub;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GetCredentialResponse<T extends Credential<?>> {
	private final List<T> data;

	@JsonCreator
	public GetCredentialResponse(@JsonProperty("data") List<T> data) {
		this.data = data;
	}

	public List<T> getData() {
		return data;
	}
}
