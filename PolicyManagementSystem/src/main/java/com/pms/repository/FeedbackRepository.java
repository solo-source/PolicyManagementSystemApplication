package com.pms.repository;

import com.pms.entity.Feedback;
import com.pms.entity.Scheme;
import com.pms.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findByScheme(Scheme scheme);
    List<Feedback> findBySchemeAndCustomer(Scheme scheme, Customer customer);
    List<Feedback> findBySchemeAndStatus(Scheme scheme, String status);
}
