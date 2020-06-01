package dev.daniellavoie.bosh.client.model;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Vm {
	private final String agent_id;
	private final String cid;
	private final String job;
	private final int index;
	private final String id;
	private final String az;
	private final List<String> ips;
	private final LocalDateTime vm_created_at;
	private final boolean active;

	@JsonCreator
	public Vm(@JsonProperty("agent_id") String agentId, @JsonProperty("cid") String cid,
			@JsonProperty("job") String job, @JsonProperty("index") int index, @JsonProperty("id") String id,
			@JsonProperty("az") String az, @JsonProperty("ips") List<String> ips,
			@JsonProperty("vm_created_at") LocalDateTime vmCreatedAt, @JsonProperty("active") boolean active) {
		this.agent_id = agentId;
		this.cid = cid;
		this.job = job;
		this.index = index;
		this.id = id;
		this.az = az;
		this.ips = ips;
		this.vm_created_at = vmCreatedAt;
		this.active = active;
	}

	public String getAgentId() {
		return agent_id;
	}

	public String getCid() {
		return cid;
	}

	public String getJob() {
		return job;
	}

	public int getIndex() {
		return index;
	}

	public String getId() {
		return id;
	}

	public String getAz() {
		return az;
	}

	public List<String> getIps() {
		return ips;
	}

	public LocalDateTime getVmCreatedAt() {
		return vm_created_at;
	}

	public boolean isActive() {
		return active;
	}
}
