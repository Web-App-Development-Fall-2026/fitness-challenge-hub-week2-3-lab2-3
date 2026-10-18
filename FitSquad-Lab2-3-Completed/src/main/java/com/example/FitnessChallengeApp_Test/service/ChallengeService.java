package com.example.FitnessChallengeApp_Test.service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Service;
import com.example.FitnessChallengeApp_Test.model.Challenge;

@Service
public class ChallengeService {
    private final List<Challenge> challenges = new CopyOnWriteArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(0);

    public ChallengeService() {
        addChallenge(new Challenge(null, "Daily Movement Quest", "Cardio",
                "Complete a daily cardio session and record your progress.", 30));
        addChallenge(new Challenge(null, "Strength Builder", "Strength",
                "Build a consistent strength-training routine.", 21));
        addChallenge(new Challenge(null, "Flexibility Focus", "Wellness",
                "Practice stretching and mobility exercises.", 14));
    }

    public List<Challenge> getAllChallenges() {
        return List.copyOf(challenges);
    }

    public Challenge getChallengeById(Long id) {
        if (id == null) return null;
        return challenges.stream()
                .filter(challenge -> challenge.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public Challenge addChallenge(Challenge challenge) {
        challenge.setId(idGenerator.incrementAndGet());
        challenges.add(challenge);
        return challenge;
    }
}
