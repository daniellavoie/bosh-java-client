package dev.daniellavoie.bosh.client.webflux.cli;

public class EnvironmentCommandResult {
	private final String directorCredentials;
	private final String directorState;

	public EnvironmentCommandResult(String directorCredentials, String directorState) {
		this.directorCredentials = directorCredentials;
		this.directorState = directorState;
	}

	public String getDirectorCredentials() {
		return directorCredentials;
	}

	public String getDirectorState() {
		return directorState;
	}
}
