package com.example.FitnessChallengeApp_Test.service;

import com.example.FitnessChallengeApp_Test.model.Challenge;
import com.example.FitnessChallengeApp_Test.model.WorkoutLog;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Service managing in-memory storage of workout logs.
 */
@Service
public class WorkoutLogService {

    // Thread-safe in-memory list and ID generator
    private final List<WorkoutLog> logs = new CopyOnWriteArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(0);

    // Returns a copy of all logs, most recent first
    public List<WorkoutLog> getAllLogs() {
        List<WorkoutLog> copy = new ArrayList<>(logs);
        Collections.reverse(copy);
        return copy;
    }

    // Returns the log with the given id, or null if not found
    public WorkoutLog getLogById(Long id) {
        if (id == null) {
            return null;
        }
        for (WorkoutLog log : logs) {
            if (id.equals(log.getId())) {
                return log;
            }
        }
        return null;
    }

    // Assigns an ID, links the challenge info, and stores the log
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
