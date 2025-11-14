package com.manohar.team_meal_planner.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Set;

public class MealUpdateRequestDto {

    @NotBlank
    private String title;

    @NotBlank
    private LocalDate date;

    @NotBlank
    private  String cuisine;

    @NotNull
    private Set<String> tags;

    @NotNull
    private Long version;

    @NotNull
    private Integer maxAttendees;
}
