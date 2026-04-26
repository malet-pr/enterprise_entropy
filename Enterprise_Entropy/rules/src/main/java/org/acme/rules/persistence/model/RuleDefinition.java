package org.acme.rules.persistence.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "rule_definition",
        indexes = {
                @Index(name = "idx_rule_category", columnList = "category"),
                @Index(name = "idx_rule_active", columnList = "active"),
                @Index(name = "idx_rule_version", columnList = "version")
        })
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RuleDefinition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rule_name", nullable = false, unique = true)
    private String ruleName;

    @Column(name = "rule_content", nullable = false, columnDefinition = "TEXT")
    private String ruleContent;  // The DRL content

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private RuleCategory category;  // e.g., "DAILY", "PLANNING", "COLLECTIVE_DEBUG_IN_ENVIRONMENT"

    @Column(name = "version", nullable = false)
    private Integer version;

    @Column(name = "is_active", nullable = false)
    private Boolean active = true;

    @Column(name = "description")
    private String description;

    @Column(name = "priority")
    private Integer priority;  // For rule execution order

    @Column(name = "metadata", columnDefinition = "jsonb")
    private String metadata;  // JSON field for additional metadata

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "last_executed_at")
    private LocalDateTime lastExecutedAt;

    @Column(name = "execution_count")
    private Long executionCount = 0L;
}
