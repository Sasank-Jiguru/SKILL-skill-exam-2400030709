package com.klef.soa.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.klef.soa.entity.InsurancePolicy;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class InsurancePolicyRepositoryTest {

    @Autowired
    private InsurancePolicyRepository repository;

    // Test 1 – Find All: Verify returned list is not null and contains data
    @Test
    void testFindAllPolicies() {
        System.out.println("=== Test 1: Find All Policies ===");
        InsurancePolicy policy = new InsurancePolicy("Health Shield", "Health", "Comprehensive health cover", "Active");
        repository.save(policy);

        List<InsurancePolicy> policies = repository.findAll();
        assertNotNull(policies);
        assertFalse(policies.isEmpty());
        System.out.println("Total Policies in DB: " + policies.size());
    }

    // Test 2 – Find by ID: Verify existing ID matches and non-existing returns empty
    @Test
    void testFindPolicyById() {
        System.out.println("=== Test 2: Find Policy By ID ===");
        InsurancePolicy policy = new InsurancePolicy("Motor Care", "Vehicle", "Vehicle accidental cover", "Active");
        InsurancePolicy saved = repository.save(policy);

        // Check existing record
        Optional<InsurancePolicy> found = repository.findById(saved.getInsurancePolicyId());
        assertTrue(found.isPresent());
        assertEquals(saved.getInsurancePolicyId(), found.get().getInsurancePolicyId());
        System.out.println("Found Policy Name: " + found.get().getName());

        // Check non-existing record
        Optional<InsurancePolicy> notFound = repository.findById(999999L);
        assertFalse(notFound.isPresent());
    }

    // Test 3 – Find by Derived Query: Test findByName and findByType
    @Test
    void testFindByDerivedQueries() {
        System.out.println("=== Test 3: Find by Derived Queries ===");
        InsurancePolicy policy = new InsurancePolicy("Term Plan Pro", "Life", "Term insurance plan", "Active");
        repository.save(policy);

        // Test findByNameIgnoreCase
        List<InsurancePolicy> byName = repository.findByNameIgnoreCase("Term Plan Pro");
        assertNotNull(byName);
        assertFalse(byName.isEmpty());
        assertEquals("Term Plan Pro", byName.get(0).getName());

        // Test findByTypeIgnoreCase
        List<InsurancePolicy> byType = repository.findByTypeIgnoreCase("Life");
        assertNotNull(byType);
        assertFalse(byType.isEmpty());
        assertEquals("Life", byType.get(0).getType());
    }

    // Test 4 – Count: Deterministic record count verification
    @Test
    void testRecordCount() {
        System.out.println("=== Test 4: Record Count ===");
        long initialCount = repository.count();
        InsurancePolicy policy = new InsurancePolicy("Travel Safe", "Travel", "International coverage", "Active");
        repository.save(policy);

        long updatedCount = repository.count();
        assertEquals(initialCount + 1, updatedCount);
        System.out.println("Record count verified: " + updatedCount);
    }

    // Test 5 – Entity Details / Property Validation: Verify required properties are not null
    @Test
    void testEntityPropertyValidation() {
        System.out.println("=== Test 5: Entity Details Validation ===");
        InsurancePolicy policy = new InsurancePolicy("Home Secure", "Property", "Fire and theft cover", "Active");
        InsurancePolicy saved = repository.save(policy);

        Optional<InsurancePolicy> retrieved = repository.findById(saved.getInsurancePolicyId());
        assertTrue(retrieved.isPresent());

        InsurancePolicy p = retrieved.get();
        assertNotNull(p.getInsurancePolicyId());
        assertNotNull(p.getName());
        assertNotNull(p.getType());
        assertNotNull(p.getDescription());
        assertNotNull(p.getStatus());
        assertEquals("Property", p.getType());
    }
}