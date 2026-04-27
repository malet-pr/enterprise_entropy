package org.acme.rules.persistence.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "rule_category", indexes = {
        @Index(name = "idx_category_name", columnList = "category_name")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RuleCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category_name", nullable = false, unique = true)
    private String categoryName;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<RuleDefinition> rules = new ArrayList<>();

    @Column(name = "kie_session_name")
    private String kieSessionName;  // For different KieSession configurations

    @Column(name = "refresh_interval_minutes")
    private Integer refreshIntervalMinutes;

    @Column(name = "is_active", nullable = false)
    private Boolean active = true;

    @Column(name = "description")
    private String description;
}
