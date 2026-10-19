package com.example.FitnessChallengeApp_Test;

import com.example.FitnessChallengeApp_Test.controller.WorkoutLogController;
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

    private WorkoutLog validLog() {
        WorkoutLog log = new WorkoutLog();
        log.setAthleteName("John Doe");
        log.setChallengeId(1L);
        log.setDurationMinutes(45);
        log.setCaloriesBurned(350);
        log.setIntensityLevel("High");
        log.setWorkoutDate("2026-09-20");
        log.setNotes("Felt great");
        return log;
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
    }

    @Test
    void processLogWorkout_withValidationErrors_shouldReturnFormView() {
        WorkoutLog invalid = new WorkoutLog();
        BindingResult bindingResult = new BeanPropertyBindingResult(invalid, "workoutLog");
        bindingResult.rejectValue("athleteName", "NotBlank", "Athlete name is required");

        Model model = new ExtendedModelMap();
        String viewName = controller.processLogWorkout(invalid, bindingResult, model);

        assertEquals("log-workout", viewName);
        assertTrue(model.containsAttribute("challenges"));
        assertTrue(model.containsAttribute("intensityLevels"));
        assertEquals(0, workoutLogService.getAllLogs().size());
    }

    @Test
    void processLogWorkout_validSubmission_shouldSaveLogAndRedirect() {
        WorkoutLog log = validLog();
        BindingResult bindingResult = new BeanPropertyBindingResult(log, "workoutLog");

        String viewName = controller.processLogWorkout(log, bindingResult, new ExtendedModelMap());

        assertEquals("redirect:/workout-logs", viewName);
        assertEquals(1, workoutLogService.getAllLogs().size());

        WorkoutLog saved = workoutLogService.getAllLogs().get(0);
        assertNotNull(saved.getId());
        assertEquals("30-Day Core Crusher", saved.getChallengeTitle());
    }

    @Test
    void showWorkoutLogs_shouldPopulateModelAndReturnView() {
        Model model = new ExtendedModelMap();
        String viewName = controller.showWorkoutLogs(model);

        assertEquals("workout-logs", viewName);
        assertTrue(model.containsAttribute("workoutLogs"));
        assertTrue(model.containsAttribute("pageTitle"));
    }

    @Test
    void service_shouldReturnIntensityLevelsAndFindById() {
        assertEquals(List.of("Low", "Moderate", "High", "Extreme"), workoutLogService.getIntensityLevels());

        WorkoutLog saved = workoutLogService.addLog(validLog(), challengeService.getChallengeById(2L));
        assertEquals(saved, workoutLogService.getLogById(saved.getId()));
        assertNull(workoutLogService.getLogById(999L));
    }
}
