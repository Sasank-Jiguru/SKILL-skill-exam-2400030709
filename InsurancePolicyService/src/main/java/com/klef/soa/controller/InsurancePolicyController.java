package com.klef.soa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.klef.soa.entity.InsurancePolicy;
import com.klef.soa.service.InsurancePolicyService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/insurancepolicy")
@CrossOrigin("*")
@Tag(name = "Insurance Policy Controller", description = "CRUD and search operations for Insurance Policies")
public class InsurancePolicyController {

    @Autowired
    private InsurancePolicyService insurancePolicyService;

    // Home Endpoint
    @GetMapping("/")
    @Operation(summary = "Microservice health status")
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("Insurance Policy Microservice Running - ID: 2400030709");
    }

    // Public Test API
    @GetMapping("/test")
    @Operation(summary = "Test API Endpoint")
    public ResponseEntity<String> testApi() {
        return ResponseEntity.ok("Test API Call");
    }

    // Insert (Create)
    @PostMapping("/add")
    @Operation(summary = "Add a new insurance policy")
    public ResponseEntity<String> addInsurancePolicy(@RequestBody InsurancePolicy policy) {
        String message = insurancePolicyService.addInsurancePolicy(policy);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    // Display All
    @GetMapping("/displayall")
    @Operation(summary = "Display all insurance policies")
    public ResponseEntity<List<InsurancePolicy>> viewAllPolicies() {
        List<InsurancePolicy> policyList = insurancePolicyService.viewAllPolicies();
        return new ResponseEntity<>(policyList, HttpStatus.OK);
    }

    // Display By ID
    @GetMapping("/display/{id}")
    @Operation(summary = "Display policy by ID")
    public ResponseEntity<?> viewPolicyById(@PathVariable Long id) {
        InsurancePolicy policy = insurancePolicyService.viewPolicyById(id);
        if (policy != null) {
            return new ResponseEntity<>(policy, HttpStatus.OK);
        }
        return new ResponseEntity<>("Policy Not Found", HttpStatus.NOT_FOUND);
    }

    // Update
    @PutMapping("/update/{id}")
    @Operation(summary = "Update an existing policy by ID")
    public ResponseEntity<String> updateInsurancePolicy(
            @PathVariable Long id,
            @RequestBody InsurancePolicy policy) {
        policy.setInsurancePolicyId(id);
        String message = insurancePolicyService.updateInsurancePolicy(policy);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    // Delete
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete policy by ID")
    public ResponseEntity<String> deleteInsurancePolicy(@PathVariable Long id) {
        String message = insurancePolicyService.deleteInsurancePolicy(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    // Custom Finder: findByName
    @GetMapping("/findbyname/{name}")
    @Operation(summary = "Find policies by name")
    public ResponseEntity<List<InsurancePolicy>> findByName(@PathVariable String name) {
        List<InsurancePolicy> policyList = insurancePolicyService.findByName(name);
        return new ResponseEntity<>(policyList, HttpStatus.OK);
    }

    // Custom Finder: findByType
    @GetMapping("/findbytype/{type}")
    @Operation(summary = "Find policies by type")
    public ResponseEntity<List<InsurancePolicy>> findByType(@PathVariable String type) {
        List<InsurancePolicy> policyList = insurancePolicyService.findByType(type);
        return new ResponseEntity<>(policyList, HttpStatus.OK);
    }
}