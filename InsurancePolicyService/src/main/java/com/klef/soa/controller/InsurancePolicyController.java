package com.klef.soa.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.klef.soa.security.JwtUtil;
import com.klef.soa.service.InsurancePolicyService;

@RestController
@RequestMapping("/insurancepolicy")
@CrossOrigin("*")
public class InsurancePolicyController
{
    @Autowired
    private InsurancePolicyService insurancePolicyService;

    @Autowired
    private JwtUtil jwtUtil;

    // Home Endpoint
    @GetMapping("/")
    public ResponseEntity<String> home()
    {
        return ResponseEntity.ok("Insurance Policy Microservice Running - ID: 2400030709");
    }

    // Public Test API (Requirement for integration tests)
    @GetMapping("/test")
    public ResponseEntity<String> testApi()
    {
        return ResponseEntity.ok("Test API Call");
    }

    // JWT Login Endpoint
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> loginRequest)
    {
        String username = loginRequest.getOrDefault("email", loginRequest.get("username"));
        if (username == null || username.isBlank()) {
            username = "admin@gmail.com";
        }

        String token = jwtUtil.generateToken(username);

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("message", "Login successful");
        response.put("username", username);

        return ResponseEntity.ok(response);
    }

    // Insert (Create)
    @PostMapping("/add")
    public ResponseEntity<String> addInsurancePolicy(@RequestBody InsurancePolicy policy)
    {
        String message = insurancePolicyService.addInsurancePolicy(policy);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    // Display All (Supports both /viewall and /displayall)
    @GetMapping({"/displayall"})
    public ResponseEntity<List<InsurancePolicy>> viewAllPolicies()
    {
        List<InsurancePolicy> policyList = insurancePolicyService.viewAllPolicies();
        return new ResponseEntity<>(policyList, HttpStatus.OK);
    }

    // Display By ID (Supports both /view/{id} and /display/{id})
    @GetMapping({"/display/{id}"})
    public ResponseEntity<?> viewPolicyById(@PathVariable Long id)
    {
        InsurancePolicy policy = insurancePolicyService.viewPolicyById(id);

        if(policy != null)
        {
            return new ResponseEntity<>(policy, HttpStatus.OK);
        }

        return new ResponseEntity<>("Policy Not Found", HttpStatus.NOT_FOUND);
    }

    // Update (Supports both /update and /update/{id})
    @PutMapping({"/update/{id}"})
    public ResponseEntity<String> updateInsurancePolicy(
            @PathVariable(required = false) Long id,
            @RequestBody InsurancePolicy policy)
    {
        if (id != null && policy.getInsurancePolicyId() == null) {
            policy.setInsurancePolicyId(id);
        }
        String message = insurancePolicyService.updateInsurancePolicy(policy);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    // Delete
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteInsurancePolicy(@PathVariable Long id)
    {
        String message = insurancePolicyService.deleteInsurancePolicy(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    // Custom Finder: findByName
    @GetMapping("/findbyname/{name}")
    public ResponseEntity<List<InsurancePolicy>> findByName(@PathVariable String name)
    {
        List<InsurancePolicy> policyList = insurancePolicyService.findByName(name);
        return new ResponseEntity<>(policyList, HttpStatus.OK);
    }

    // Custom Finder: findByType
    @GetMapping("/findbytype/{type}")
    public ResponseEntity<List<InsurancePolicy>> findByType(@PathVariable String type)
    {
        List<InsurancePolicy> policyList = insurancePolicyService.findByType(type);
        return new ResponseEntity<>(policyList, HttpStatus.OK);
    }
}