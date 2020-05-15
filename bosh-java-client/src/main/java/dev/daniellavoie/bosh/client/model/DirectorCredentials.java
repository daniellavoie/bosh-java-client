package dev.daniellavoie.bosh.client.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DirectorCredentials {
	private String adminPassword;
	private String blobstoreAgentPassword;
	private Certificate blobstoreCa;
	private String blobstoreDirectorPassword;
	private Certificate blobstoreServerTls;
	private String credhubAdminClientSecret;
	private Certificate credhubCa;
	private String credhubCliUserPassword;
	private String credhubEncryptionPassword;
	private Certificate credhubTls;
	private Certificate defaultCa;
	private Certificate directorSsl;
	private String hmPassword;
	private Certificate jumpboxSsh;
	private String mbusBootstrapPassword;
	private Certificate mbusBootstrapSsl;
	private Certificate natsCa;
	private Certificate natsClientsDirectorTls;
	private Certificate natsClientsHealthMonitorTls;
	private String natsPassword;
	private Certificate natsServerTls;
	private String postgresPassword;
	private String uaaAdminClientSecret;
	private String uaaClientsDirectorToCredhub;
	private String uaaEncryptionKey1;
	private Certificate uaaJwtSigningKey;
	private Certificate uaaServiceProviderSsl;
	private Certificate uaaSsl;

	@JsonCreator
	public DirectorCredentials(String adminPassword, String blobstoreAgentPassword, Certificate blobstoreCa,
			String blobstoreDirectorPassword, Certificate blobstoreServerTls, String credhubAdminClientSecret,
			Certificate credhubCa, String credhubCliUserPassword, String credhubEncryptionPassword,
			Certificate credhubTls, Certificate defaultCa, Certificate directorSsl, String hmPassword,
			Certificate jumpboxSsh, String mbusBootstrapPassword, Certificate mbusBootstrapSsl, Certificate natsCa,
			Certificate natsClientsDirectorTls, Certificate natsClientsHealthMonitorTls, String natsPassword,
			Certificate natsServerTls, String postgresPassword, String uaaAdminClientSecret,
			String uaaClientsDirectorToCredhub, @JsonProperty("uaa_encryption_key_1") String uaaEncryptionKey1,
			Certificate uaaJwtSigningKey, Certificate uaaServiceProviderSsl, Certificate uaaSsl) {
		this.adminPassword = adminPassword;
		this.blobstoreAgentPassword = blobstoreAgentPassword;
		this.blobstoreCa = blobstoreCa;
		this.blobstoreDirectorPassword = blobstoreDirectorPassword;
		this.blobstoreServerTls = blobstoreServerTls;
		this.credhubAdminClientSecret = credhubAdminClientSecret;
		this.credhubCa = credhubCa;
		this.credhubCliUserPassword = credhubCliUserPassword;
		this.credhubEncryptionPassword = credhubEncryptionPassword;
		this.credhubTls = credhubTls;
		this.defaultCa = defaultCa;
		this.directorSsl = directorSsl;
		this.hmPassword = hmPassword;
		this.jumpboxSsh = jumpboxSsh;
		this.mbusBootstrapPassword = mbusBootstrapPassword;
		this.mbusBootstrapSsl = mbusBootstrapSsl;
		this.natsCa = natsCa;
		this.natsClientsDirectorTls = natsClientsDirectorTls;
		this.natsClientsHealthMonitorTls = natsClientsHealthMonitorTls;
		this.natsPassword = natsPassword;
		this.natsServerTls = natsServerTls;
		this.postgresPassword = postgresPassword;
		this.uaaAdminClientSecret = uaaAdminClientSecret;
		this.uaaClientsDirectorToCredhub = uaaClientsDirectorToCredhub;
		this.uaaEncryptionKey1 = uaaEncryptionKey1;
		this.uaaJwtSigningKey = uaaJwtSigningKey;
		this.uaaServiceProviderSsl = uaaServiceProviderSsl;
		this.uaaSsl = uaaSsl;
	}

	public String getAdminPassword() {
		return adminPassword;
	}

	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}

	public String getBlobstoreAgentPassword() {
		return blobstoreAgentPassword;
	}

	public void setBlobstoreAgentPassword(String blobstoreAgentPassword) {
		this.blobstoreAgentPassword = blobstoreAgentPassword;
	}

	public Certificate getBlobstoreCa() {
		return blobstoreCa;
	}

	public void setBlobstoreCa(Certificate blobstoreCa) {
		this.blobstoreCa = blobstoreCa;
	}

	public String getBlobstoreDirectorPassword() {
		return blobstoreDirectorPassword;
	}

	public void setBlobstoreDirectorPassword(String blobstoreDirectorPassword) {
		this.blobstoreDirectorPassword = blobstoreDirectorPassword;
	}

	public Certificate getBlobstoreServerTls() {
		return blobstoreServerTls;
	}

	public void setBlobstoreServerTls(Certificate blobstoreServerTls) {
		this.blobstoreServerTls = blobstoreServerTls;
	}

	public String getCredhubAdminClientSecret() {
		return credhubAdminClientSecret;
	}

	public void setCredhubAdminClientSecret(String credhubAdminClientSecret) {
		this.credhubAdminClientSecret = credhubAdminClientSecret;
	}

	public Certificate getCredhubCa() {
		return credhubCa;
	}

	public void setCredhubCa(Certificate credhubCa) {
		this.credhubCa = credhubCa;
	}

	public String getCredhubCliUserPassword() {
		return credhubCliUserPassword;
	}

	public void setCredhubCliUserPassword(String credhubCliUserPassword) {
		this.credhubCliUserPassword = credhubCliUserPassword;
	}

	public String getCredhubEncryptionPassword() {
		return credhubEncryptionPassword;
	}

	public void setCredhubEncryptionPassword(String credhubEncryptionPassword) {
		this.credhubEncryptionPassword = credhubEncryptionPassword;
	}

	public Certificate getCredhubTls() {
		return credhubTls;
	}

	public void setCredhubTls(Certificate credhubTls) {
		this.credhubTls = credhubTls;
	}

	public Certificate getDefaultCa() {
		return defaultCa;
	}

	public void setDefaultCa(Certificate defaultCa) {
		this.defaultCa = defaultCa;
	}

	public Certificate getDirectorSsl() {
		return directorSsl;
	}

	public void setDirectorSsl(Certificate directorSsl) {
		this.directorSsl = directorSsl;
	}

	public String getHmPassword() {
		return hmPassword;
	}

	public void setHmPassword(String hmPassword) {
		this.hmPassword = hmPassword;
	}

	public Certificate getJumpboxSsh() {
		return jumpboxSsh;
	}

	public void setJumpboxSsh(Certificate jumpboxSsh) {
		this.jumpboxSsh = jumpboxSsh;
	}

	public String getMbusBootstrapPassword() {
		return mbusBootstrapPassword;
	}

	public void setMbusBootstrapPassword(String mbusBootstrapPassword) {
		this.mbusBootstrapPassword = mbusBootstrapPassword;
	}

	public Certificate getMbusBootstrapSsl() {
		return mbusBootstrapSsl;
	}

	public void setMbusBootstrapSsl(Certificate mbusBootstrapSsl) {
		this.mbusBootstrapSsl = mbusBootstrapSsl;
	}

	public Certificate getNatsCa() {
		return natsCa;
	}

	public void setNatsCa(Certificate natsCa) {
		this.natsCa = natsCa;
	}

	public Certificate getNatsClientsDirectorTls() {
		return natsClientsDirectorTls;
	}

	public void setNatsClientsDirectorTls(Certificate natsClientsDirectorTls) {
		this.natsClientsDirectorTls = natsClientsDirectorTls;
	}

	public Certificate getNatsClientsHealthMonitorTls() {
		return natsClientsHealthMonitorTls;
	}

	public void setNatsClientsHealthMonitorTls(Certificate natsClientsHealthMonitorTls) {
		this.natsClientsHealthMonitorTls = natsClientsHealthMonitorTls;
	}

	public String getNatsPassword() {
		return natsPassword;
	}

	public void setNatsPassword(String natsPassword) {
		this.natsPassword = natsPassword;
	}

	public Certificate getNatsServerTls() {
		return natsServerTls;
	}

	public void setNatsServerTls(Certificate natsServerTls) {
		this.natsServerTls = natsServerTls;
	}

	public String getPostgresPassword() {
		return postgresPassword;
	}

	public void setPostgresPassword(String postgresPassword) {
		this.postgresPassword = postgresPassword;
	}

	public String getUaaAdminClientSecret() {
		return uaaAdminClientSecret;
	}

	public void setUaaAdminClientSecret(String uaaAdminClientSecret) {
		this.uaaAdminClientSecret = uaaAdminClientSecret;
	}

	public String getUaaClientsDirectorToCredhub() {
		return uaaClientsDirectorToCredhub;
	}

	public void setUaaClientsDirectorToCredhub(String uaaClientsDirectorToCredhub) {
		this.uaaClientsDirectorToCredhub = uaaClientsDirectorToCredhub;
	}

	public String getUaaEncryptionKey1() {
		return uaaEncryptionKey1;
	}

	public void setUaaEncryptionKey1(String uaaEncryptionKey1) {
		this.uaaEncryptionKey1 = uaaEncryptionKey1;
	}

	public Certificate getUaaJwtSigningKey() {
		return uaaJwtSigningKey;
	}

	public void setUaaJwtSigningKey(Certificate uaaJwtSigningKey) {
		this.uaaJwtSigningKey = uaaJwtSigningKey;
	}

	public Certificate getUaaServiceProviderSsl() {
		return uaaServiceProviderSsl;
	}

	public void setUaaServiceProviderSsl(Certificate uaaServiceProviderSsl) {
		this.uaaServiceProviderSsl = uaaServiceProviderSsl;
	}

	public Certificate getUaaSsl() {
		return uaaSsl;
	}

	public void setUaaSsl(Certificate uaaSsl) {
		this.uaaSsl = uaaSsl;
	}
}
