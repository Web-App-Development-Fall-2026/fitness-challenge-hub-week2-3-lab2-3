
package com.example.FitnessChallengeApp_Test.service;

import com.example.FitnessChallengeApp_Test.model.Challenge;
import com.example.FitnessChallengeApp_Test.model.WorkoutLog;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class WorkoutLogService {

    private final List<WorkoutLog> workoutLogs =
            new CopyOnWriteArrayList<>();

    private final AtomicLong idGenerator = new AtomicLong(0);

    public List<WorkoutLog> getAllLogs() {
        List<WorkoutLog> logs = new ArrayList<>(workoutLogs);

        logs.sort(Comparator.comparing(
                WorkoutLog::getId,
                Comparator.reverseOrder()));

        return logs;
    }

    public WorkoutLog getLogById(Long id) {
        return workoutLogs.stream()
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
            log.setCreatedAt(new java.util.Date());
        }

        workoutLogs.add(log);
        return log;
    }

    public List<String> getIntensityLevels() {
        return List.of("Low", "Moderate", "High", "Extreme");
    }
}
