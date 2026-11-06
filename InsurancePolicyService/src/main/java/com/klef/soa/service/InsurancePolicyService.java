package com.klef.soa.service;

import java.util.List;
import com.klef.soa.entity.InsurancePolicy;

public interface InsurancePolicyService {
    String addInsurancePolicy(InsurancePolicy policy);
    List<InsurancePolicy> viewAllPolicies();
    InsurancePolicy viewPolicyById(Long id);
    String updateInsurancePolicy(InsurancePolicy policy);
    String deleteInsurancePolicy(Long id);
    List<InsurancePolicy> findByName(String name);
    List<InsurancePolicy> findByType(String type);
}