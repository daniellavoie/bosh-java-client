package dev.daniellavoie.bosh.client.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public class Deployment {
	private final String manifest;

	@JsonCreator
	public Deployment(String manifest) {
		this.manifest = manifest;
	}

	public String getManifest() {
		return manifest;
	}
}
