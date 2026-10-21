package com.example.FitnessChallengeApp_Test.service;
import com.example.FitnessChallengeApp_Test.model.Challenge;import org.springframework.stereotype.Service;import java.util.*;import java.util.concurrent.CopyOnWriteArrayList;import java.util.concurrent.atomic.AtomicLong;
@Service public final class ChallengeService {
 private final List<Challenge> challenges=new CopyOnWriteArrayList<>();private final AtomicLong ids=new AtomicLong();
 public ChallengeService(){addChallenge(new Challenge(null,"30-Day Core Crusher","Strength & Core","Build a stronger core through daily exercises","15 minutes daily",30,450,"Beginner","",true));addChallenge(new Challenge(null,"Cardio Sprint","Cardio & HIIT","Improve endurance with cardio intervals","20 minutes daily",14,300,"Intermediate","",true));}
 public List<Challenge> getAllChallenges(){return new ArrayList<>(challenges);}public Challenge getChallengeById(Long id){return challenges.stream().filter(c->Objects.equals(c.getId(),id)).findFirst().orElse(null);}public Challenge addChallenge(Challenge c){c.setId(ids.incrementAndGet());challenges.add(c);return c;}public List<String> getCategories(){return List.of("Strength & Core","Cardio & HIIT","Flexibility","Endurance");}public List<String> getDifficulties(){return List.of("Beginner","Intermediate","Advanced");}
}
