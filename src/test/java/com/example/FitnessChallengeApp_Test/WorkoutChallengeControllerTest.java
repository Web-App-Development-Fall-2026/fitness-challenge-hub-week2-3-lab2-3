package com.example.FitnessChallengeApp_Test;

import com.example.FitnessChallengeApp_Test.controller.WorkoutChallengeController;
import com.example.FitnessChallengeApp_Test.model.Challenge;
import com.example.FitnessChallengeApp_Test.service.ChallengeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WorkoutChallengeControllerTest {

    private ChallengeService challengeService;
    private WorkoutChallengeController controller;

    @BeforeEach
    void setUp() {
        challengeService = new ChallengeService();
        controller = new WorkoutChallengeController(challengeService);
    }

    @Test
    void showChallenges_shouldPopulateModelAndReturnView() {
        Model model = new ExtendedModelMap();
        String viewName = controller.showChallenges(model);

        assertEquals("challenges", viewName);
        assertTrue(model.containsAttribute("pageTitle"));
        assertTrue(model.containsAttribute("challenges"));

        @SuppressWarnings("unchecked")
        List<Challenge> challenges = (List<Challenge>) model.getAttribute("challenges");
        assertNotNull(challenges);
        assertEquals(6, challenges.size());

        Challenge firstChallenge = challenges.get(0);
        assertEquals(1L, firstChallenge.getId());
        assertEquals("30-Day Core Crusher", firstChallenge.getTitle());
    }

    @Test
    void showCreateForm_shouldPrepareEmptyModelAndReturnView() {
        Model model = new ExtendedModelMap();
        String viewName = controller.showCreateForm(model);

        assertEquals("create-challenge", viewName);
        assertTrue(model.containsAttribute("challenge"));
        assertTrue(model.containsAttribute("categories"));
        assertTrue(model.containsAttribute("difficulties"));

        @SuppressWarnings("unchecked")
        List<String> categories = (List<String>) model.getAttribute("categories");
        assertNotNull(categories);
        assertTrue(categories.contains("Strength & Core"));
    }

    @Test
    void processCreateChallenge_withValidationErrors_shouldReturnFormView() {
        Challenge invalidChallenge = new Challenge(); // Missing title, category, etc.
        BindingResult bindingResult = new BeanPropertyBindingResult(invalidChallenge, "challenge");
        bindingResult.rejectValue("title", "NotBlank", "Challenge title is required");

        Model model = new ExtendedModelMap();
        String viewName = controller.processCreateChallenge(invalidChallenge, bindingResult, model);

        assertEquals("create-challenge", viewName);
        assertTrue(model.containsAttribute("categories"));
        assertTrue(model.containsAttribute("difficulties"));
        assertEquals(6, challengeService.getAllChallenges().size()); // No new challenge added
    }

    @Test
    void processCreateChallenge_validSubmission_shouldAddChallengeAndRedirect() {
        Challenge validChallenge = new Challenge(
                null,
                "Spartan 100 Burpee Blast",
                "Cardio & HIIT",
                "Test burpee endurance progression for high metabolic conditioning.",
                "100 burpees unbroken or partitioned daily",
                14,
                600,
                "Advanced",
                null,
                true
        );
        BindingResult bindingResult = new BeanPropertyBindingResult(validChallenge, "challenge");
        Model model = new ExtendedModelMap();

        String viewName = controller.processCreateChallenge(validChallenge, bindingResult, model);

        assertEquals("redirect:/challenges", viewName);
        assertEquals(7, challengeService.getAllChallenges().size());
        Challenge added = challengeService.getAllChallenges().get(6);
        assertEquals("Spartan 100 Burpee Blast", added.getTitle());
        assertNotNull(added.getId());
    }
}
