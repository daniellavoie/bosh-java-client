package dev.daniellavoie.bosh.client.api;

public class UpdateDeploymentRequest {
	private final String manifest;

	public UpdateDeploymentRequest(String manifest) {
		this.manifest = manifest;
	}

	public String getManifest() {
		return manifest;
	}
}
