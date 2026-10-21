
package com.example.FitnessChallengeApp_Test.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private boolean active = true;
}
