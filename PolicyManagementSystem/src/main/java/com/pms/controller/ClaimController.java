package com.pms.controller;

import com.pms.entity.Claim;
import com.pms.exception.InvalidEntityException;

import com.pms.repository.ClaimRepository;
import com.pms.service.ClaimService;
import com.pms.service.EmailService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/claims")
@Validated
public class ClaimController {

    @Autowired
    private ClaimService claimService;
    @Autowired
    private ClaimRepository claimRepository;
    @Autowired
    private EmailService emailService;

    public ClaimController(ClaimService claimService) {
        this.claimService = claimService;
    }

    @GetMapping("/policy/{policyId}")
    public ResponseEntity<?> getClaimsByPolicyId(@PathVariable String policyId) {
        try {
            List<Claim> claims = claimService.getClaimsByPolicyId(policyId);
            return ResponseEntity.ok(claims);
        } catch (InvalidEntityException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createClaim(@Valid @RequestBody Claim claim) {
        try {
            Claim savedClaim = claimService.createClaim(claim);

            // Prepare email content
            String subject = "Claim Submitted Successfully";
            String body = "Dear User,\n\n" +
                          "Your claim with ID: " + savedClaim.getClaimId() + " has been submitted successfully.\n\n" +
                          "Claim Amount: " + savedClaim.getClaimAmount() + "\n" +
                          "Status: " + savedClaim.getClaimStatus();

            // Send email if email exists
            if (savedClaim.getEmail() != null && !savedClaim.getEmail().isEmpty()) {
                try {
                    emailService.sendEmail(savedClaim.getEmail(), subject, body);
                } catch (Exception e) {
                    System.err.println("Error sending email: " + e.getMessage());
                }
            } else {
                System.err.println("No email found for claim ID: " + savedClaim.getClaimId());
            }

            return ResponseEntity.ok(savedClaim);
        } catch (InvalidEntityException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            System.err.println("Error processing claim: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing the claim.");
        }
    }

    @GetMapping
    public ResponseEntity<List<Claim>> getAllClaims() {
        return ResponseEntity.ok(claimService.getAllClaims());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteClaim(@PathVariable Long id) {
        try {
            claimService.deleteClaim(id);
            return ResponseEntity.ok("Claim deleted successfully.");
        } catch (InvalidEntityException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getClaimById(@PathVariable Long id) {
        try {
            Claim claim = claimService.getClaimById(id);
            return ResponseEntity.ok(claim);
        } catch (InvalidEntityException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateClaim(@PathVariable Long id, @RequestBody Claim updatedClaim) {
        try {
            Claim claim = claimService.updateClaim(id, updatedClaim);
            return ResponseEntity.ok(claim);
        } catch (InvalidEntityException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


}
