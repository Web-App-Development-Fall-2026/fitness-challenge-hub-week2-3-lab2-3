package com.example.FitnessChallengeApp_Test.service;

import org.springframework.stereotype.Service;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

import com.example.FitnessChallengeApp_Test.model.Challenge;
import com.example.FitnessChallengeApp_Test.model.WorkoutLog;
import java.util.List;

@Service 
public class WorkoutLogService {
    private final List<WorkoutLog> workoutLogs = new CopyOnWriteArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(0);

    public List<WorkoutLog> getAllLogs() {
        return workoutLogs;
    }

    public WorkoutLog getLogById(Long id) {
        return workoutLogs.stream()
                .filter(log -> log.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public WorkoutLog addLog(WorkoutLog workoutLog, Challenge challenge) {
        workoutLog.setId(idGenerator.incrementAndGet());
        if (challenge != null) {
            workoutLog.setChallengeId(challenge.getId());
            workoutLog.setChallengeTitle(challenge.getTitle());
        }
        
        workoutLogs.add(workoutLog);
        return workoutLog;
    }

    public List<String> getIntensityLevels() {
        return List.of("Low", "Moderate", "High", "Extreme");
    }
}
