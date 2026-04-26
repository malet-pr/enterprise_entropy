package org.acme.rules.persistence.repository;

import org.acme.rules.persistence.model.RuleCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface RuleCategoryRepository extends JpaRepository<RuleCategory, Long> {

    Optional<RuleCategory> findByCategoryName(String categoryName);

    List<RuleCategory> findByActiveTrue();
}
