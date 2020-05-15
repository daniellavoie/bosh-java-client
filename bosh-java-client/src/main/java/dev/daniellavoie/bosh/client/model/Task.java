package dev.daniellavoie.bosh.client.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public class Task {
	public enum State {
		queued, processing, cancelled, cancelling, done, error, timeout
	}

	private final int id;
	private final State state;
	private final String description;
	private final int timestamp;
	private final String result;
	private final String user;
	private final String contextId;
	private final int startedAt;
	private final String deployment;

	@JsonCreator
	public Task(int id, State state, String description, int timestamp, String result, String user, String contextId,
			int startedAt, String deployment) {
		this.id = id;
		this.state = state;
		this.description = description;
		this.timestamp = timestamp;
		this.result = result;
		this.user = user;
		this.contextId = contextId;
		this.startedAt = startedAt;
		this.deployment = deployment;
	}

	public int getId() {
		return id;
	}

	public State getState() {
		return state;
	}

	public String getDescription() {
		return description;
	}

	public int getTimestamp() {
		return timestamp;
	}

	public String getResult() {
		return result;
	}

	public String getUser() {
		return user;
	}

	public String getContextId() {
		return contextId;
	}

	public int getStartedAt() {
		return startedAt;
	}

	public String getDeployment() {
		return deployment;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((contextId == null) ? 0 : contextId.hashCode());
		result = prime * result + ((deployment == null) ? 0 : deployment.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + id;
		result = prime * result + ((this.result == null) ? 0 : this.result.hashCode());
		result = prime * result + startedAt;
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + timestamp;
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		Task other = (Task) obj;
		if (contextId == null) {
			if (other.contextId != null)
				return false;
		} else if (!contextId.equals(other.contextId))
			return false;
		if (deployment == null) {
			if (other.deployment != null)
				return false;
		} else if (!deployment.equals(other.deployment))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id != other.id)
			return false;
		if (result == null) {
			if (other.result != null)
				return false;
		} else if (!result.equals(other.result))
			return false;
		if (startedAt != other.startedAt)
			return false;
		if (state != other.state)
			return false;
		if (timestamp != other.timestamp)
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Task [id=" + id + ", state=" + state + ", description=" + description + ", timestamp=" + timestamp
				+ ", result=" + result + ", user=" + user + ", contextId=" + contextId + ", startedAt=" + startedAt
				+ ", deployment=" + deployment + "]";
	}
}
