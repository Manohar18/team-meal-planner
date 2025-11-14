package com.manohar.team_meal_planner.service.impl;

import com.manohar.team_meal_planner.dto.MealCreateRequestDto;
import com.manohar.team_meal_planner.dto.MealUpdateRequestDto;
import com.manohar.team_meal_planner.model.Meal;
import com.manohar.team_meal_planner.repository.MealRepository;
import com.manohar.team_meal_planner.repository.SignupRepository;
import com.manohar.team_meal_planner.service.MealService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class MealServiceImpl implements MealService{

    private final MealRepository mealRepository;
    private final SignupRepository signupRepository;

    @Override
    @Transactional
    public Meal createMeal(MealCreateRequestDto mealCreateRequestDto){
        if(mealCreateRequestDto == null){
            throw new IllegalArgumentException("MealCreateRequestDto cannot be null");
        }

        Meal meal = Meal.builder().date(mealCreateRequestDto.getDate())
                .title(mealCreateRequestDto.getTitle())
                .cuisine(mealCreateRequestDto.getCuisine())
                .tags(mealCreateRequestDto.getTags())
                .maxAttendees(mealCreateRequestDto.getMaxAttendees())
                .build();

        return  mealRepository.save(meal);

    }

    @Override
    public Page<Meal> listMeals(LocalDate startDate, LocalDate endDate, Pageable pageable) {
        return mealRepository.findByDateBetween(startDate, endDate, pageable);
    }

    @Override
    public Meal getMealById(Long mealId) throws NoSuchElementException {
        return mealRepository.findById(mealId).orElseThrow(()-> new NoSuchElementException("Meal Not found with id:" + mealId));
    }

    @Override
    public List<Meal> getAllMeals() throws NoSuchElementException{
        return mealRepository.findAll();
    }

    @Override
    @Transactional
    public Meal updateMeal(Long id, MealUpdateRequestDto mealUpdateRequestDto) throws NoSuchElementException {

        Meal existingMeal = getMealById(id);

        if(!existingMeal.getVersion().equals(mealUpdateRequestDto.getVersion())) {
            throw new IllegalStateException("Meal has been modified by another transaction.");
        }
        existingMeal.setTitle(mealUpdateRequestDto.getTitle());
        existingMeal.setDate(mealUpdateRequestDto.getDate());
        existingMeal.setCuisine(mealUpdateRequestDto.getCuisine());
        existingMeal.setTags(mealUpdateRequestDto.getTags());
        existingMeal.setMaxAttendees(mealUpdateRequestDto.getMaxAttendees());

        return mealRepository.save(existingMeal);
    }

    @Override
    @Transactional
    public void deleteMeal(Long mealId) throws NoSuchElementException{
        Meal meal = getMealById(mealId);
        long signupCount = signupRepository.countByMeal_Id(mealId);
        if(signupCount > 0){
            throw new IllegalStateException("Cannot delete meal with exisiting signups.");
        }
        mealRepository.delete(meal);
    }


}
