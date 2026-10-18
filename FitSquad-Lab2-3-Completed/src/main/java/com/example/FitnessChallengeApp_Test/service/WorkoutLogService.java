package com.example.FitnessChallengeApp_Test.service;

import java.util.List;
import java.util.Date;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Service;
import com.example.FitnessChallengeApp_Test.model.Challenge;
import com.example.FitnessChallengeApp_Test.model.WorkoutLog;

@Service
public class WorkoutLogService {
    private final List<WorkoutLog> logs = new CopyOnWriteArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(0);

    public List<WorkoutLog> getAllLogs() {
        return List.copyOf(logs);
    }

    public WorkoutLog getLogById(Long id) {
        if (id == null) return null;
        return logs.stream()
                .filter(log -> log.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public WorkoutLog addLog(WorkoutLog log, Challenge challenge) {
        log.setId(idGenerator.incrementAndGet());
        if (challenge != null) {
            log.setChallengeId(challenge.getId());
            log.setChallengeTitle(challenge.getTitle());
        }
        if (log.getCreatedAt() == null) {
            log.setCreatedAt(new Date());
        }
        logs.add(log);
        return log;
    }

    public List<String> getIntensityLevels() {
        return List.of("Low", "Moderate", "High", "Extreme");
    }
}
