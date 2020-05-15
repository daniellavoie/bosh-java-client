package dev.daniellavoie.bosh.client.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;

public class TaskEvent {
	private final int time;
	private final String stage;
	private final List<String> tags;
	private final int total;
	private final String task;
	private final int index;
	private final String state;
	private final int progress;
	private final TaskError error;

	@JsonCreator
	public TaskEvent(int time, String stage, List<String> tags, int total, String task, int index, String state,
			int progress, TaskError error) {
		this.time = time;
		this.stage = stage;
		this.tags = tags;
		this.total = total;
		this.task = task;
		this.index = index;
		this.state = state;
		this.progress = progress;
		this.error = error;
	}

	public int getTime() {
		return time;
	}

	public String getStage() {
		return stage;
	}

	public List<String> getTags() {
		return tags;
	}

	public int getTotal() {
		return total;
	}

	public String getTask() {
		return task;
	}

	public int getIndex() {
		return index;
	}

	public String getState() {
		return state;
	}

	public int getProgress() {
		return progress;
	}

	public TaskError getError() {
		return error;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((error == null) ? 0 : error.hashCode());
		result = prime * result + index;
		result = prime * result + progress;
		result = prime * result + ((stage == null) ? 0 : stage.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((tags == null) ? 0 : tags.hashCode());
		result = prime * result + ((task == null) ? 0 : task.hashCode());
		result = prime * result + time;
		result = prime * result + total;
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
		TaskEvent other = (TaskEvent) obj;
		if (error == null) {
			if (other.error != null)
				return false;
		} else if (!error.equals(other.error))
			return false;
		if (index != other.index)
			return false;
		if (progress != other.progress)
			return false;
		if (stage == null) {
			if (other.stage != null)
				return false;
		} else if (!stage.equals(other.stage))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		if (tags == null) {
			if (other.tags != null)
				return false;
		} else if (!tags.equals(other.tags))
			return false;
		if (task == null) {
			if (other.task != null)
				return false;
		} else if (!task.equals(other.task))
			return false;
		if (time != other.time)
			return false;
		if (total != other.total)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TaskEvent [time=" + time + ", stage=" + stage + ", tags=" + tags + ", total=" + total + ", task=" + task
				+ ", index=" + index + ", state=" + state + ", progress=" + progress + ", error=" + error + "]";
	}
}
