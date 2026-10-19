package com.example.FitnessChallengeApp_Test.service;

import com.example.FitnessChallengeApp_Test.model.WorkoutLog;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class WorkoutLogService {

    private final List<WorkoutLog> workoutLogs = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    public List<WorkoutLog> getAllWorkoutLogs() {
        return new ArrayList<>(workoutLogs);
    }

    public void addWorkoutLog(WorkoutLog workoutLog) {
        workoutLog.setId(idCounter.getAndIncrement());
        workoutLogs.add(workoutLog);
    }

    public WorkoutLog getWorkoutLogById(Long id) {
        return workoutLogs.stream()
                .filter(log -> log.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}