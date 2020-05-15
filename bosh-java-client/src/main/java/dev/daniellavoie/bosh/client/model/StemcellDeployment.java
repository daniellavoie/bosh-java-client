package dev.daniellavoie.bosh.client.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public class StemcellDeployment {
	private final String name;

	@JsonCreator
	public StemcellDeployment(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
