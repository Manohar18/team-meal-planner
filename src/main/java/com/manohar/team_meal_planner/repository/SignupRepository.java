package com.manohar.team_meal_planner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.manohar.team_meal_planner.model.Signup;
import java.time.LocalDate;
import java.util.List;

public interface SignupRepository extends JpaRepository<Signup, Long>{

    List<Signup> findByPerson_IdAndMeal_Date(Long personId, LocalDate date);

    long countByMeal_Id(Long mealId);

    boolean existsByPerson_IdAndMeal_Id(Long personId, Long mealId);

}
