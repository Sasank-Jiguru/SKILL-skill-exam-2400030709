package com.klef.soa.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="insurancepolicy_table")
public class InsurancePolicy
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="policy_id")
	private Long insurancePolicyId;

	@Column(nullable=false,length=100)
	private String name;

	@Column(nullable=false,length=50)
	private String type;

	@Column(nullable=false,length=50)
	private String status;

	@Column(nullable=false,length=100)
	private String location;

	@CreationTimestamp
	private LocalDateTime createdAt;

	@UpdateTimestamp
	private LocalDateTime updatedAt;

	public InsurancePolicy() {
	}

	public InsurancePolicy(Long insurancePolicyId, String name, String type, String status, String location) {
		this.insurancePolicyId = insurancePolicyId;
		this.name = name;
		this.type = type;
		this.status = status;
		this.location = location;
	}

	public InsurancePolicy(String name, String type, String status, String location) {
		this.name = name;
		this.type = type;
		this.status = status;
		this.location = location;
	}

	public Long getInsurancePolicyId() {
		return insurancePolicyId;
	}

	public void setInsurancePolicyId(Long insurancePolicyId) {
		this.insurancePolicyId = insurancePolicyId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public String toString() {
		return "InsurancePolicy [insurancePolicyId=" + insurancePolicyId + ", name=" + name + ", type=" + type
				+ ", status=" + status + ", location=" + location + ", createdAt=" + createdAt + ", updatedAt="
				+ updatedAt + "]";
	}
}