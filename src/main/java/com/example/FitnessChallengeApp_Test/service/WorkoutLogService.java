package com.example.FitnessChallengeApp_Test.service;
import com.example.FitnessChallengeApp_Test.model.*;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;
@Service
public class WorkoutLogService {
 private final CopyOnWriteArrayList<WorkoutLog> logs = new CopyOnWriteArrayList<>();
 private final AtomicLong idGenerator = new AtomicLong(0);
 public List<WorkoutLog> getAllLogs(){ List<WorkoutLog> result=new ArrayList<>(logs); Collections.reverse(result); return result; }
 public WorkoutLog getLogById(Long id){ return logs.stream().filter(log -> Objects.equals(log.getId(),id)).findFirst().orElse(null); }
 public WorkoutLog addLog(WorkoutLog log, Challenge challenge){
  log.setId(idGenerator.incrementAndGet());
  if(challenge!=null){log.setChallengeId(challenge.getId());log.setChallengeTitle(challenge.getTitle());}
  if(log.getCreatedAt()==null)log.setCreatedAt(new Date());
  logs.add(log);return log;
 }
 public List<String> getIntensityLevels(){return List.of("Low","Moderate","High","Extreme");}
}
