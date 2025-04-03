package com.pms.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "claim")  // ✅ Ensure lowercase name for MySQL consistency
public class Claim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "claim_id", nullable = false, updatable = false)
    private Long claimId;

    @ManyToOne
    @JoinColumn(name = "policy_id", nullable = false)  // ✅ Correctly references `policy_id`
    @NotNull(message = "Policy must not be null")
    private Policy policy;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "claim_date", nullable = false)
//    @NotNull(message = "Claim date must not be null")
    private LocalDate claimDate;

    @Column(name = "claim_description", length = 500, nullable = false)
    @NotBlank(message = "Claim description must not be blank")
    @Size(max = 500, message = "Claim description must not exceed 500 characters")
    private String claimDescription;

    @Column(name = "claim_amount", nullable = false, precision = 12, scale = 2)
    @NotNull(message = "Claim amount must not be null")
    @DecimalMin(value = "0.01", message = "Claim amount must be greater than 0")
    @Digits(integer = 10, fraction = 2, message = "Claim amount must be a valid monetary value")
    private BigDecimal claimAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "claim_status", nullable = false)
    @NotNull(message = "Claim status must not be null")
    private ClaimStatus claimStatus = ClaimStatus.PENDING;

    public enum ClaimStatus {
        PENDING, PAID, REJECTED
    }
    @Column(name = "email", nullable = false)
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")  
    private String email; 

    // ✅ Default Constructor (Required by JPA)
    public Claim() {}

    // ✅ Constructor (Without `claimId`, since it’s auto-generated)
    public Claim(Policy policy, LocalDate claimDate, String claimDescription, BigDecimal claimAmount, ClaimStatus claimStatus) {
        this.policy = policy;
        this.claimDate = claimDate;
        this.claimDescription = claimDescription;
        this.claimAmount = claimAmount;
        this.claimStatus = claimStatus;
        this.email = email;
    }

    // ✅ Getters and Setters
    public Long getClaimId() {
        return claimId;
    }

    public Policy getPolicy() {
        return policy;
    }

    public void setPolicy(Policy policy) {
        this.policy = policy;
    }

    public LocalDate getClaimDate() {
        return claimDate;
    }

    public void setClaimDate(LocalDate claimDate) {
        this.claimDate = claimDate;
    }

    public String getClaimDescription() {
        return claimDescription;
    }

    public void setClaimDescription(String claimDescription) {
        this.claimDescription = claimDescription;
    }

    public BigDecimal getClaimAmount() {
        return claimAmount;
    }

    public void setClaimAmount(BigDecimal claimAmount) {
        this.claimAmount = claimAmount;
    }

    public ClaimStatus getClaimStatus() {
        return claimStatus;
    }

    public void setClaimStatus(ClaimStatus claimStatus) {
        this.claimStatus = claimStatus;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
