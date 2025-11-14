package com.manohar.team_meal_planner.service.impl;


import com.manohar.team_meal_planner.dto.SignupCreateRequestDto;
import com.manohar.team_meal_planner.model.DietaryTag;
import com.manohar.team_meal_planner.model.Meal;
import com.manohar.team_meal_planner.model.Person;
import com.manohar.team_meal_planner.model.Signup;
import com.manohar.team_meal_planner.repository.MealRepository;
import com.manohar.team_meal_planner.repository.PersonRepository;
import com.manohar.team_meal_planner.repository.SignupRepository;
import com.manohar.team_meal_planner.service.SignupService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class SignupServiceImpl implements SignupService {

    private final SignupRepository signupRepository;
    private final PersonRepository personRepository;
    private final MealRepository mealRepository;

   @Override
   public Signup createSignup(SignupCreateRequestDto signupCreateRequestDto){
     if(signupCreateRequestDto == null){
         throw new IllegalArgumentException("SignupCreateRequestDto cannot be null");
     }

     Long personId = signupCreateRequestDto.getPersonId();
     Long mealId = signupCreateRequestDto.getMealId();

       Person person = personRepository.findById(personId)
               .orElseThrow(() -> new NoSuchElementException("Person not found with id: " + personId));

       Meal meal = mealRepository.findById(mealId)
               .orElseThrow(() -> new NoSuchElementException("Meal not found with id: " + mealId));

       // If a signup already exists for this person and mean then return it
       if(signupRepository.existsByPerson_IdAndMeal_Id(personId, mealId)){
           return signupRepository.findByPerson_IdAndMeal_Date(personId, meal.getDate())
                   .stream()
                   .filter(s -> s.getMeal().getId().equals(mealId))
                   .findFirst()
                   .orElseThrow(() -> new IllegalStateException("Existing signup not found"));
       }
       // Once meal per day per person for a different meal on same date
       boolean hasOtherMealSameDay = signupRepository
               .findByPerson_IdAndMeal_Date(personId, meal.getDate())
               .stream()
               .anyMatch( s-> !s.getMeal().getId().equals(mealId));

       if(hasOtherMealSameDay){
           throw new IllegalStateException("Person already signed up for another meal on this date");

       }

       final Set<String> mealTags = meal.getTags() == null ? Set.of() : meal.getTags();

       Set<DietaryTag> personTags = person.getDietaryTags();
       if(personTags!= null && !personTags.isEmpty()){
           boolean violatesDiet = personTags.stream()
                   .filter(tag -> tag!=DietaryTag.NONE)
                   .anyMatch(tag -> !mealTags.contains(tag.name()));

           if(violatesDiet){
               throw new IllegalStateException("Meal does not satisfy person's dietary restrictions");
           }
       }

       //Capacity check
       long currentCount = signupRepository.countByMeal_Id(mealId);
       Integer maxAttendees = meal.getMaxAttendees();
       if (maxAttendees != null && currentCount >= maxAttendees) {
           throw new IllegalStateException("Meal is at full capacity");
       }

       Signup signup = Signup.builder()
               .person(person)
               .meal(meal)
               .note(signupCreateRequestDto.getNote())
               .build();
       return signupRepository.save(signup);







   }
}
