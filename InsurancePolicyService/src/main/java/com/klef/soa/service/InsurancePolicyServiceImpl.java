package com.klef.soa.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.klef.soa.entity.InsurancePolicy;
import com.klef.soa.repository.InsurancePolicyRepository;
import com.klef.soa.service.InsurancePolicyService;

@Service
public class InsurancePolicyServiceImpl implements InsurancePolicyService {

    private final InsurancePolicyRepository insurancePolicyRepository;

    public InsurancePolicyServiceImpl(InsurancePolicyRepository insurancePolicyRepository) {
        this.insurancePolicyRepository = insurancePolicyRepository;
    }

    @Override
    public String addInsurancePolicy(InsurancePolicy policy) {
        insurancePolicyRepository.save(policy);
        return "Insurance Policy added successfully";
    }

    @Override
    public List<InsurancePolicy> viewAllPolicies() {
        return insurancePolicyRepository.findAll();
    }

    @Override
    public InsurancePolicy viewPolicyById(Long id) {
        return insurancePolicyRepository.findById(id).orElse(null);
    }

    @Override
    public String updateInsurancePolicy(InsurancePolicy policy) {
        Optional<InsurancePolicy> existingOptional = insurancePolicyRepository.findById(policy.getInsurancePolicyId());
        if (existingOptional.isPresent()) {
            InsurancePolicy existing = existingOptional.get();
            existing.setName(policy.getName());
            existing.setType(policy.getType());
            existing.setDescription(policy.getDescription());
            existing.setStatus(policy.getStatus());
            insurancePolicyRepository.save(existing);
            return "Insurance Policy updated successfully";
        }
        return "Insurance Policy Not Found";
    }

    @Override
    public String deleteInsurancePolicy(Long id) {
        Optional<InsurancePolicy> existingOptional = insurancePolicyRepository.findById(id);
        if (existingOptional.isPresent()) {
            insurancePolicyRepository.deleteById(id);
            return "Insurance Policy deleted successfully";
        }
        return "Insurance Policy Not Found";
    }

    @Override
    public List<InsurancePolicy> findByName(String name) {
        return insurancePolicyRepository.findByNameIgnoreCase(name);
    }

    @Override
    public List<InsurancePolicy> findByType(String type) {
        return insurancePolicyRepository.findByTypeIgnoreCase(type);
    }
}