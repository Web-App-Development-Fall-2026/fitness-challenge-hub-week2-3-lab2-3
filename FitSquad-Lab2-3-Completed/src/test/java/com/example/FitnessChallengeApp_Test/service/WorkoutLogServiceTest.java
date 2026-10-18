package com.example.FitnessChallengeApp_Test.service;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.example.FitnessChallengeApp_Test.model.Challenge;
import com.example.FitnessChallengeApp_Test.model.WorkoutLog;

class WorkoutLogServiceTest {
    @Test
    void addLogAssignsIdAndChallengeDetails() {
        WorkoutLogService service = new WorkoutLogService();
        Challenge challenge = new Challenge(7L, "Test Challenge", "Cardio", "Description", 10);
        WorkoutLog log = new WorkoutLog();
        log.setAthleteName("Harpreet");
        log.setChallengeId(7L);
        log.setDurationMinutes(30);
        log.setCaloriesBurned(250);
        log.setIntensityLevel("Moderate");
        log.setWorkoutDate("2026-10-18");

        WorkoutLog saved = service.addLog(log, challenge);

        assertEquals(1L, saved.getId());
        assertEquals("Test Challenge", saved.getChallengeTitle());
        assertNotNull(saved.getCreatedAt());
        assertEquals(1, service.getAllLogs().size());
    }

    @Test
    void returnsIntensityLevels() {
        WorkoutLogService service = new WorkoutLogService();
        assertEquals(java.util.List.of("Low", "Moderate", "High", "Extreme"),
                service.getIntensityLevels());
    }
}
