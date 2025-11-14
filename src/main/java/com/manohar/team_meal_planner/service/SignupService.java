package com.manohar.team_meal_planner.service;

import com.manohar.team_meal_planner.dto.SignupCreateRequestDto;
import com.manohar.team_meal_planner.model.Signup;

public interface SignupService {

    Signup createSignup(SignupCreateRequestDto signupCreateRequestDto);

}
