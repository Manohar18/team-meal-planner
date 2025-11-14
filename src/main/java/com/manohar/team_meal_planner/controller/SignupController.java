package com.manohar.team_meal_planner.controller;


import com.manohar.team_meal_planner.dto.SignupCreateRequestDto;
import com.manohar.team_meal_planner.model.Signup;
import com.manohar.team_meal_planner.repository.SignupRepository;
import com.manohar.team_meal_planner.service.SignupService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/signup")
public class SignupController {

    private final SignupRepository signupRepository;
    private final SignupService signupService;


    public SignupController(SignupRepository signupRepository, SignupService signupService) {
        this.signupRepository = signupRepository;
        this.signupService = signupService;
    }

    @PostMapping
    public ResponseEntity<Signup> createSignup(@Valid @RequestBody SignupCreateRequestDto signupCreateRequestDto, UriComponentsBuilder uriComponentsBuilder){
        Signup signup = signupService.createSignup(signupCreateRequestDto);
        URI location = uriComponentsBuilder.path("/signups/{id}").buildAndExpand(signup.getId()).toUri();
        return ResponseEntity.created(location).body(signup);
    }

    public List<Signup> listSignups(@RequestParam("personId") Long personId,
                                    @RequestParam("date")
                                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate date,
                                    @RequestParam(name = "period", defaultValue = "DAT") String period){

        if("WEEK".equalsIgnoreCase(period)){
            List<Signup> result = new ArrayList<>();
            for(int i=0;i<7;i++){
                LocalDate day = date.plusDays(i);
                result.addAll(signupRepository.findByPerson_IdAndMeal_Date(personId, date));
            }

            return result;
        }
        return signupRepository.findByPerson_IdAndMeal_Date(personId, date);
    }

}
