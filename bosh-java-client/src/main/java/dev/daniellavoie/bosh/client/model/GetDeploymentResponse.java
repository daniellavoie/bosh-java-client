package dev.daniellavoie.bosh.client.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GetDeploymentResponse {
	private final String manifest;

	@JsonCreator
	public GetDeploymentResponse(@JsonProperty("manifest") String manifest) {
		this.manifest = manifest;
	}

	public String getManifest() {
		return manifest;
	}

	@Override
	public String toString() {
		return "GetDeploymentResponse [manifest=" + manifest + "]";
	}
}
