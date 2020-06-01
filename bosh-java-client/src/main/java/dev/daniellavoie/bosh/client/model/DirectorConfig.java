package dev.daniellavoie.bosh.client.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DirectorConfig {
	private final String ip;
	private final String cidr;
	private final String gateway;

	@JsonCreator
	public DirectorConfig(@JsonProperty("ip") String ip, @JsonProperty("cidr") String cidr,
			@JsonProperty("gateway") String gateway) {
		this.ip = ip;
		this.cidr = cidr;
		this.gateway = gateway;
	}

	public String getIp() {
		return ip;
	}

	public String getCidr() {
		return cidr;
	}

	public String getGateway() {
		return gateway;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cidr == null) ? 0 : cidr.hashCode());
		result = prime * result + ((gateway == null) ? 0 : gateway.hashCode());
		result = prime * result + ((ip == null) ? 0 : ip.hashCode());
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
		DirectorConfig other = (DirectorConfig) obj;
		if (cidr == null) {
			if (other.cidr != null)
				return false;
		} else if (!cidr.equals(other.cidr))
			return false;
		if (gateway == null) {
			if (other.gateway != null)
				return false;
		} else if (!gateway.equals(other.gateway))
			return false;
		if (ip == null) {
			if (other.ip != null)
				return false;
		} else if (!ip.equals(other.ip))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DirectorConfig [ip=" + ip + ", cidr=" + cidr + ", gateway=" + gateway + "]";
	}
}
