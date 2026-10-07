package com.example.FitnessChallengeApp_Test.service;

import com.example.FitnessChallengeApp_Test.model.Challenge;
import com.example.FitnessChallengeApp_Test.model.WorkoutLog;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class WorkoutLogService {

    private final List<WorkoutLog> workoutLogs = new CopyOnWriteArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(0);

    public List<WorkoutLog> getAllLogs() {
        return new ArrayList<>(workoutLogs);
    }

    public WorkoutLog getLogById(Long id) {
        if (id == null) {
            return null;
        }
        return workoutLogs.stream()
                .filter(log -> id.equals(log.getId()))
                .findFirst()
                .orElse(null);
    }

    public WorkoutLog addLog(WorkoutLog log, Challenge challenge) {
        if (log == null) {
            return null;
        }

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

    public List<String> getIntensityLevels() {
        return List.of("Low", "Moderate", "High", "Extreme");
    }
}
