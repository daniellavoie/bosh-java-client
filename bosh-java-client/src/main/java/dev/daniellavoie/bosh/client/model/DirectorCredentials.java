package dev.daniellavoie.bosh.client.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonInclude(Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
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
	public DirectorCredentials(@JsonProperty("admin_password") String adminPassword,
			@JsonProperty("blobstore_agent_password") String blobstoreAgentPassword,
			@JsonProperty("blobstore_ca") Certificate blobstoreCa,
			@JsonProperty("blobstore_director_password") String blobstoreDirectorPassword,
			@JsonProperty("blobstore_server_tls") Certificate blobstoreServerTls,
			@JsonProperty("credhub_admin_client_secret") String credhubAdminClientSecret,
			@JsonProperty("credhub_ca") Certificate credhubCa,
			@JsonProperty("credhub_cli_user_password") String credhubCliUserPassword,
			@JsonProperty("credhub_encryption_password") String credhubEncryptionPassword,
			@JsonProperty("credhub_tls") Certificate credhubTls, @JsonProperty("default_ca") Certificate defaultCa,
			@JsonProperty("director_ssl") Certificate directorSsl, @JsonProperty("hm_password") String hmPassword,
			@JsonProperty("jumpbox_ssh") Certificate jumpboxSsh,
			@JsonProperty("mbus_bootstrap_password") String mbusBootstrapPassword,
			@JsonProperty("mbus_bootstrap_ssl") Certificate mbusBootstrapSsl,
			@JsonProperty("nats_ca") Certificate natsCa,
			@JsonProperty("nats_clients_director_tls") Certificate natsClientsDirectorTls,
			@JsonProperty("nats_clients_health_monitor_tls") Certificate natsClientsHealthMonitorTls,
			@JsonProperty("nats_password") String natsPassword,
			@JsonProperty("nats_server_tls") Certificate natsServerTls,
			@JsonProperty("postgres_password") String postgresPassword,
			@JsonProperty("uaa_admin_client_secret") String uaaAdminClientSecret,
			@JsonProperty("uaa_clients_director_to_credhub") String uaaClientsDirectorToCredhub,
			@JsonProperty("uaa_encryption_key_1") String uaaEncryptionKey1,
			@JsonProperty("uaa_jwt_signing_key") Certificate uaaJwtSigningKey,
			@JsonProperty("uaa_service_provider_ssl") Certificate uaaServiceProviderSsl,
			@JsonProperty("uaa_ssl") Certificate uaaSsl) {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((adminPassword == null) ? 0 : adminPassword.hashCode());
		result = prime * result + ((blobstoreAgentPassword == null) ? 0 : blobstoreAgentPassword.hashCode());
		result = prime * result + ((blobstoreCa == null) ? 0 : blobstoreCa.hashCode());
		result = prime * result + ((blobstoreDirectorPassword == null) ? 0 : blobstoreDirectorPassword.hashCode());
		result = prime * result + ((blobstoreServerTls == null) ? 0 : blobstoreServerTls.hashCode());
		result = prime * result + ((credhubAdminClientSecret == null) ? 0 : credhubAdminClientSecret.hashCode());
		result = prime * result + ((credhubCa == null) ? 0 : credhubCa.hashCode());
		result = prime * result + ((credhubCliUserPassword == null) ? 0 : credhubCliUserPassword.hashCode());
		result = prime * result + ((credhubEncryptionPassword == null) ? 0 : credhubEncryptionPassword.hashCode());
		result = prime * result + ((credhubTls == null) ? 0 : credhubTls.hashCode());
		result = prime * result + ((defaultCa == null) ? 0 : defaultCa.hashCode());
		result = prime * result + ((directorSsl == null) ? 0 : directorSsl.hashCode());
		result = prime * result + ((hmPassword == null) ? 0 : hmPassword.hashCode());
		result = prime * result + ((jumpboxSsh == null) ? 0 : jumpboxSsh.hashCode());
		result = prime * result + ((mbusBootstrapPassword == null) ? 0 : mbusBootstrapPassword.hashCode());
		result = prime * result + ((mbusBootstrapSsl == null) ? 0 : mbusBootstrapSsl.hashCode());
		result = prime * result + ((natsCa == null) ? 0 : natsCa.hashCode());
		result = prime * result + ((natsClientsDirectorTls == null) ? 0 : natsClientsDirectorTls.hashCode());
		result = prime * result + ((natsClientsHealthMonitorTls == null) ? 0 : natsClientsHealthMonitorTls.hashCode());
		result = prime * result + ((natsPassword == null) ? 0 : natsPassword.hashCode());
		result = prime * result + ((natsServerTls == null) ? 0 : natsServerTls.hashCode());
		result = prime * result + ((postgresPassword == null) ? 0 : postgresPassword.hashCode());
		result = prime * result + ((uaaAdminClientSecret == null) ? 0 : uaaAdminClientSecret.hashCode());
		result = prime * result + ((uaaClientsDirectorToCredhub == null) ? 0 : uaaClientsDirectorToCredhub.hashCode());
		result = prime * result + ((uaaEncryptionKey1 == null) ? 0 : uaaEncryptionKey1.hashCode());
		result = prime * result + ((uaaJwtSigningKey == null) ? 0 : uaaJwtSigningKey.hashCode());
		result = prime * result + ((uaaServiceProviderSsl == null) ? 0 : uaaServiceProviderSsl.hashCode());
		result = prime * result + ((uaaSsl == null) ? 0 : uaaSsl.hashCode());
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
		DirectorCredentials other = (DirectorCredentials) obj;
		if (adminPassword == null) {
			if (other.adminPassword != null)
				return false;
		} else if (!adminPassword.equals(other.adminPassword))
			return false;
		if (blobstoreAgentPassword == null) {
			if (other.blobstoreAgentPassword != null)
				return false;
		} else if (!blobstoreAgentPassword.equals(other.blobstoreAgentPassword))
			return false;
		if (blobstoreCa == null) {
			if (other.blobstoreCa != null)
				return false;
		} else if (!blobstoreCa.equals(other.blobstoreCa))
			return false;
		if (blobstoreDirectorPassword == null) {
			if (other.blobstoreDirectorPassword != null)
				return false;
		} else if (!blobstoreDirectorPassword.equals(other.blobstoreDirectorPassword))
			return false;
		if (blobstoreServerTls == null) {
			if (other.blobstoreServerTls != null)
				return false;
		} else if (!blobstoreServerTls.equals(other.blobstoreServerTls))
			return false;
		if (credhubAdminClientSecret == null) {
			if (other.credhubAdminClientSecret != null)
				return false;
		} else if (!credhubAdminClientSecret.equals(other.credhubAdminClientSecret))
			return false;
		if (credhubCa == null) {
			if (other.credhubCa != null)
				return false;
		} else if (!credhubCa.equals(other.credhubCa))
			return false;
		if (credhubCliUserPassword == null) {
			if (other.credhubCliUserPassword != null)
				return false;
		} else if (!credhubCliUserPassword.equals(other.credhubCliUserPassword))
			return false;
		if (credhubEncryptionPassword == null) {
			if (other.credhubEncryptionPassword != null)
				return false;
		} else if (!credhubEncryptionPassword.equals(other.credhubEncryptionPassword))
			return false;
		if (credhubTls == null) {
			if (other.credhubTls != null)
				return false;
		} else if (!credhubTls.equals(other.credhubTls))
			return false;
		if (defaultCa == null) {
			if (other.defaultCa != null)
				return false;
		} else if (!defaultCa.equals(other.defaultCa))
			return false;
		if (directorSsl == null) {
			if (other.directorSsl != null)
				return false;
		} else if (!directorSsl.equals(other.directorSsl))
			return false;
		if (hmPassword == null) {
			if (other.hmPassword != null)
				return false;
		} else if (!hmPassword.equals(other.hmPassword))
			return false;
		if (jumpboxSsh == null) {
			if (other.jumpboxSsh != null)
				return false;
		} else if (!jumpboxSsh.equals(other.jumpboxSsh))
			return false;
		if (mbusBootstrapPassword == null) {
			if (other.mbusBootstrapPassword != null)
				return false;
		} else if (!mbusBootstrapPassword.equals(other.mbusBootstrapPassword))
			return false;
		if (mbusBootstrapSsl == null) {
			if (other.mbusBootstrapSsl != null)
				return false;
		} else if (!mbusBootstrapSsl.equals(other.mbusBootstrapSsl))
			return false;
		if (natsCa == null) {
			if (other.natsCa != null)
				return false;
		} else if (!natsCa.equals(other.natsCa))
			return false;
		if (natsClientsDirectorTls == null) {
			if (other.natsClientsDirectorTls != null)
				return false;
		} else if (!natsClientsDirectorTls.equals(other.natsClientsDirectorTls))
			return false;
		if (natsClientsHealthMonitorTls == null) {
			if (other.natsClientsHealthMonitorTls != null)
				return false;
		} else if (!natsClientsHealthMonitorTls.equals(other.natsClientsHealthMonitorTls))
			return false;
		if (natsPassword == null) {
			if (other.natsPassword != null)
				return false;
		} else if (!natsPassword.equals(other.natsPassword))
			return false;
		if (natsServerTls == null) {
			if (other.natsServerTls != null)
				return false;
		} else if (!natsServerTls.equals(other.natsServerTls))
			return false;
		if (postgresPassword == null) {
			if (other.postgresPassword != null)
				return false;
		} else if (!postgresPassword.equals(other.postgresPassword))
			return false;
		if (uaaAdminClientSecret == null) {
			if (other.uaaAdminClientSecret != null)
				return false;
		} else if (!uaaAdminClientSecret.equals(other.uaaAdminClientSecret))
			return false;
		if (uaaClientsDirectorToCredhub == null) {
			if (other.uaaClientsDirectorToCredhub != null)
				return false;
		} else if (!uaaClientsDirectorToCredhub.equals(other.uaaClientsDirectorToCredhub))
			return false;
		if (uaaEncryptionKey1 == null) {
			if (other.uaaEncryptionKey1 != null)
				return false;
		} else if (!uaaEncryptionKey1.equals(other.uaaEncryptionKey1))
			return false;
		if (uaaJwtSigningKey == null) {
			if (other.uaaJwtSigningKey != null)
				return false;
		} else if (!uaaJwtSigningKey.equals(other.uaaJwtSigningKey))
			return false;
		if (uaaServiceProviderSsl == null) {
			if (other.uaaServiceProviderSsl != null)
				return false;
		} else if (!uaaServiceProviderSsl.equals(other.uaaServiceProviderSsl))
			return false;
		if (uaaSsl == null) {
			if (other.uaaSsl != null)
				return false;
		} else if (!uaaSsl.equals(other.uaaSsl))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DirectorCredentials [adminPassword=" + adminPassword + ", blobstoreAgentPassword="
				+ blobstoreAgentPassword + ", blobstoreCa=" + blobstoreCa + ", blobstoreDirectorPassword="
				+ blobstoreDirectorPassword + ", blobstoreServerTls=" + blobstoreServerTls
				+ ", credhubAdminClientSecret=" + credhubAdminClientSecret + ", credhubCa=" + credhubCa
				+ ", credhubCliUserPassword=" + credhubCliUserPassword + ", credhubEncryptionPassword="
				+ credhubEncryptionPassword + ", credhubTls=" + credhubTls + ", defaultCa=" + defaultCa
				+ ", directorSsl=" + directorSsl + ", hmPassword=" + hmPassword + ", jumpboxSsh=" + jumpboxSsh
				+ ", mbusBootstrapPassword=" + mbusBootstrapPassword + ", mbusBootstrapSsl=" + mbusBootstrapSsl
				+ ", natsCa=" + natsCa + ", natsClientsDirectorTls=" + natsClientsDirectorTls
				+ ", natsClientsHealthMonitorTls=" + natsClientsHealthMonitorTls + ", natsPassword=" + natsPassword
				+ ", natsServerTls=" + natsServerTls + ", postgresPassword=" + postgresPassword
				+ ", uaaAdminClientSecret=" + uaaAdminClientSecret + ", uaaClientsDirectorToCredhub="
				+ uaaClientsDirectorToCredhub + ", uaaEncryptionKey1=" + uaaEncryptionKey1 + ", uaaJwtSigningKey="
				+ uaaJwtSigningKey + ", uaaServiceProviderSsl=" + uaaServiceProviderSsl + ", uaaSsl=" + uaaSsl + "]";
	}
}
