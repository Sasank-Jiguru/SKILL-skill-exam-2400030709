package com.klef.soa.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klef.soa.entity.InsurancePolicy;
import com.klef.soa.repository.InsurancePolicyRepository;

@Service
public class InsurancePolicyServiceImpl implements InsurancePolicyService
{
    @Autowired
    private InsurancePolicyRepository insurancePolicyRepository;

    @Override
    public String addInsurancePolicy(InsurancePolicy policy)
    {
        insurancePolicyRepository.save(policy);
        return "Insurance Policy Added Successfully";
    }

    @Override
    public List<InsurancePolicy> viewAllPolicies()
    {
        return insurancePolicyRepository.findAll();
    }

    @Override
    public InsurancePolicy viewPolicyById(Long id)
    {
        Optional<InsurancePolicy> optional = insurancePolicyRepository.findById(id);

        if(optional.isPresent())
        {
            return optional.get();
        }

        return null;
    }

    @Override
    public String updateInsurancePolicy(InsurancePolicy policy)
    {
        Optional<InsurancePolicy> optional = insurancePolicyRepository.findById(policy.getInsurancePolicyId());

        if(optional.isPresent())
        {
            InsurancePolicy ip = optional.get();

            ip.setName(policy.getName());
            ip.setType(policy.getType());
            ip.setStatus(policy.getStatus());
            ip.setLocation(policy.getLocation());

            insurancePolicyRepository.save(ip);

            return "Insurance Policy Updated Successfully";
        }

        return "Insurance Policy Not Found";
    }

    @Override
    public String deleteInsurancePolicy(Long id)
    {
        Optional<InsurancePolicy> optional = insurancePolicyRepository.findById(id);

        if(optional.isPresent())
        {
            insurancePolicyRepository.deleteById(id);
            return "Insurance Policy Deleted Successfully";
        }

        return "Insurance Policy Not Found";
    }

    @Override
    public List<InsurancePolicy> findByName(String name)
    {
        return insurancePolicyRepository.findByName(name);
    }

    @Override
    public List<InsurancePolicy> findByType(String type)
    {
        return insurancePolicyRepository.findByType(type);
    }
}