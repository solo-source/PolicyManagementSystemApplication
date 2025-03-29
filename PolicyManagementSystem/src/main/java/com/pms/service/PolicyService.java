package com.pms.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pms.entity.Policy;
import com.pms.entity.Policy.PolicyStatus;
import com.pms.entity.Policy.AnnuityTerm;
import com.pms.exception.InvalidEntityException;
import com.pms.repository.CustomerRepository;
import com.pms.repository.PolicyRepository;
import com.pms.repository.SchemeRepository;

@Service
public class PolicyService {

    @Autowired
    private PolicyRepository repo;
    
    @Autowired
    private CustomerRepository customerRepo;
    
    @Autowired
    private SchemeRepository schemeRepo;

    public List<Policy> viewPolicies() {
        return repo.findAll();
    }

    // Assuming getPolicyId() returns a unique identifier of type String (the business key)
    public Optional<Policy> viewPolicyDetails(Policy p) {
        // If your primary key is a Long (e.g. id) and policyId is a business key,
        // adjust the finder method accordingly.
        return repo.findByPolicyId(p.getPolicyId());
    }

    public List<Policy> viewCustPolicies(String id) {
        return repo.findByCustomerId(id);
    }

    public List<Policy> viewSchemePolicies(Integer id) {
        return repo.findBySchemeId(id);
    }
    
    public Policy createPolicy(Policy policy) throws InvalidEntityException {
        // Validate if Customer exists (must be provided)
        if (policy.getCustomer() == null || policy.getCustomer().getId() == null ||
            !customerRepo.existsById(policy.getCustomer().getId())) {
            throw new InvalidEntityException("Customer not found with id " +
                (policy.getCustomer() != null ? policy.getCustomer().getId() : "null"));
        }
        // Validate if Scheme exists (must be provided)
        if (policy.getScheme() == null || policy.getScheme().getId() == null ||
            !schemeRepo.existsById(policy.getScheme().getId())) {
            throw new InvalidEntityException("Scheme not found with id " +
                (policy.getScheme() != null ? policy.getScheme().getId() : "null"));
        }
        
        // Validate numeric and other fields
        if (policy.getStartDate() == null) {
            throw new IllegalArgumentException("Start date must be provided and be a valid date.");
        }
        if (policy.getTotalPremiumAmount() == null || policy.getTotalPremiumAmount() <= 0) {
            throw new IllegalArgumentException("Total premium amount must be a positive number.");
        }
        if (policy.getMaturityAmount() == null || policy.getMaturityAmount() <= 0) {
            throw new IllegalArgumentException("Maturity amount must be a positive number.");
        }
        if (policy.getPolicyTerm() == null || policy.getPolicyTerm() <= 0) {
            throw new IllegalArgumentException("Policy Term must be a positive number.");
        }
        // Validate PolicyStatus using enum comparison
        if (policy.getPolicyStatus() == null ||
            (policy.getPolicyStatus() != PolicyStatus.ACTIVE &&
             policy.getPolicyStatus() != PolicyStatus.INACTIVE)) {
            throw new IllegalArgumentException("Policy status must be either ACTIVE or DEACTIVATED.");
        }
     // Validate AnnuityTerm using enum comparison
        if (policy.getAnnuityTerm() == null ||
            (policy.getAnnuityTerm() != AnnuityTerm.QUARTERLY &&
             policy.getAnnuityTerm() != AnnuityTerm.HALF_YEARLY &&
             policy.getAnnuityTerm() != AnnuityTerm.ANNUAL &&
             policy.getAnnuityTerm() != AnnuityTerm.ONE_TIME)) {
            throw new IllegalArgumentException("Annuity Term must be one of: QUARTERLY, HALF_YEARLY, ANNUAL, ONE_TIME.");
        }

        // Generate a new policyId by checking the last record.
        // Assumes that policy.getPolicyId() is of type String.
        Policy lastPolicy = repo.findTopByOrderByIdDesc();
        String newPolicyId;
        if (lastPolicy == null || lastPolicy.getPolicyId() == null) {
            newPolicyId = "POLICY001";
        } else {
            String lastId = lastPolicy.getPolicyId();
            int lastNumber = Integer.parseInt(lastId.substring("POLICY".length()));
            int newNumber = lastNumber + 1;
            newPolicyId = String.format("POLICY%03d", newNumber);
        }
        
        // Set the auto-generated policyId; user input is ignored for this field.
        policy.setPolicyId(newPolicyId);
        
        return repo.save(policy);
    }
    
