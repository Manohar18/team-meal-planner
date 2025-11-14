package com.manohar.team_meal_planner.service.impl;

import com.manohar.team_meal_planner.dto.PersonCreateRequestDto;
import com.manohar.team_meal_planner.model.Person;
import com.manohar.team_meal_planner.repository.PersonRepository;
import com.manohar.team_meal_planner.service.PersonService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;


@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    @Override
    @Transactional
    public Person createPerson(PersonCreateRequestDto personCreateRequestDto){
        if(personCreateRequestDto == null)
        {
            throw new IllegalArgumentException("PersonCreateRequestDto cannot be null");
        }

        String email = personCreateRequestDto.getEmail().trim().toLowerCase();
        if(personRepository.existsByEmail(email)){
            throw new IllegalArgumentException("Email already in use:" +email);
        }

        Person person = Person.builder().name(personCreateRequestDto.getName().trim())
                .email(email)
                .dietaryTags(personCreateRequestDto.getDietaryTags())
                .build();

        return personRepository.save(person);
    }

    @Override
    public Person getPersonById(Long id){
       return personRepository.findById(id)
               .orElseThrow(() -> new NoSuchElementException("Person not found with id:" + id));
    }
}
