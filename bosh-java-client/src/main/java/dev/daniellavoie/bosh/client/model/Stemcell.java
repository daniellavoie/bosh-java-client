package dev.daniellavoie.bosh.client.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;

public class Stemcell {
	private final String id;
	private final String name;
	private final String operatingSystem;
	private final String version;
	private final String cid;
	private final List<StemcellDeployment> deployments;
	private final String cpi;
	private final String apiVersion;

	@JsonCreator
	public Stemcell(String id, String name, String operatingSystem, String version, String cid,
			List<StemcellDeployment> deployments, final String cpi, String apiVersion) {
		this.id = id;
		this.name = name;
		this.operatingSystem = operatingSystem;
		this.version = version;
		this.cid = cid;
		this.deployments = deployments;
		this.cpi = cpi;
		this.apiVersion = apiVersion;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getOperatingSystem() {
		return operatingSystem;
	}

	public String getVersion() {
		return version;
	}

	public String getCid() {
		return cid;
	}

	public List<StemcellDeployment> getDeployments() {
		return deployments;
	}

	public String getCpi() {
		return cpi;
	}

	public String getApiVersion() {
		return apiVersion;
	}
}
