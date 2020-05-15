package dev.daniellavoie.bosh.client.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Release {
	private final String name;
	private final List<ReleaseVersion> releaseVersions;

	public Release(String name, @JsonProperty("release_versions") List<ReleaseVersion> releaseVersions) {
		this.name = name;
		this.releaseVersions = releaseVersions;
	}

	public String getName() {
		return name;
	}

	public List<ReleaseVersion> getReleaseVersions() {
		return releaseVersions;
	}

}
