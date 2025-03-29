package com.pms.repository;

import com.pms.entity.Policy;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PolicyRepository extends JpaRepository<Policy, String> {

    List<Policy> findByCustomerId(String id);

    List<Policy> findBySchemeId(int id);
    
    Optional<Policy> findByPolicyId(String policyId);
    
    Policy findTopByOrderByIdDesc();
    
    List<Policy> findBySchemeSchemeName(String schemeName);
    List<Policy> findByStartDate(LocalDate startDate);
    List<Policy> findByTotalPremiumAmountBetween(Double min, Double max);
    List<Policy> findByMaturityAmountBetween(Double min, Double max);
    List<Policy> findByNumberOfYears(Integer numberOfYears);
}
