package com.example.FitnessChallengeApp_Test;

import com.example.FitnessChallengeApp_Test.controller.WorkoutLogController;
import com.example.FitnessChallengeApp_Test.model.Challenge;
import com.example.FitnessChallengeApp_Test.model.WorkoutLog;
import com.example.FitnessChallengeApp_Test.service.ChallengeService;
import com.example.FitnessChallengeApp_Test.service.WorkoutLogService;
import jakarta.validation.Validation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.validation.BeanPropertyBindingResult;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class WorkoutLoggingTest {
    private WorkoutLogService logs;
    private ChallengeService challenges;
    private WorkoutLogController controller;

    @BeforeEach
    void setUp() {
        logs = new WorkoutLogService();
        challenges = new ChallengeService();
        controller = new WorkoutLogController(logs, challenges);
    }

    private WorkoutLog validLog() {
        WorkoutLog log = new WorkoutLog();
        log.setAthleteName("Peter");
        log.setChallengeId(1L);
        log.setDurationMinutes(30);
        log.setCaloriesBurned(200);
        log.setIntensityLevel("Moderate");
        log.setWorkoutDate("2026-10-18");
        return log;
    }

    @Test
    void formOffersOnlyActiveChallengesAndAllIntensities() {
        var model = new ExtendedModelMap();
        assertEquals("log-workout", controller.showLogForm(model));
        assertNotNull(model.get("workoutLog"));
        @SuppressWarnings("unchecked")
        var options = (List<Challenge>) model.get("challenges");
        assertEquals(4, options.size());
        assertTrue(options.stream().allMatch(Challenge::isActive));
        assertEquals(List.of("Low", "Moderate", "High", "Extreme"), model.get("intensityLevels"));
    }

    @Test
    void validSubmissionAssociatesChallengeAndRedirects() {
        var log = validLog();
        log.setCreatedAt(null);
        var errors = new BeanPropertyBindingResult(log, "workoutLog");
        assertEquals("redirect:/workout-logs", controller.processLogWorkout(log, errors, new ExtendedModelMap()));
        assertEquals("30-Day Core Crusher", log.getChallengeTitle());
        assertNotNull(log.getCreatedAt());
        assertSame(log, logs.getLogById(log.getId()));
        logs.getAllLogs().clear();
        assertEquals(1, logs.getAllLogs().size());
        var second = logs.addLog(validLog(), challenges.getChallengeById(1L));
        assertNotEquals(log.getId(), second.getId());
        assertNull(logs.getLogById(999L));
    }

    @Test
    void invalidSubmissionRetainsFormOptionsWithoutSaving() {
        var log = validLog();
        var errors = new BeanPropertyBindingResult(log, "workoutLog");
        errors.rejectValue("athleteName", "NotBlank", "Athlete name is required");
        var model = new ExtendedModelMap();
        assertEquals("log-workout", controller.processLogWorkout(log, errors, model));
        assertTrue(model.containsAttribute("challenges"));
        assertTrue(model.containsAttribute("intensityLevels"));
        assertTrue(model.containsAttribute("pageTitle"));
        assertTrue(logs.getAllLogs().isEmpty());
    }

    @Test
    void rejectsInactiveMissingChallengesAndUnknownIntensity() {
        for (Long id : List.of(4L, 999L)) {
            var log = validLog();
            log.setChallengeId(id);
            log.setIntensityLevel("Unknown");
            var errors = new BeanPropertyBindingResult(log, "workoutLog");
            assertEquals("log-workout", controller.processLogWorkout(log, errors, new ExtendedModelMap()));
            assertTrue(errors.hasFieldErrors("challengeId"));
            assertTrue(errors.hasFieldErrors("intensityLevel"));
        }
        assertTrue(logs.getAllLogs().isEmpty());
    }

    @Test
    void beanValidationChecksRequiredFieldsAndNumericBounds() {
        try (var factory = Validation.buildDefaultValidatorFactory()) {
            var validator = factory.getValidator();
            Set<String> missing = validator.validate(new WorkoutLog()).stream()
                    .map(v -> v.getPropertyPath().toString()).collect(Collectors.toSet());
            assertTrue(missing.containsAll(Set.of("athleteName", "challengeId", "durationMinutes",
                    "caloriesBurned", "intensityLevel", "workoutDate")));
            for (int[] pair : new int[][]{{5, 10}, {360, 4000}}) {
                var log = validLog();
                log.setDurationMinutes(pair[0]);
                log.setCaloriesBurned(pair[1]);
                assertTrue(validator.validate(log).isEmpty());
            }
            for (int[] pair : new int[][]{{4, 9}, {361, 4001}}) {
                var log = validLog();
                log.setDurationMinutes(pair[0]);
                log.setCaloriesBurned(pair[1]);
                log.setNotes("x".repeat(251));
                var invalid = validator.validate(log).stream().map(v -> v.getPropertyPath().toString())
                        .collect(Collectors.toSet());
                assertTrue(invalid.containsAll(Set.of("durationMinutes", "caloriesBurned", "notes")));
            }
        }
    }

    @Test
    void dashboardProvidesEmptyAndPopulatedLists() {
        var model = new ExtendedModelMap();
        assertEquals("workout-logs", controller.showWorkoutLogs(model));
        assertEquals(List.of(), model.get("workoutLogs"));
        logs.addLog(validLog(), challenges.getChallengeById(1L));
        controller.showWorkoutLogs(model);
        assertEquals(logs.getAllLogs(), model.get("workoutLogs"));
    }
}
