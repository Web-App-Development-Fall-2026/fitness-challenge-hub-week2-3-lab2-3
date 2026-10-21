package com.example.FitnessChallengeApp_Test.service;
import com.example.FitnessChallengeApp_Test.model.*;import org.springframework.stereotype.Service;import java.util.*;import java.util.concurrent.CopyOnWriteArrayList;import java.util.concurrent.atomic.AtomicLong;
@Service public class WorkoutLogService {private final List<WorkoutLog> logs=new CopyOnWriteArrayList<>();private final AtomicLong idGenerator=new AtomicLong(0);
 public List<WorkoutLog> getAllLogs(){return new ArrayList<>(logs);}public WorkoutLog getLogById(Long id){return logs.stream().filter(l->Objects.equals(l.getId(),id)).findFirst().orElse(null);}
 public WorkoutLog addLog(WorkoutLog log,Challenge challenge){log.setId(idGenerator.incrementAndGet());if(challenge!=null){log.setChallengeId(challenge.getId());log.setChallengeTitle(challenge.getTitle());}if(log.getCreatedAt()==null)log.setCreatedAt(new Date());logs.add(0,log);return log;}
 public List<String> getIntensityLevels(){return List.of("Low","Moderate","High","Extreme");}}
