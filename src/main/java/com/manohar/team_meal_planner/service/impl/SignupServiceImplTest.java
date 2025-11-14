package com.manohar.team_meal_planner.service.impl;


import com.manohar.team_meal_planner.dto.SignupCreateRequestDto;
import com.manohar.team_meal_planner.model.Meal;
import com.manohar.team_meal_planner.model.Person;
import com.manohar.team_meal_planner.model.Signup;
import com.manohar.team_meal_planner.repository.MealRepository;
import com.manohar.team_meal_planner.repository.PersonRepository;
import com.manohar.team_meal_planner.repository.SignupRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.verification.VerificationMode;

import java.util.Optional;
import java.util.Properties;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SignupServiceImplTest {

    @Mock
    private SignupRepository signupRepository;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private MealRepository mealRepository;

    @Mock
    private SignupServiceImpl signupService;


    public void testCreateSignup_SuccessCase(){

        SignupCreateRequestDto signupCreateRequestDto = SignupCreateRequestDto.builder()
                .personId(1L)
                .mealId(10L)
                .note("Test note")
                .build();

        Person person = Person.builder().id(1L).name("John").dietaryTags(Set.of()).build();
        Meal meal = Meal.builder().id(10L).tags(Set.of()).maxAttendees(5).build();

        Signup saved = Signup.builder().id(100L).person(person).meal(meal).note("Test note").build();

        when(personRepository.findById(1L)).thenReturn(Optional.of(person));
        when(mealRepository.findById(10L)).thenReturn(Optional.of(meal));
       // when(signupRepository.save(any(Signup.class))).thenReturn(saved);

        Signup result = signupService.createSignup(signupCreateRequestDto);

        assertThat(result.getId()).isEqualTo(100L);
       // verify(signupRepository, times(1)).save(any(Signup.class));
    }

}
