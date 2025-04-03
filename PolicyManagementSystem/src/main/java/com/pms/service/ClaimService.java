package com.pms.service;

import com.pms.entity.Claim;
import com.pms.entity.Policy;
import com.pms.exception.InvalidEntityException;
import com.pms.repository.ClaimRepository;
import com.pms.repository.PolicyRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ClaimService {

    @Autowired
    private ClaimRepository claimRepository;

    @Autowired
    private PolicyRepository policyRepository;

    @Autowired
    private JavaMailSender mailSender;
    
    @Autowired
    private EmailService emailService;

    public List<Claim> getClaimsByPolicyId(String policyId) throws InvalidEntityException {
        if (policyId == null || policyId.trim().isEmpty()) {
            throw new InvalidEntityException("Invalid Policy ID.");
        }
        if (!policyRepository.existsById(policyId)) {
            throw new InvalidEntityException("Policy not found with ID: " + policyId);
        }
        List<Claim> claims = claimRepository.findByPolicy_PolicyId(policyId);
        if (claims.isEmpty()) {
            throw new InvalidEntityException("No claims found for Policy ID: " + policyId);
        }
        return claims;
    }

    public Claim createClaim(@Valid Claim claim) throws InvalidEntityException {
        if (claim.getPolicy() == null || claim.getPolicy().getPolicyId() == null) {
            throw new InvalidEntityException("Policy ID is required to create a claim.");
        }
        Policy policy = policyRepository.findById(claim.getPolicy().getPolicyId())
                .orElseThrow(() -> new InvalidEntityException("Policy not found with ID: " + claim.getPolicy().getPolicyId()));

        if (claim.getClaimDescription() == null || claim.getClaimDescription().trim().isEmpty()) {
            throw new InvalidEntityException("Claim description cannot be empty.");
        }
        if (claim.getClaimStatus() == null) {
            throw new InvalidEntityException("Claim status cannot be null.");
        }
        claim.setPolicy(policy);
        return claimRepository.save(claim);
    }

    public List<Claim> getAllClaims() {
        return claimRepository.findAll();
    }

    public Claim getClaimById(Long id) throws InvalidEntityException {
        if (id == null || id <= 0) {
            throw new InvalidEntityException("Invalid Claim ID.");
        }
        return claimRepository.findById(id)
                .orElseThrow(() -> new InvalidEntityException("Claim not found with ID: " + id));
    }

    public void deleteClaim(Long id) throws InvalidEntityException {
        if (id == null || id <= 0) {
            throw new InvalidEntityException("Invalid Claim ID.");
        }
        if (!claimRepository.existsById(id)) {
            throw new InvalidEntityException("Claim not found with ID: " + id);
        }
        claimRepository.deleteById(id);
    }

    public Claim updateClaim(Long id, Claim updatedClaim) throws InvalidEntityException {
        Claim existingClaim = claimRepository.findById(id)
            .orElseThrow(() -> new InvalidEntityException("Claim not found with ID: " + id));

        if (updatedClaim.getClaimDescription() != null) {
            existingClaim.setClaimDescription(updatedClaim.getClaimDescription());
        }
        if (updatedClaim.getClaimStatus() != null) {
            existingClaim.setClaimStatus(updatedClaim.getClaimStatus());
        }
        if (updatedClaim.getEmail() != null) {
            existingClaim.setEmail(updatedClaim.getEmail());
        }
        Claim savedClaim = claimRepository.save(existingClaim);
        emailService.sendClaimUpdateEmail(savedClaim);
        return savedClaim;
    }
    
    private void sendClaimNotificationEmail(String recipientEmail, String subject, String message) throws InvalidEntityException {
        if (recipientEmail == null || recipientEmail.trim().isEmpty()) {
            throw new InvalidEntityException("Recipient email cannot be empty.");
        }
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(recipientEmail);
            helper.setSubject(subject);
            helper.setText(message, true);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email: " + e.getMessage());
        }
    }
}
