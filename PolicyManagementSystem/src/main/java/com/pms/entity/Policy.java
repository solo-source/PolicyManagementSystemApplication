
package com.pms.entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "policy")
public class Policy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "policy_id", nullable = false)
    private String policyId;

    @Column(name = "start_date")
    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    @Column(name = "total_premium_amount", nullable = false)
    @Positive(message = "Total premium amount must be positive")
    private Double totalPremiumAmount;

    @Column(name = "maturity_amount", nullable = false)
    @Positive(message = "Number of years must be positive")
    private Double maturityAmount;

    @Column(name = "policy_term")
    @NotNull(message = "Policy Term is required")
    @Positive(message = "Policy Term must be positive")
    private Integer policyTerm;

    @Enumerated(EnumType.STRING)
    @Column(name = "policy_status")
    private PolicyStatus policyStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "annuity_term")
    private AnnuityTerm annuityTerm;

    @JsonBackReference(value="customer-policies")
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
    
    @JsonBackReference(value="scheme-policies")
    @ManyToOne
    @JoinColumn(name = "scheme_id", nullable = false)
    private Scheme scheme;

    @JsonManagedReference(value="policy-payments")
    @OneToMany(mappedBy = "policy", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Payment> payments;

    public enum PolicyStatus {
        ACTIVE, INACTIVE
    }

    public enum AnnuityTerm {
        QUARTERLY, HALF_YEARLY, ANNUAL, ONE_TIME
    }

    public String getPolicyId() {
        return policyId;
    }

    public void setPolicyId(String policyId) {
        this.policyId = policyId;
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

    public PolicyStatus getPolicyStatus() {
        return policyStatus;
    }

    public void setPolicyStatus(PolicyStatus policyStatus) {
        this.policyStatus = policyStatus;
    }

    public AnnuityTerm getAnnuityTerm() {
        return annuityTerm;
    }

    public void setAnnuityTerm(AnnuityTerm annuityTerm) {
        this.annuityTerm = annuityTerm;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }
    
    public Scheme getScheme() {
        return scheme;
    }

    public void setScheme(Scheme scheme) {
        this.scheme = scheme;
    }

}
