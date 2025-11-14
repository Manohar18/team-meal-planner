package com.manohar.team_meal_planner.repository;

import com.manohar.team_meal_planner.model.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import java.time.LocalDate;

public interface MealRepository extends JpaRepository<Meal,Long>{

    org.springframework.data.domain.Page<Meal> findByDateBetween(java.time.LocalDate start, java.time.LocalDate end, Pageable pageable);
}
