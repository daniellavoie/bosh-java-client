package dev.daniellavoie.bosh.client.webflux.cli;

import java.util.List;
import java.util.Map;

import dev.daniellavoie.bosh.client.model.DirectorConfig;
import dev.daniellavoie.bosh.client.model.DirectorInfo;

public class EnvironmentRequest {
	private final String environmentName;
	private final String stateDir;
	private final DirectorConfig directorConfig;
	private final DirectorInfo existingDirectorInfo;
	private final String manifest;
	private final List<String> operators;
	private final Map<String, String> variables;
	private final Map<String, String> variableFiles;

	public EnvironmentRequest(String environmentName, String stateDir, DirectorConfig directorConfig,
			DirectorInfo existingDirectorInfo, String manifest, List<String> operators, Map<String, String> variables,
			Map<String, String> variableFiles) {
		this.environmentName = environmentName;
		this.stateDir = stateDir;
		this.directorConfig = directorConfig;
		this.existingDirectorInfo = existingDirectorInfo;
		this.manifest = manifest;
		this.operators = operators;
		this.variables = variables;
		this.variableFiles = variableFiles;
	}

	public String getEnvironmentName() {
		return environmentName;
	}

	public String getStateDir() {
		return stateDir;
	}

	public DirectorConfig getDirectorConfig() {
		return directorConfig;
	}

	public DirectorInfo getExistingDirectorInfo() {
		return existingDirectorInfo;
	}

	public String getManifest() {
		return manifest;
	}

	public List<String> getOperators() {
		return operators;
	}

	public Map<String, String> getVariables() {
		return variables;
	}

	public Map<String, String> getVariableFiles() {
		return variableFiles;
	}
}
