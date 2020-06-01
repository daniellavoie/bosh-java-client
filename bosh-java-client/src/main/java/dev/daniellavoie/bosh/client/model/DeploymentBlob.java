package dev.daniellavoie.bosh.client.model;

public class DeploymentBlob {
	private final String name;
	private final String version;

	public DeploymentBlob(String name, String version) {
		this.name = name;
		this.version = version;
	}

	public String getName() {
		return name;
	}

	public String getVersion() {
		return version;
	}
}
