package dev.daniellavoie.bosh.client.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public class Certificate {
	private final String ca;
	private final String certificate;
	private final String privateKey;
	private final String publicKey;
	private final String publicKeyFingerprint;

	@JsonCreator
	public Certificate(String ca, String certificate, String privateKey, String publicKey,
			String publicKeyFingerprint) {
		this.ca = ca;
		this.certificate = certificate;
		this.privateKey = privateKey;
		this.publicKey = publicKey;
		this.publicKeyFingerprint = publicKeyFingerprint;
	}

	public String getCa() {
		return ca;
	}

	public String getCertificate() {
		return certificate;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public String getPublicKeyFingerprint() {
		return publicKeyFingerprint;
	}
}
