package com.klef.soa.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.klef.soa.entity.InsurancePolicy;

@Repository
public interface InsurancePolicyRepository extends JpaRepository<InsurancePolicy, Long>
{
    List<InsurancePolicy> findByName(String name);
    List<InsurancePolicy> findByType(String type);
}