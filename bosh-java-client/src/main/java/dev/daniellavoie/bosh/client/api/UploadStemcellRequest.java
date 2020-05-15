package dev.daniellavoie.bosh.client.api;

public class UploadStemcellRequest {
	private final String location;
	private final String sha1;

	public UploadStemcellRequest(String location, String sha1) {
		this.location = location;
		this.sha1 = sha1;
	}

	public String getLocation() {
		return location;
	}

	public String getSha1() {
		return sha1;
	}

	@Override
	public String toString() {
		return "UploadStemcellRequest [location=" + location + ", sha1=" + sha1 + "]";
	}
}
