package dev.daniellavoie.bosh.client.webflux.credhub;

public class CredhubConfiguration {
	private final String url;
	private final String clientId;
	private final String clientSecret;
	private final String tokenUri;
	private final byte[][] caChain;

	public CredhubConfiguration(String url, String clientId, String clientSecret, String tokenUri, byte[][] caChain) {
		this.url = url;
		this.clientId = clientId;
		this.clientSecret = clientSecret;
		this.tokenUri = tokenUri;
		this.caChain = caChain;
	}

	public String getUrl() {
		return url;
	}

	public String getClientId() {
		return clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public String getTokenUri() {
		return tokenUri;
	}

	public byte[][] getCaChain() {
		return caChain;
	}
}