    public Policy updatePolicy(String id, Policy policyDetails) throws InvalidEntityException {
        Policy existingPolicy = repo.findByPolicyId(id)
                .orElseThrow(() -> new InvalidEntityException("Policy not found with id " + id));

        // Disallow update of restricted fields:
        if (policyDetails.getCustomer() != null) {
            throw new IllegalArgumentException("Customer field is not allowed in policy update.");
        }
        if (policyDetails.getScheme() != null) {
            throw new IllegalArgumentException("Scheme field is not allowed in policy update.");
        }
        if (policyDetails.getPolicyId() != null) {
            throw new IllegalArgumentException("Policy ID field is not allowed in policy update.");
        }

        // Update allowed fields if provided
        if (policyDetails.getStartDate() != null) {
            existingPolicy.setStartDate(policyDetails.getStartDate());
        }

        if (policyDetails.getTotalPremiumAmount() != null) {
            if (policyDetails.getTotalPremiumAmount() <= 0) {
                throw new IllegalArgumentException("Total premium amount must be a positive number.");
            }
            existingPolicy.setTotalPremiumAmount(policyDetails.getTotalPremiumAmount());
        }

        if (policyDetails.getMaturityAmount() != null) {
            if (policyDetails.getMaturityAmount() <= 0) {
                throw new IllegalArgumentException("Maturity amount must be a positive number.");
            }
            existingPolicy.setMaturityAmount(policyDetails.getMaturityAmount());
        }

        if (policyDetails.getPolicyTerm() != null) {
            if (policyDetails.getPolicyTerm() <= 0) {
                throw new IllegalArgumentException("Policy Term must be a positive number.");
            }
            existingPolicy.setPolicyTerm(policyDetails.getPolicyTerm());
        }

        // For updating PolicyStatus, assume that policyDetails carries a String representation.
        // Convert the string to the enum before setting.
        if (policyDetails.getPolicyStatus() != null) {
            // If the incoming type is already enum, then no trim() is available.
            // One approach is to expect a String from the client. If so, you could use:
            String statusStr = policyDetails.getPolicyStatus().toString();
            if (!(statusStr.equalsIgnoreCase("ACTIVE") || statusStr.equalsIgnoreCase("DEACTIVATED"))) {
                throw new IllegalArgumentException("Policy status must be either ACTIVE or DEACTIVATED.");
            }
            PolicyStatus newStatus = PolicyStatus.valueOf(statusStr.toUpperCase());
            existingPolicy.setPolicyStatus(newStatus);
        }

        // Similarly update AnnuityTerm.
        if (policyDetails.getAnnuityTerm() != null) {
            String termStr = policyDetails.getAnnuityTerm().toString();
            if (!(termStr.equalsIgnoreCase("YEARLY") || termStr.equalsIgnoreCase("MONTHLY"))) {
                throw new IllegalArgumentException("Annuity Term must be either YEARLY or MONTHLY.");
            }
            AnnuityTerm newTerm = AnnuityTerm.valueOf(termStr.toUpperCase());
            existingPolicy.setAnnuityTerm(newTerm);
        }

        return repo.save(existingPolicy);
    }
    
    public Policy getPolicyById(String id) throws InvalidEntityException {
        return repo.findByPolicyId(id)
                .orElseThrow(() -> new InvalidEntityException("Policy not found with id " + id));
    }
    
    public List<Policy> getPoliciesBySchemeName(String schemeName) {
        return repo.findBySchemeSchemeName(schemeName);
    }
    
    public List<Policy> getPoliciesByStartDate(LocalDate startDate) {
        return repo.findByStartDate(startDate);
    }
    
    public List<Policy> getPoliciesByPremiumAmountRange(Double min, Double max) {
        return repo.findByTotalPremiumAmountBetween(min, max);
    }
    
    public List<Policy> getPoliciesByMaturityAmountRange(Double min, Double max) {
        return repo.findByMaturityAmountBetween(min, max);
    }
    
    public List<Policy> getPoliciesByNumberOfYears(Integer years) {
        return repo.findByNumberOfYears(years);
    }
}
