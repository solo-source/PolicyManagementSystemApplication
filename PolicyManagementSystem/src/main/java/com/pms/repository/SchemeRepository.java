package com.pms.repository;

import com.pms.entity.Scheme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface SchemeRepository extends JpaRepository<Scheme, Integer> {
    List<Scheme> findBySchemeIsActiveIsTrue();
    List<Scheme> findSchemeBySchemeName(String schemeName);
    List<Scheme> findSchemeBySchemeNameAndSchemeIsActiveIsTrue(String schemeName);
}
