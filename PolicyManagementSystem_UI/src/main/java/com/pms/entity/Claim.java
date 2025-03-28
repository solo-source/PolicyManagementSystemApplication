package com.pms.entity;

import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;


public class Claim {

   
    private Long claimId;

   
    private Policy policy;

   
    private Date claimDate;

    private String claimDescription;

   
    private BigDecimal claimAmount;

    
    private ClaimStatus claimStatus = ClaimStatus.PENDING;

    public enum ClaimStatus {
        PENDING, PAID, REJECTED
    }

    
    private String email;

    public Claim() {}

    public Claim(Policy policy, Date claimDate, String claimDescription, BigDecimal claimAmount, ClaimStatus claimStatus, String email) {
        this.policy = policy;
        this.claimDate = claimDate;
        this.claimDescription = claimDescription;
        this.claimAmount = claimAmount;
        this.claimStatus = claimStatus;
        this.email = email;
    }

    public Long getClaimId() {
        return claimId;
    }

    public Policy getPolicy() {
        return policy;
    }

    public void setPolicy(Policy policy) {
        this.policy = policy;
    }

    public Date getClaimDate() {
        return claimDate;
    }

    public void setClaimDate(Date claimDate) {
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
