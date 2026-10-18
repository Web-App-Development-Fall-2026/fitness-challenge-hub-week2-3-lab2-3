package com.example.FitnessChallengeApp_Test;

import com.example.FitnessChallengeApp_Test.controller.WorkoutChallengeController;
import com.example.FitnessChallengeApp_Test.model.Challenge;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WorkoutChallengeControllerTest {

    private final WorkoutChallengeController controller = new WorkoutChallengeController();

    @Test
    void showChallenges_shouldPopulateModelAndReturnView() {
        Model model = new ExtendedModelMap();
        String viewName = controller.showChallenges(model);

        // Verify view name
        assertEquals("challenges", viewName);

        // Verify pageTitle attribute
        assertTrue(model.containsAttribute("pageTitle"));
        String pageTitle = (String) model.getAttribute("pageTitle");
        assertNotNull(pageTitle);
        assertTrue(pageTitle.contains("FitSquad Hub"));

        // Verify challenges attribute
        assertTrue(model.containsAttribute("challenges"));
        @SuppressWarnings("unchecked")
        List<Challenge> challenges = (List<Challenge>) model.getAttribute("challenges");
        assertNotNull(challenges);
        assertEquals(6, challenges.size());

        // Verify specific challenge properties
        Challenge firstChallenge = challenges.get(0);
        assertEquals(1L, firstChallenge.getId());
        assertEquals("30-Day Core Crusher", firstChallenge.getTitle());
        assertEquals("Strength & Core", firstChallenge.getCategory());
        assertEquals(30, firstChallenge.getDurationDays());
        assertEquals(450, firstChallenge.getRewardPoints());
        assertEquals("Intermediate", firstChallenge.getDifficulty());
        assertTrue(firstChallenge.isActive());
        assertNotNull(firstChallenge.getImageUrl());
        assertNotNull(firstChallenge.getTargetGoal());

        // Verify inactive challenge exists
        boolean hasInactive = challenges.stream().anyMatch(c -> !c.isActive());
        assertTrue(hasInactive, "Should contain inactive challenges to demonstrate th:unless");
    }
}
