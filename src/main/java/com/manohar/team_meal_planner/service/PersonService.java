package com.manohar.team_meal_planner.service;

import com.manohar.team_meal_planner.dto.PersonCreateRequestDto;
import com.manohar.team_meal_planner.model.Person;

public interface PersonService {

    Person createPerson(PersonCreateRequestDto personCreateRequestDto);

    Person getPersonById(Long id);
}
