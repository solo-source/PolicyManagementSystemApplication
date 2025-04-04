package com.pms.repository;

import com.pms.entity.BoughtPolicy;
import com.pms.entity.Policy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BoughtPolicyRepository extends JpaRepository<BoughtPolicy, Long> {
    List<BoughtPolicy> findByCustomerId(String customerId);
    Optional<BoughtPolicy> findByBoughtPolicyId(BoughtPolicy p);
    
    
    @Query("SELECT bp FROM BoughtPolicy bp WHERE bp.policy.scheme.schemeName = ?1")
    List<BoughtPolicy> findBySchemeName(String schemeName);
    
    
    List<BoughtPolicy> findByPolicyTerm(Integer policyTerm);
    List<BoughtPolicy> findByMaturityAmountBetween(Double min, Double max);
    List<BoughtPolicy> findByTotalPremiumAmountBetween(Double min, Double max);

}
