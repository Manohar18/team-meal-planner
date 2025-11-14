package com.manohar.team_meal_planner.exception;

import java.time.LocalDateTime;
import java.util.List;


public class ApiError {

    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
    private List<String> details;
}
