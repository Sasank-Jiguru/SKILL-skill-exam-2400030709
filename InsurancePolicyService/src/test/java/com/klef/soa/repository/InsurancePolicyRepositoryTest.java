package com.klef.soa.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.test.context.ContextConfiguration;

import com.klef.soa.TaskApplication;
import com.klef.soa.entity.InsurancePolicy;

@DataJpaTest
@ContextConfiguration(classes = TaskApplication.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class InsurancePolicyRepositoryTest {

    @Autowired
    private InsurancePolicyRepository repository;

    @Test
    void testSaveInsurancePolicy() {
        System.out.println("=== Test: Save Insurance Policy ===");
        InsurancePolicy policy = new InsurancePolicy("Jeevan Anand", "Life", "ACTIVE", "Hyderabad");
        InsurancePolicy saved = repository.save(policy);

        assertNotNull(saved);
        assertNotNull(saved.getInsurancePolicyId());
        assertEquals("Jeevan Anand", saved.getName());
        assertEquals("Life", saved.getType());
        System.out.println("Saved Policy ID: " + saved.getInsurancePolicyId());
    }

    @Test
    void testFindAllPolicies() {
        System.out.println("=== Test: Find All Policies ===");
        // Ensure at least one policy exists
        InsurancePolicy policy = new InsurancePolicy("Health Shield", "Health", "ACTIVE", "Vijayawada");
        repository.save(policy);

        List<InsurancePolicy> policies = repository.findAll();
        assertNotNull(policies);
        assertFalse(policies.isEmpty());
        System.out.println("Total Policies in DB: " + policies.size());
    }

    @Test
    void testFindPolicyById() {
        System.out.println("=== Test: Find Policy By ID ===");
        InsurancePolicy policy = new InsurancePolicy("Motor Care", "Vehicle", "ACTIVE", "Guntur");
        InsurancePolicy saved = repository.save(policy);

        Optional<InsurancePolicy> found = repository.findById(saved.getInsurancePolicyId());
        assertTrue(found.isPresent());
        assertEquals(saved.getInsurancePolicyId(), found.get().getInsurancePolicyId());
        System.out.println("Found Policy Name: " + found.get().getName());
    }

    @Test
    void testFindPolicyByName() {
        System.out.println("=== Test: Find Policy By Name ===");
        InsurancePolicy policy = new InsurancePolicy("Term Plan Pro", "Life", "ACTIVE", "Bangalore");
        repository.save(policy);

        List<InsurancePolicy> foundList = repository.findByName("Term Plan Pro");
        assertNotNull(foundList);
        assertFalse(foundList.isEmpty());
        assertEquals("Term Plan Pro", foundList.get(0).getName());
        System.out.println("Found Policies with Name 'Term Plan Pro': " + foundList.size());
    }

    @Test
    void testFindPolicyByType() {
        System.out.println("=== Test: Find Policy By Type ===");
        InsurancePolicy policy = new InsurancePolicy("Home Secure", "Property", "ACTIVE", "Chennai");
        repository.save(policy);

        List<InsurancePolicy> foundList = repository.findByType("Property");
        assertNotNull(foundList);
        assertFalse(foundList.isEmpty());
        assertEquals("Property", foundList.get(0).getType());
        System.out.println("Found Policies with Type 'Property': " + foundList.size());
    }

    @Test
    void testUpdateInsurancePolicy() {
        System.out.println("=== Test: Update Insurance Policy ===");
        InsurancePolicy policy = new InsurancePolicy("Family Floater", "Health", "PENDING", "Hyderabad");
        InsurancePolicy saved = repository.save(policy);

        saved.setStatus("ACTIVE");
        saved.setLocation("Amaravati");
        InsurancePolicy updated = repository.save(saved);

        assertEquals("ACTIVE", updated.getStatus());
        assertEquals("Amaravati", updated.getLocation());
        System.out.println("Updated Status: " + updated.getStatus());
    }

    @Test
    void testDeleteInsurancePolicy() {
        System.out.println("=== Test: Delete Insurance Policy ===");
        InsurancePolicy policy = new InsurancePolicy("Temp Travel Care", "Travel", "INACTIVE", "Delhi");
        InsurancePolicy saved = repository.save(policy);
        Long id = saved.getInsurancePolicyId();

        repository.deleteById(id);
        Optional<InsurancePolicy> deleted = repository.findById(id);
        assertFalse(deleted.isPresent());
        System.out.println("Policy ID " + id + " deleted successfully");
    }

    @Test
    void testDatabaseConnection() {
        System.out.println("=== Test: Database Connection ===");
        long count = repository.count();
        assertTrue(count >= 0);
        System.out.println("Database Connection Verified! Total Policies: " + count);
    }

    @Test
    void testPolicyDetails() {
        System.out.println("=== Test: Policy Details Validation ===");
        InsurancePolicy policy = new InsurancePolicy("Senior Citizen Health", "Health", "ACTIVE", "Mumbai");
        InsurancePolicy saved = repository.save(policy);

        Optional<InsurancePolicy> found = repository.findById(saved.getInsurancePolicyId());
        assertTrue(found.isPresent());
        InsurancePolicy p = found.get();

        assertNotNull(p.getName());
        assertNotNull(p.getType());
        assertNotNull(p.getStatus());
        assertNotNull(p.getLocation());
        System.out.println("Policy Details - Name: " + p.getName() + ", Type: " + p.getType() + ", Status: " + p.getStatus());
    }

    @Test
    void testPolicyStatus() {
        System.out.println("=== Test: Policy Status ===");
        InsurancePolicy policy = new InsurancePolicy("Comprehensive Car", "Vehicle", "ACTIVE", "Pune");
        InsurancePolicy saved = repository.save(policy);

        Optional<InsurancePolicy> found = repository.findById(saved.getInsurancePolicyId());
        assertTrue(found.isPresent());
        assertEquals("ACTIVE", found.get().getStatus());
        System.out.println("Policy Status Verified: " + found.get().getStatus());
    }

    @Test
    void testTotalPolicyCount() {
        System.out.println("=== Test: Total Policy Count ===");
        long count = repository.count();
        assertTrue(count >= 0);
        System.out.println("Total Policy Count: " + count);
    }
}
