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

@Service
public class WorkoutLogService {

    List<WorkoutLog> workoutLogs = new CopyOnWriteArrayList<>();
    AtomicLong idGenerator = new AtomicLong(0);

    public List<WorkoutLog> getAllLogs() {
        List<WorkoutLog> copy = new ArrayList<>(workoutLogs);
        Collections.reverse(copy);
        return copy;
    }

    public WorkoutLog getLogById(Long id) {
        if (id == null) {
            return null;
        }
        for (WorkoutLog log : workoutLogs) {
            if (id.equals(log.getId())) {
                return log;
            }
        }
        return null;
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