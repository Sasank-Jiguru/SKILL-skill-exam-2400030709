package com.klef.soa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "insurance_policies")
public class InsurancePolicy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long insurancePolicyId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String type;

    private String description;

    private String status;

    // Default Constructor
    public InsurancePolicy() {
    }

    // Parameterized Constructor
    public InsurancePolicy(Long insurancePolicyId, String name, String type, String description, String status) {
        this.insurancePolicyId = insurancePolicyId;
        this.name = name;
        this.type = type;
        this.description = description;
        this.status = status;
    }

    // Constructor without ID
    public InsurancePolicy(String name, String type, String description, String status) {
        this.name = name;
        this.type = type;
        this.description = description;
        this.status = status;
    }

    // Getters and Setters
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}