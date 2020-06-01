package dev.daniellavoie.bosh.client.webflux.credhub;

import java.time.LocalDateTime;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class Credential<T> {
	private final String type;
	private final LocalDateTime versionCreatedAt;
	private final String id;
	private final String name;
	private final Map<String, String> metadata;
	private final T value;

	@JsonCreator
	public Credential(@JsonProperty("type") String type,
			@JsonProperty("version_created_at") LocalDateTime versionCreatedAt, @JsonProperty("id") String id,
			@JsonProperty("name") String name, @JsonProperty("metadata") Map<String, String> metadata,
			@JsonProperty("value") T value) {
		this.type = type;
		this.versionCreatedAt = versionCreatedAt;
		this.id = id;
		this.name = name;
		this.metadata = metadata;
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public LocalDateTime getVersionCreatedAt() {
		return versionCreatedAt;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Map<String, String> getMetadata() {
		return metadata;
	}

	public T getValue() {
		return value;
	}

}
