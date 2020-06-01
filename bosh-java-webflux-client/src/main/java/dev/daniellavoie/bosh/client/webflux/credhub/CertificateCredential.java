package dev.daniellavoie.bosh.client.webflux.credhub;

import java.time.LocalDateTime;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CertificateCredential extends Credential<Certificate> {

	@JsonCreator
	public CertificateCredential(@JsonProperty("type") String type,
			@JsonProperty("version_created_at") LocalDateTime versionCreatedAt, @JsonProperty("id") String id,
			@JsonProperty("name") String name, @JsonProperty("metadata") Map<String, String> metadata,
			@JsonProperty("value") Certificate value) {
		super(type, versionCreatedAt, id, name, metadata, value);
	}

}
