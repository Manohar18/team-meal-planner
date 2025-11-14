package com.manohar.team_meal_planner.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;


@Data
@Builder
public class MealUpdateRequestDto {

    @NotBlank
    private String title;

    @NotNull
    private LocalDate date;

    @NotBlank
    private  String cuisine;

    @NotNull
    private Set<String> tags;

    @NotNull
    private Long version;

    @Positive
    @NotNull
    private Integer maxAttendees;
}
