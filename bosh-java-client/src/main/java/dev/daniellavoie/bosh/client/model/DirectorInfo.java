package dev.daniellavoie.bosh.client.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public class DirectorInfo {
	private final String environment;
	private final DirectorCredentials directorCredentials;
	private final String directorState;
	
	@JsonCreator
	public DirectorInfo(String environment, DirectorCredentials directorCredentials, String directorState) {
		this.environment = environment;
		this.directorCredentials = directorCredentials;
		this.directorState = directorState;
	}
	public String getEnvironment() {
		return environment;
	}
	public DirectorCredentials getDirectorCredentials() {
		return directorCredentials;
	}
	public String getDirectorState() {
		return directorState;
	}
}
