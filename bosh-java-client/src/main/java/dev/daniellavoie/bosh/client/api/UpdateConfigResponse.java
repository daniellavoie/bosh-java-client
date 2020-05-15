package dev.daniellavoie.bosh.client.api;

public class UpdateConfigResponse {
	private final String name;
	private final String type;
	private final String content;

	public UpdateConfigResponse(String name, String type, String content) {
		this.name = name;
		this.type = type;
		this.content = content;
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
}
