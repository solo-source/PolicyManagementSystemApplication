package com.pms.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.pms.entity.Customer;
import com.pms.entity.Policy;
import com.pms.entity.Scheme;
import com.pms.exception.InvalidEntityException;
import com.pms.service.PolicyService;

@RestController
@RequestMapping("/policy")
@CrossOrigin(origins = "UI_URL")
public class PolicyController {
    
    @Autowired
    private PolicyService service;
    
    @GetMapping("/viewPolicies")
    public List<Policy> viewPolicies(){
        return service.viewPolicies();
    }

    @PostMapping("/viewPolicyDetails")
    public Optional<Policy> viewPolicyDetails(@RequestBody Policy p){
        return service.viewPolicyDetails(p);
    }
    
    @PostMapping("/viewCustPolicies")
    public List<Policy> viewCustPolicies(@RequestBody Customer cust){
        return service.viewCustPolicies(cust.getId());
    }
    
    @PostMapping("/viewSchemePolicies")
    public List<Policy> viewSchemePolicies(@RequestBody Scheme sc){
        return service.viewSchemePolicies(sc.getId());
    }
    
    @PostMapping("/create")
    public ResponseEntity<Policy> createPolicy(@RequestBody Policy policy) throws InvalidEntityException {
        Policy createdPolicy = service.createPolicy(policy);
        return ResponseEntity.ok(createdPolicy);
    }
    
    @PutMapping("/update/{id}")
    public ResponseEntity<Policy> updatePolicy(@PathVariable String id, 
                                               @RequestBody Policy policy) throws InvalidEntityException {
        Policy updatedPolicy = service.updatePolicy(id, policy);
        return ResponseEntity.ok(updatedPolicy);
    }
    
    @GetMapping("/{policyId}")
    public ResponseEntity<Policy> getPolicyByPolicyId(@PathVariable String policyId) throws InvalidEntityException {
        Policy policy = service.getPolicyByPolicyId(policyId);
        return ResponseEntity.ok(policy);
    }
    
    @GetMapping("/scheme/{schemeName}")
    public ResponseEntity<List<Policy>> getPoliciesByScheme(@PathVariable String schemeName) {
        List<Policy> policies = service.getPoliciesBySchemeName(schemeName);
        return ResponseEntity.ok(policies);
    }
    
    @GetMapping("/date/{date}")
    public ResponseEntity<List<Policy>> getPoliciesByDate(@PathVariable String date) {
        LocalDate d = LocalDate.parse(date);
        List<Policy> policies = service.getPoliciesByStartDate(d);
        return ResponseEntity.ok(policies);
    }
    
    @GetMapping("/premium")
    public ResponseEntity<List<Policy>> getPoliciesByPremium(@RequestParam Double min,
                                                             @RequestParam Double max) {
        List<Policy> policies = service.getPoliciesByPremiumAmountRange(min, max);
        return ResponseEntity.ok(policies);
    }
    
    @GetMapping("/maturity")
    public ResponseEntity<List<Policy>> getPoliciesByMaturity(@RequestParam Double min,
                                                              @RequestParam Double max) {
        List<Policy> policies = service.getPoliciesByMaturityAmountRange(min, max);
        return ResponseEntity.ok(policies);
    }
    
    @GetMapping("/terms/{terms}")
    public ResponseEntity<List<Policy>> getPoliciesByYears(@PathVariable Integer term) {
        List<Policy> policies = service.getPoliciesByPolicyTerm(term);
        return ResponseEntity.ok(policies);
    }
}
