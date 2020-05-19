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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ca == null) ? 0 : ca.hashCode());
		result = prime * result + ((certificate == null) ? 0 : certificate.hashCode());
		result = prime * result + ((privateKey == null) ? 0 : privateKey.hashCode());
		result = prime * result + ((publicKey == null) ? 0 : publicKey.hashCode());
		result = prime * result + ((publicKeyFingerprint == null) ? 0 : publicKeyFingerprint.hashCode());
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
		Certificate other = (Certificate) obj;
		if (ca == null) {
			if (other.ca != null)
				return false;
		} else if (!ca.equals(other.ca))
			return false;
		if (certificate == null) {
			if (other.certificate != null)
				return false;
		} else if (!certificate.equals(other.certificate))
			return false;
		if (privateKey == null) {
			if (other.privateKey != null)
				return false;
		} else if (!privateKey.equals(other.privateKey))
			return false;
		if (publicKey == null) {
			if (other.publicKey != null)
				return false;
		} else if (!publicKey.equals(other.publicKey))
			return false;
		if (publicKeyFingerprint == null) {
			if (other.publicKeyFingerprint != null)
				return false;
		} else if (!publicKeyFingerprint.equals(other.publicKeyFingerprint))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Certificate [ca=" + ca + ", certificate=" + certificate + ", privateKey=" + privateKey + ", publicKey="
				+ publicKey + ", publicKeyFingerprint=" + publicKeyFingerprint + "]";
	}
}
