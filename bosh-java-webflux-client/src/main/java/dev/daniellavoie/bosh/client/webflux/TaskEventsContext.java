package dev.daniellavoie.bosh.client.webflux;

import java.util.HashSet;
import java.util.Set;

import dev.daniellavoie.bosh.client.model.TaskEvent;

public class TaskEventsContext {
	private final Set<TaskEvent> taskEvents = new HashSet<TaskEvent>();
	private boolean completed;

	public void add(TaskEvent taskEvent) {
		taskEvents.add(taskEvent);
	}
	
	public boolean contains(TaskEvent taskEvent) {
		return taskEvents.contains(taskEvent);
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
}
