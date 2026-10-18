package com.example.FitnessChallengeApp_Test.service;

import com.example.FitnessChallengeApp_Test.model.Challenge;
import com.example.FitnessChallengeApp_Test.model.WorkoutLog;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Date;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class WorkoutLogService {

    private final List<WorkoutLog> workoutLogs =
            new CopyOnWriteArrayList<>();

    private final AtomicLong idGenerator =
            new AtomicLong(0);

    // Get all workout logs
    public List<WorkoutLog> getAllLogs() {
        return List.copyOf(workoutLogs);
    }

    // Get workout log by ID
    public WorkoutLog getLogById(Long id) {

        for (WorkoutLog log : workoutLogs) {

            if (log.getId().equals(id)) {
                return log;
            }
        }

        return null;
    }

    // Add a new workout log
    public WorkoutLog addLog(WorkoutLog log, Challenge challenge) {

        // Generate unique ID
        log.setId(idGenerator.incrementAndGet());

        // Connect workout to challenge
        if (challenge != null) {

            log.setChallengeId(challenge.getId());
            log.setChallengeTitle(challenge.getTitle());
        }

        // Set creation date
        if (log.getCreatedAt() == null) {
            log.setCreatedAt(new Date());
        }

        // Save workout
        workoutLogs.add(log);

        return log;
    }

    // Available intensity levels
    public List<String> getIntensityLevels() {

        return List.of(
                "Low",
                "Moderate",
                "High",
                "Extreme"
        );
    }
}