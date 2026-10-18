package com.example.FitnessChallengeApp_Test.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Challenge {

    private Long id;

    @NotBlank(message = "Challenge title is required")
    @Size(min = 3, max = 60, message = "Challenge title must be between 3 and 60 characters")
    private String title;

    @NotBlank(message = "Please select a category")
    private String category;

    @NotBlank(message = "Challenge description is required")
    @Size(max = 250, message = "Description cannot exceed 250 characters")
    private String description;

    @Min(value = 1, message = "Duration must be at least 1 day")
    @Max(value = 365, message = "Duration cannot exceed 365 days")
    private Integer durationDays;

    public Challenge() {
    }

    public Challenge(Long id,
                     String title,
                     String category,
                     String description,
                     Integer durationDays) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.description = description;
        this.durationDays = durationDays;
    }
}