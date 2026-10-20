
package com.example.FitnessChallengeApp_Test.model;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.AllArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
public class Challenge {

    private Long id;

    private Date createdAt = new Date();

    @NotBlank(message = "Challenge title is required")
    @Size(min = 3, max = 60,
          message = "Title must be between 3 and 60 characters")
    private String title;

    @NotBlank(message = "Please select a category")
    private String category;

    @NotBlank(message = "Description is required")
    @Size(min = 10, max = 500,
          message = "Description must be between 10 and 500 characters")
    private String description;

    @NotBlank(message = "Target goal is required")
    @Size(min = 5, max = 100,
          message = "Target goal must be between 5 and 100 characters")
    private String targetGoal;

    @Min(value = 1,
         message = "Duration must be at least 1 day")
    @Max(value = 365,
         message = "Duration cannot exceed 365 days")
    private int durationDays;

    @Min(value = 50,
         message = "Reward points must be at least 50")
    @Max(value = 5000,
         message = "Reward points cannot exceed 5000")
    private int rewardPoints;

    @NotBlank(message = "Please select a difficulty")
    private String difficulty;

    private String imageUrl;

    private boolean active = true;

    // Empty constructor
    public Challenge() {
    }

    // Constructor for creating challenges
    public Challenge(
            Long id,
            String title,
            String category,
            String description,
            String targetGoal,
            int durationDays,
            int rewardPoints,
            String difficulty,
            String imageUrl,
            boolean active) {

        this.id = id;
        this.createdAt = new Date();
        this.title = title;
        this.category = category;
        this.description = description;
        this.targetGoal = targetGoal;
        this.durationDays = durationDays;
        this.rewardPoints = rewardPoints;
        this.difficulty = difficulty;
        this.imageUrl = imageUrl;
        this.active = active;
    }
}
