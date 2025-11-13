package com.manohar.team_meal_planner.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import lombok.*;
import org.hibernate.annotations.Fetch;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Data
@Builder
@Table(name="Meals")
@AllArgsConstructor
@NoArgsConstructor
public class Meal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    private String title;
    @Column(nullable = false)
    private String cuisine;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name="meal_tags", joinColumns = @JoinColumn(name="meal_id"))
    @Column(name = "tag")
    @Builder.Default
    private Set<String> tags = new HashSet<>();

    private Integer maxAttendees;

    @Version
    private Long version;

    private LocalDateTime createdAt;
    private LocalDateTime updateAt;

    @PrePersist
    void prePersist() {
        createdAt = LocalDateTime.now();
        updateAt = createdAt;

    }

    @PreUpdate
    void preUpdate(){
        updateAt = LocalDateTime.now();
    }

}
