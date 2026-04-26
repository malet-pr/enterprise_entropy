package org.acme.rules.persistence.repository;

import org.acme.rules.persistence.model.RuleDefinition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface RuleDefinitionRepository extends JpaRepository<RuleDefinition, Long> {

    // Find active rules by category
    List<RuleDefinition> findByCategoryAndActiveTrueOrderByPriorityAsc(String category);


    // Find rules by rule name
    Optional<RuleDefinition> findByRuleName(String ruleName);

    // Find recently updated rules
    List<RuleDefinition> findByUpdatedAtAfter(LocalDateTime updatedAfter);

    // Batch load rules for multiple categories
    @Query("SELECT r FROM RuleDefinition r WHERE r.category IN :categories AND r.active = true ORDER BY r.category, r.priority")
    List<RuleDefinition> findByCategories(@Param("categories") List<String> categories);

    // Count active rules by category
    @Query("SELECT r.category, COUNT(r) FROM RuleDefinition r WHERE r.active = true GROUP BY r.category")
    List<Object[]> countActiveRulesByCategory();

    // Update execution statistics
    @Modifying
    @Transactional
    @Query("UPDATE RuleDefinition r SET r.lastExecutedAt = CURRENT_TIMESTAMP, r.executionCount = r.executionCount + 1 WHERE r.ruleName = :ruleName")
    void updateExecutionStats(@Param("ruleName") String ruleName);

    @Query("SELECT DISTINCT r FROM RuleDefinition r " +
            "LEFT JOIN FETCH r.category c " +
            "WHERE c.categoryName = :categoryName AND r.active = true " +
            "ORDER BY r.priority ASC")
    List<RuleDefinition> findByCategoryNameWithCategory(@Param("categoryName") String categoryName);
}

