package dev.daniellavoie.bosh.client.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Deployment {
	private final String name;
	private final String cloudConfig;
	private final List<DeploymentBlob> releases;
	private final List<DeploymentBlob> stemcells;
	private final List<String> teams;
	private final boolean locked;

	@JsonCreator
	public Deployment(@JsonProperty("name") String name, @JsonProperty("cloud_config") String cloudConfig,
			@JsonProperty("releases") List<DeploymentBlob> releases,
			@JsonProperty("stemcells") List<DeploymentBlob> stemcells, @JsonProperty("teams") List<String> teams,
			@JsonProperty("locked") boolean locked) {
		this.name = name;
		this.cloudConfig = cloudConfig;
		this.releases = releases;
		this.stemcells = stemcells;
		this.teams = teams;
		this.locked = locked;
	}

	public String getName() {
		return name;
	}

	public String getCloudConfig() {
		return cloudConfig;
	}

	public List<DeploymentBlob> getReleases() {
		return releases;
	}

	public List<DeploymentBlob> getStemcells() {
		return stemcells;
	}

	public List<String> getTeams() {
		return teams;
	}

	public boolean isLocked() {
		return locked;
	}

	@Override
	public String toString() {
		return "Deployment [name=" + name + ", cloudConfig=" + cloudConfig + ", releases=" + releases + ", stemcells="
				+ stemcells + ", teams=" + teams + ", locked=" + locked + "]";
	}
}
