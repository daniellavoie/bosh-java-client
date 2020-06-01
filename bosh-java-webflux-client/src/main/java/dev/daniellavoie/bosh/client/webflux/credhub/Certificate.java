package dev.daniellavoie.bosh.client.webflux.credhub;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Certificate {
	private final String ca;
	private final String certificate;
	private final String private_key;
	private final boolean generated;
	private final boolean selfSigned;
	private final boolean certificateAuthority;
	private final LocalDateTime expiryDate;
	private final boolean transitional;

	@JsonCreator
	public Certificate(@JsonProperty("ca") String ca, @JsonProperty("certificate") String certificate,
			@JsonProperty("private_key") String privateKey, @JsonProperty("generated") boolean generated,
			@JsonProperty("selfSigned") boolean selfSigned,
			@JsonProperty("certificateAuthority") boolean certificateAuthority,
			@JsonProperty("expiryDate") LocalDateTime expiryDate,
			@JsonProperty("transitional") boolean transitional) {
		this.ca = ca;
		this.certificate = certificate;
		this.private_key = privateKey;
		this.generated = generated;
		this.selfSigned = selfSigned;
		this.certificateAuthority = certificateAuthority;
		this.expiryDate = expiryDate;
		this.transitional = transitional;
	}

	public String getCa() {
		return ca;
	}

	public String getCertificate() {
		return certificate;
	}

	public String getPrivate_key() {
		return private_key;
	}

	public boolean isGenerated() {
		return generated;
	}

	public boolean isSelfSigned() {
		return selfSigned;
	}

	public boolean isCertificateAuthority() {
		return certificateAuthority;
	}

	public LocalDateTime getExpiryDate() {
		return expiryDate;
	}

	public boolean isTransitional() {
		return transitional;
	}
}
