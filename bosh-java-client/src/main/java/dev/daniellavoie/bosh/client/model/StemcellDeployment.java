package dev.daniellavoie.bosh.client.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class StemcellDeployment {
	private final String name;

	@JsonCreator
	public StemcellDeployment(@JsonProperty("name") String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
