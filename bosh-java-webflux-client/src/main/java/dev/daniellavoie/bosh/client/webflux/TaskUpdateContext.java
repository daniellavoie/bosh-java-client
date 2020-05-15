package dev.daniellavoie.bosh.client.webflux;

import dev.daniellavoie.bosh.client.model.Task;

public class TaskUpdateContext {
	private Task latestTask;
	private boolean completed;

	public Task getLatestTask() {
		return latestTask;
	}

	public void setLatestTask(Task latestTask) {
		this.latestTask = latestTask;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
}
