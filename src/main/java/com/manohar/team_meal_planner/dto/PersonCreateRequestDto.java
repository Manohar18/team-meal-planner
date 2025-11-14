package com.manohar.team_meal_planner.dto;

import com.manohar.team_meal_planner.model.DietaryTag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.util.Set;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonCreateRequestDto {

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Email is mandatory")
    @Email
    private String email;

    @NotNull(message = "Dietary tags cannot be null")
    private Set<DietaryTag> dietaryTags;
}
