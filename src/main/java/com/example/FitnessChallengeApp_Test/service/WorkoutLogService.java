
package com.example.FitnessChallengeApp_Test.service;

import com.example.FitnessChallengeApp_Test.model.Challenge;
import com.example.FitnessChallengeApp_Test.model.WorkoutLog;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
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
        return new ArrayList<>(workoutLogs);
    }

    // Find a workout log by ID
    public WorkoutLog getLogById(Long id) {
        return workoutLogs.stream()
                .filter(log -> Objects.equals(log.getId(), id))
                .findFirst()
                .orElse(null);
    }

    // Save a new workout log
    public WorkoutLog addLog(WorkoutLog log, Challenge challenge) {

        log.setId(idGenerator.incrementAndGet());

        if (challenge != null) {
            log.setChallengeId(challenge.getId());
            log.setChallengeTitle(challenge.getTitle());
        }

        if (log.getCreatedAt() == null) {
            log.setCreatedAt(new Date());
        }

        workoutLogs.add(log);

        return log;
    }

    // Get available intensity levels
    public List<String> getIntensityLevels() {
        return List.of(
                "Low",
                "Moderate",
                "High",
                "Extreme"
        );
    }
}
