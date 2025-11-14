package com.manohar.team_meal_planner.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.*;

import java.util.Set;
import java.time.LocalDate;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MealCreateRequestDto {

    @NotNull
    private LocalDate date;

    @NotBlank
    private String title;

    @NotBlank
    private String cuisine;

    private Set<String> tags;

    @NotNull
    private Integer maxAttendees;
}
