package com.klef.soa.service;

import java.util.List;

import com.klef.soa.entity.InsurancePolicy;

public interface InsurancePolicyService
{
    public String addInsurancePolicy(InsurancePolicy policy);

    public List<InsurancePolicy> viewAllPolicies();

    public InsurancePolicy viewPolicyById(Long id);

    public String updateInsurancePolicy(InsurancePolicy policy);

    public String deleteInsurancePolicy(Long id);

    public List<InsurancePolicy> findByName(String name);

    public List<InsurancePolicy> findByType(String type);
}