package dev.daniellavoie.bosh.client.api;

import com.fasterxml.jackson.annotation.JsonCreator;

public class UpdateDeploymentResponse {
	private final String manifest;

	@JsonCreator
	public UpdateDeploymentResponse(String manifest) {
		this.manifest = manifest;
	}

	public String getManifest() {
		return manifest;
	}
}
