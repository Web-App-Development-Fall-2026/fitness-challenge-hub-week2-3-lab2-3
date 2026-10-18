package com.example.FitnessChallengeApp_Test.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Domain model representing a fitness challenge in FitSquad Hub.
 * Uses standard Lombok annotations for getters/setters and constructors.
 * Note: Builder pattern is intentionally omitted per project specifications.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Challenge {
    private Long id;
    private String title;
    private String category;
    private String description;
    private String targetGoal;
    private int durationDays;
    private int rewardPoints;
    private String difficulty;
    private String imageUrl;
    private boolean active;
}
