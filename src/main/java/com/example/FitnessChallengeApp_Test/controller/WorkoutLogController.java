package com.example.FitnessChallengeApp_Test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.FitnessChallengeApp_Test.model.Challenge;
import com.example.FitnessChallengeApp_Test.model.WorkoutLog;
import com.example.FitnessChallengeApp_Test.service.ChallengeService;
import com.example.FitnessChallengeApp_Test.service.WorkoutLogService;

import jakarta.validation.Valid;

@Controller
public class WorkoutLogController {

    private final ChallengeService challengeService;
    private final WorkoutLogService workoutLogService;
    
    public WorkoutLogController(ChallengeService challengeService, WorkoutLogService workoutLogService) {
        this.challengeService = challengeService;
        this.workoutLogService = workoutLogService;
    }

    @GetMapping("/log-workout")
    public String getWorkoutLogForm(Model model) {
        model.addAttribute("workoutLog", new WorkoutLog());
        model.addAttribute("challenges", challengeService.getAllChallenges());
        model.addAttribute("intensityLevels", workoutLogService.getIntensityLevels());
        model.addAttribute("pageTitle", "Log Workout");
        return "log-workout";
    }

    @PostMapping("/log-workout")
    public String processLogWorkout(
            @Valid @ModelAttribute("workoutLog") WorkoutLog workoutLog,
            BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("challenges", challengeService.getAllChallenges());
            model.addAttribute("intensityLevels", workoutLogService.getIntensityLevels());
            model.addAttribute("pageTitle", "Log Workout");
            return "log-workout";
        }

        Challenge selectedChallenge = challengeService.getChallengeById(workoutLog.getChallengeId());
        workoutLogService.addLog(workoutLog, selectedChallenge);

        return "redirect:/workout-logs";
    }

    @GetMapping("/workout-logs")
    public String showWorkoutLogs(Model model) {
        model.addAttribute("workoutLogs", workoutLogService.getAllLogs());
        model.addAttribute("pageTitle", "Workout Logs");
        return "workout-logs";
    }
}