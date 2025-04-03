package com.pms.repository;

import com.pms.entity.BoughtPolicy;
import com.pms.entity.Policy;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PolicyRepository extends JpaRepository<Policy, String> {
//    List<Policy> findByCustomerId(String id);
    List<Policy> findBySchemeId(Integer id);
    Optional<Policy> findByPolicyId(String policyId);
    Policy findTopByOrderByPolicyIdDesc();
    List<Policy> findBySchemeSchemeName(String schemeName);
    List<Policy> findByStartDate(LocalDate startDate);
    List<Policy> findByTotalPremiumAmountBetween(Double min, Double max);
    List<Policy> findByMaturityAmountBetween(Double min, Double max);
    List<Policy> findByPolicyTerm(Integer policyTerm);
}
