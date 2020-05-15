package dev.daniellavoie.bosh.client.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public class Config {
	private final long id;
	private final String name;
	private final String type;
	private final String content;
	private final String team;
	private final String createdAt;
	private final boolean current;

	@JsonCreator
	public Config(long id, String name, String type, String content, String team, String createdAt, boolean current) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.content = content;
		this.team = team;
		this.createdAt = createdAt;
		this.current = current;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public String getContent() {
		return content;
	}

	public String getTeam() {
		return team;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public boolean getCurrent() {
		return current;
	}
}
