package com.manohar.team_meal_planner.model;

import java.util.Set;
import jakarta.persistence.*;
import lombok.*;


@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="people")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<DietaryTag> dietaryTags;
}
