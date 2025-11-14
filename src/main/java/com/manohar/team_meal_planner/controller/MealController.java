package com.manohar.team_meal_planner.controller;


import com.manohar.team_meal_planner.dto.MealCreateRequestDto;
import com.manohar.team_meal_planner.dto.MealUpdateRequestDto;
import com.manohar.team_meal_planner.model.Meal;
import com.manohar.team_meal_planner.service.MealService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/meals")
@RequiredArgsConstructor
public class MealController {

    private final MealService mealService;

    @PostMapping
    public ResponseEntity<Meal> createMeal(@Valid @RequestBody MealCreateRequestDto mealCreateRequestDto, UriComponentsBuilder uriComponentsBuilder){
        Meal created = mealService.createMeal(mealCreateRequestDto);
        URI location = uriComponentsBuilder.path("/meals/{id}").buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(location).body(created);
    }


    @GetMapping
    public Page<Meal> listMeals(
            @RequestParam("startDate")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate startDate,
            @RequestParam("endDate")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(name = "cuisine", required = false) String cuisine,
            @RequestParam(name = "tag", required = false) String tag,
            Pageable pageable
            ){
        Page<Meal> page = mealService.listMeals(startDate, endDate, pageable);

        List<Meal> filtered = page.getContent().stream()
                .filter(meal -> cuisine == null
                || (meal.getCuisine() !=null
                && cuisine.equalsIgnoreCase(meal.getCuisine())))
                .filter((meal -> tag == null
                || (meal.getTags() !=null && meal.getTags().contains(tag))
                )).toList();

        return  new PageImpl<>(filtered, pageable, filtered.size());
    }

    //Retrieve the each meal based on the meal ID
    @GetMapping("/{id}")
    public Meal getMeal(@PathVariable Long id){
        return mealService.getMealById(id);
    }

    @GetMapping("/all")
    public List<Meal> getAllMeal(){
        return mealService.getAllMeals();
    }


    @PutMapping("/{id}")
    public Meal updateMeal(@PathVariable Long id,
                           @Valid @RequestBody MealUpdateRequestDto mealUpdateRequestDto){
        return mealService.updateMeal(id, mealUpdateRequestDto);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMeal(@PathVariable Long id){
        mealService.deleteMeal(id);
        return ResponseEntity.noContent().build();
    }

}
