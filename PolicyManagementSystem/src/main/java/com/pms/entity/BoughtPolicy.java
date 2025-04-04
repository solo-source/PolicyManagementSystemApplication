package com.pms.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "bought_policy")
public class BoughtPolicy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long boughtPolicyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "policy_id", nullable = false)
    public Policy policy;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "total_premium_amount")
    private Double totalPremiumAmount;

    @Column(name = "maturity_amount")
    private Double maturityAmount;

    @Column(name = "policy_term")
    private Integer policyTerm;

    @Enumerated(EnumType.STRING)
    @Column(name = "policy_status")
    private Policy.PolicyStatus policyStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "annuity_term")
    private AnnuityTerm annuityTerm;

    // Getters and Setters
    public Long getBoughtPolicyId() {
        return boughtPolicyId;
    }

    public void setBoughtPolicyId(Long boughtPolicyId) {
        this.boughtPolicyId = boughtPolicyId;
    }

    public Policy getPolicy() {
        return policy;
    }

    public void setPolicy(Policy policy) {
        this.policy = policy;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public Double getTotalPremiumAmount() {
        return totalPremiumAmount;
    }

    public void setTotalPremiumAmount(Double totalPremiumAmount) {
        this.totalPremiumAmount = totalPremiumAmount;
    }

    public Double getMaturityAmount() {
        return maturityAmount;
    }

    public void setMaturityAmount(Double maturityAmount) {
        this.maturityAmount = maturityAmount;
    }

    public Integer getPolicyTerm() {
        return policyTerm;
    }

    public void setPolicyTerm(Integer policyTerm) {
        this.policyTerm = policyTerm;
    }

    public Policy.PolicyStatus getPolicyStatus() {
        return policyStatus;
    }

    public void setPolicyStatus(Policy.PolicyStatus policyStatus) {
        this.policyStatus = policyStatus;
    }

    public AnnuityTerm getAnnuityTerm() {
        return annuityTerm;
    }

    public void setAnnuityTerm(AnnuityTerm annuityTerm) {
        this.annuityTerm = annuityTerm;
    }
}
