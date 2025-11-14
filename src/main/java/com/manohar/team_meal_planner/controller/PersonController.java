package com.manohar.team_meal_planner.controller;


import com.manohar.team_meal_planner.dto.PersonCreateRequestDto;
import com.manohar.team_meal_planner.model.Person;
import com.manohar.team_meal_planner.service.PersonService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/people")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService){
        this.personService = personService;
    }

    //Create a new person
    @PostMapping
    public ResponseEntity createPerson(@Valid @RequestBody PersonCreateRequestDto personCreateRequestDto, UriComponentsBuilder uriComponentsBuilder){
        Person created = personService.createPerson(personCreateRequestDto);
        URI location = uriComponentsBuilder.path("/people/{id}").buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(location).body(created);
    }

    @GetMapping("/{id}")
    public Person getPerson(@PathVariable Long id){
        return personService.getPersonById(id);
    }

    @GetMapping
    public List<Person> getAllPersons(){
        return personService.getAllPersons();
    }


}
