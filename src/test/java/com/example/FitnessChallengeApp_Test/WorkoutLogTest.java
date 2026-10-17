package com.example.FitnessChallengeApp_Test;

import com.example.FitnessChallengeApp_Test.controller.WorkoutLogController;
import com.example.FitnessChallengeApp_Test.model.WorkoutLog;
import com.example.FitnessChallengeApp_Test.service.ChallengeService;
import com.example.FitnessChallengeApp_Test.service.WorkoutLogService;
import jakarta.validation.Validation;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.validation.BeanPropertyBindingResult;
import static org.junit.jupiter.api.Assertions.*;

class WorkoutLogTest {
    private WorkoutLog validLog() {
        WorkoutLog log = new WorkoutLog();
        log.setAthleteName("Great Opiti");
        log.setChallengeId(1L);
        log.setDurationMinutes(30);
        log.setCaloriesBurned(200);
        log.setIntensityLevel("Moderate");
        log.setWorkoutDate("2026-10-15");
        return log;
    }

    @Test
    void validationEnforcesRequiredFieldsAndNumericBoundaries() {
        try (var factory = Validation.buildDefaultValidatorFactory()) {
            var validator = factory.getValidator();
            assertFalse(validator.validate(new WorkoutLog()).isEmpty());
            WorkoutLog log = validLog();
            assertTrue(validator.validate(log).isEmpty());
            log.setDurationMinutes(4);
            log.setCaloriesBurned(4001);
            log.setNotes("x".repeat(251));
            assertEquals(3, validator.validate(log).size());
            log.setDurationMinutes(5);
            log.setCaloriesBurned(10);
            log.setNotes("x".repeat(250));
            assertTrue(validator.validate(log).isEmpty());
            log.setDurationMinutes(360);
            log.setCaloriesBurned(4000);
            assertTrue(validator.validate(log).isEmpty());
        }
    }

    @Test
    void validSubmissionAssociatesChallengeAndRedirects() {
        var service = new WorkoutLogService();
        var controller = new WorkoutLogController(service, new ChallengeService());
        var log = validLog();
        log.setCreatedAt(null);
        assertEquals("redirect:/workout-logs", controller.processLogWorkout(log,
                new BeanPropertyBindingResult(log, "workoutLog"), new ExtendedModelMap()));
        assertEquals("30-Day Core Crusher", log.getChallengeTitle());
        assertNotNull(log.getCreatedAt());
        assertSame(log, service.getLogById(log.getId()));
        service.getAllLogs().clear();
        assertEquals(1, service.getAllLogs().size());
        assertNull(service.getLogById(999L));
        assertNotEquals(log.getId(), service.addLog(validLog(), null).getId());
    }

    @Test
    void inactiveAndUnknownChallengesReturnFormWithoutSaving() {
        var service = new WorkoutLogService();
        var controller = new WorkoutLogController(service, new ChallengeService());
        for (Long id : new Long[]{4L, 999L}) {
            var log = validLog();
            log.setChallengeId(id);
            var errors = new BeanPropertyBindingResult(log, "workoutLog");
            var model = new ExtendedModelMap();
            assertEquals("log-workout", controller.processLogWorkout(log, errors, model));
            assertTrue(errors.hasFieldErrors("challengeId"));
            assertTrue(model.containsAttribute("challenges"));
            assertTrue(model.containsAttribute("intensityLevels"));
        }
        assertTrue(service.getAllLogs().isEmpty());
    }

    @Test
    void bindingErrorsKeepFormAndSelections() {
        var service = new WorkoutLogService();
        var controller = new WorkoutLogController(service, new ChallengeService());
        var log = validLog();
        var errors = new BeanPropertyBindingResult(log, "workoutLog");
        errors.rejectValue("durationMinutes", "typeMismatch", "Enter a whole number");
        var model = new ExtendedModelMap();
        assertEquals("log-workout", controller.processLogWorkout(log, errors, model));
        assertTrue(model.containsAttribute("pageTitle"));
        assertTrue(service.getAllLogs().isEmpty());
    }
}
