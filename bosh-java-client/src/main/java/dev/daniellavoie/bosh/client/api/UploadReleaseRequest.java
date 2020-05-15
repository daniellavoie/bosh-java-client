package dev.daniellavoie.bosh.client.api;

public class UploadReleaseRequest {
	private final String location;
	private final String sha1;

	public UploadReleaseRequest(String location, String sha1) {
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
		return "UploadReleaseRequest [location=" + location + ", sha1=" + sha1 + "]";
	}
}
