package com.example.FitnessChallengeApp_Test.controller;
import com.example.FitnessChallengeApp_Test.model.*;
import com.example.FitnessChallengeApp_Test.service.*;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
@Controller
public class WorkoutLogController {
 private final WorkoutLogService workoutLogService;
 private final ChallengeService challengeService;
 public WorkoutLogController(WorkoutLogService workoutLogService,ChallengeService challengeService){this.workoutLogService=workoutLogService;this.challengeService=challengeService;}
 private void formData(Model model){model.addAttribute("challenges",challengeService.getAllChallenges());model.addAttribute("intensityLevels",workoutLogService.getIntensityLevels());model.addAttribute("pageTitle","Log a Workout | FitSquad Hub");}
 @GetMapping("/log-workout") public String showForm(Model model){model.addAttribute("workoutLog",new WorkoutLog());formData(model);return "log-workout";}
 @PostMapping("/log-workout") public String processLogWorkout(@Valid @ModelAttribute("workoutLog") WorkoutLog workoutLog,BindingResult bindingResult,Model model){
  Challenge selectedChallenge=workoutLog.getChallengeId()==null?null:challengeService.getChallengeById(workoutLog.getChallengeId());
  if(workoutLog.getChallengeId()!=null && (selectedChallenge==null || !selectedChallenge.isActive()))bindingResult.rejectValue("challengeId","invalid","Please select a valid active challenge");
  if(workoutLog.getIntensityLevel()!=null && !workoutLog.getIntensityLevel().isBlank() && !workoutLogService.getIntensityLevels().contains(workoutLog.getIntensityLevel()))bindingResult.rejectValue("intensityLevel","invalid","Please choose a listed intensity level");
  if(workoutLog.getWorkoutDate()!=null && !workoutLog.getWorkoutDate().isBlank()){
   try{LocalDate date=LocalDate.parse(workoutLog.getWorkoutDate());if(date.isAfter(LocalDate.now()))bindingResult.rejectValue("workoutDate","future","Workout date cannot be in the future");}
   catch(DateTimeParseException ex){bindingResult.rejectValue("workoutDate","invalid","Please enter a valid workout date");}
  }
  if(bindingResult.hasErrors()){formData(model);return "log-workout";}
  workoutLogService.addLog(workoutLog,selectedChallenge);return "redirect:/workout-logs?success";
 }
 @GetMapping("/workout-logs") public String showLogs(Model model){
  var logs=workoutLogService.getAllLogs();model.addAttribute("workoutLogs",logs);
  model.addAttribute("totalMinutes",logs.stream().mapToInt(x->x.getDurationMinutes()==null?0:x.getDurationMinutes()).sum());
  model.addAttribute("totalCalories",logs.stream().mapToInt(x->x.getCaloriesBurned()==null?0:x.getCaloriesBurned()).sum());
  model.addAttribute("totalAthletes",logs.stream().map(WorkoutLog::getAthleteName).distinct().count());
  model.addAttribute("pageTitle","Workout Dashboard | FitSquad Hub");return "workout-logs";
 }
}
