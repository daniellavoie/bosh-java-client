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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((directorCredentials == null) ? 0 : directorCredentials.hashCode());
		result = prime * result + ((directorState == null) ? 0 : directorState.hashCode());
		result = prime * result + ((environment == null) ? 0 : environment.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DirectorInfo other = (DirectorInfo) obj;
		if (directorCredentials == null) {
			if (other.directorCredentials != null)
				return false;
		} else if (!directorCredentials.equals(other.directorCredentials))
			return false;
		if (directorState == null) {
			if (other.directorState != null)
				return false;
		} else if (!directorState.equals(other.directorState))
			return false;
		if (environment == null) {
			if (other.environment != null)
				return false;
		} else if (!environment.equals(other.environment))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DirectorInfo [environment=" + environment + ", directorCredentials=" + directorCredentials
				+ ", directorState=" + directorState + "]";
	}
}
