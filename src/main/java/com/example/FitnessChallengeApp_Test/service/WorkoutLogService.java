import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import org.springframework.stereotype.Service;

import com.example.FitnessChallengeApp_Test.model.Challenge;
import com.example.FitnessChallengeApp_Test.model.WorkoutLog;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class WorkoutLogService {
    private final List<WorkoutLog> logs = new CopyOnWriteArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(0);
    private final Validator validator;
    public List<WorkoutLog> getAllLogs() {
        return Collections.unmodifiableList(new ArrayList<>(logs));
    }
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
    public WorkoutLog addLog(WorkoutLog log, Challenge challenge) {
        if(log == null){
            throw new IllegalArgumentException("WorkoutLog cannot be null");
        }
        Set<ConstraintViolation<WorkoutLog>> violations = validator.validate(log);
        if (!violations.isEmpty()) {
            String messages = violations.stream()
                .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                .collect(Collectors.joining(", "));
            throw new IllegalArgumentException("Validation failed: " + messages);
        }
        Long newId = idGenerator.incrementAndGet();
        log.setId(newId);

        if (challenge != null) {
            log.setChallengeId(challenge.getId());
            log.setChallengeTitle(challenge.getTitle());
        }

        if (log.getCreatedAt() == null) {
            log.setCreatedAt(new Date());
        }

        logs.add(log);
        return log;

        public List<String> getIntensityLevels() {
            return List.of("Low", "Moderate", "High", "Extreme");
        }
    }
}