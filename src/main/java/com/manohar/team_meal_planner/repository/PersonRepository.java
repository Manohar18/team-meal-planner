package com.manohar.team_meal_planner.repository;

import org.springframework.data.jpa.repository.JpaRepository;


public interface PersonRepository extends JpaRepository<com.manohar.team_meal_planner.model.Person, Long>{

    boolean existsByEmail(String email);
}
