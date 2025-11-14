package com.manohar.team_meal_planner.service;

import jdk.dynalink.linker.LinkerServices;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.manohar.team_meal_planner.model.Meal;
import com.manohar.team_meal_planner.dto.MealCreateRequestDto;
import com.manohar.team_meal_planner.dto.MealUpdateRequestDto;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;


public interface MealService {

    Meal createMeal(MealCreateRequestDto mealCreateRequestDto);

    Page<Meal> listMeals(LocalDate startDate, LocalDate endDate, Pageable pageable);

    Meal getMealById(Long mealId) throws NoSuchElementException;

    List<Meal> getAllMeals() throws NoSuchElementException;


    Meal updateMeal(Long id, MealUpdateRequestDto mealUpdateRequestDto) throws NoSuchElementException;

    void deleteMeal(Long mealId) throws NoSuchElementException;
}
