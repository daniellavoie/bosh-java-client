package dev.daniellavoie.bosh.client.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;

public class ReleaseVersion {
	private final String version;
	private final String commitHash;
	private final boolean uncommittedChanges;
	private final boolean currentlyDeployed;
	private final List<String> jobNames;

	@JsonCreator
	public ReleaseVersion(String version, String commitHash, boolean uncommittedChanges, boolean currentlyDeployed,
			List<String> jobNames) {
		this.version = version;
		this.commitHash = commitHash;
		this.uncommittedChanges = uncommittedChanges;
		this.currentlyDeployed = currentlyDeployed;
		this.jobNames = jobNames;
	}

	public String getVersion() {
		return version;
	}

	public String getCommitHash() {
		return commitHash;
	}

	public boolean isUncommittedChanges() {
		return uncommittedChanges;
	}

	public boolean isCurrentlyDeployed() {
		return currentlyDeployed;
	}

	public List<String> getJobNames() {
		return jobNames;
	}
}
