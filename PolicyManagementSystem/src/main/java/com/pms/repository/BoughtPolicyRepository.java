package com.pms.repository;

import com.pms.entity.BoughtPolicy;
import com.pms.entity.Policy;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface BoughtPolicyRepository extends JpaRepository<BoughtPolicy, String> {
    List<BoughtPolicy> findByCustomerId(String customerId);
    Optional<BoughtPolicy> findByBoughtPolicyId(BoughtPolicy p);
}
