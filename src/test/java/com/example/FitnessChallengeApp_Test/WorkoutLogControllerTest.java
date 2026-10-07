package com.example.FitnessChallengeApp_Test;

import com.example.FitnessChallengeApp_Test.controller.WorkoutLogController;
import com.example.FitnessChallengeApp_Test.model.Challenge;
import com.example.FitnessChallengeApp_Test.model.WorkoutLog;
import com.example.FitnessChallengeApp_Test.service.ChallengeService;
import com.example.FitnessChallengeApp_Test.service.WorkoutLogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WorkoutLogControllerTest {

    private ChallengeService challengeService;
    private WorkoutLogService workoutLogService;
    private WorkoutLogController controller;

    @BeforeEach
    void setUp() {
        challengeService = new ChallengeService();
        workoutLogService = new WorkoutLogService();
        controller = new WorkoutLogController(workoutLogService, challengeService);
    }

    @Test
    void showLogWorkoutForm_shouldPopulateModelAndReturnView() {
        Model model = new ExtendedModelMap();

        String viewName = controller.showLogWorkoutForm(model);

        assertEquals("log-workout", viewName);
        assertTrue(model.containsAttribute("workoutLog"));
        assertTrue(model.containsAttribute("challenges"));
        assertTrue(model.containsAttribute("intensityLevels"));
        assertTrue(model.containsAttribute("pageTitle"));
        assertEquals(6, ((List<?>) model.getAttribute("challenges")).size());
        assertEquals(List.of("Low", "Moderate", "High", "Extreme"), model.getAttribute("intensityLevels"));
    }

    @Test
    void processLogWorkout_withValidationErrors_shouldReturnFormView() {
        WorkoutLog invalidLog = new WorkoutLog();
        BindingResult bindingResult = new BeanPropertyBindingResult(invalidLog, "workoutLog");
        bindingResult.rejectValue("athleteName", "NotBlank", "Athlete name is required");
        Model model = new ExtendedModelMap();

        String viewName = controller.processLogWorkout(invalidLog, bindingResult, model);

        assertEquals("log-workout", viewName);
        assertTrue(model.containsAttribute("challenges"));
        assertTrue(model.containsAttribute("intensityLevels"));
        assertEquals(0, workoutLogService.getAllLogs().size());
    }

    @Test
    void processLogWorkout_validSubmission_shouldSaveLogAndRedirect() {
        WorkoutLog validLog = new WorkoutLog();
        validLog.setAthleteName("Alex Runner");
        validLog.setChallengeId(1L);
        validLog.setDurationMinutes(30);
        validLog.setCaloriesBurned(250);
        validLog.setIntensityLevel("Moderate");
        validLog.setWorkoutDate("2026-10-06");
        validLog.setNotes("Strong session");
        BindingResult bindingResult = new BeanPropertyBindingResult(validLog, "workoutLog");
        Model model = new ExtendedModelMap();

        String viewName = controller.processLogWorkout(validLog, bindingResult, model);

        assertEquals("redirect:/workout-logs", viewName);
        assertEquals(1, workoutLogService.getAllLogs().size());
        WorkoutLog saved = workoutLogService.getAllLogs().get(0);
        assertEquals("30-Day Core Crusher", saved.getChallengeTitle());
        assertEquals(1L, saved.getChallengeId());
        assertNotNull(saved.getCreatedAt());
    }

    @Test
    void showWorkoutLogs_shouldPopulateModelAndReturnView() {
        Model model = new ExtendedModelMap();

        String viewName = controller.showWorkoutLogs(model);

        assertEquals("workout-logs", viewName);
        assertTrue(model.containsAttribute("workoutLogs"));
        assertTrue(model.containsAttribute("pageTitle"));
    }
}
