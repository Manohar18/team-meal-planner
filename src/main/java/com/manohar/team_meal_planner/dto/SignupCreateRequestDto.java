package com.manohar.team_meal_planner.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignupCreateRequestDto {

    @NotNull
    private Long personId;

    @NotNull
    private Long mealId;

    private String note;

}
