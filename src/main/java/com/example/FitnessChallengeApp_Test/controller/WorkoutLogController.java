package com.example.FitnessChallengeApp_Test.controller;

import com.example.FitnessChallengeApp_Test.model.Challenge;
import com.example.FitnessChallengeApp_Test.model.WorkoutLog;
import com.example.FitnessChallengeApp_Test.service.ChallengeService;
import com.example.FitnessChallengeApp_Test.service.WorkoutLogService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@Controller
public class WorkoutLogController {
    private final WorkoutLogService workoutLogService;
    private final ChallengeService challengeService;

    public WorkoutLogController(WorkoutLogService workoutLogService, ChallengeService challengeService) {
        this.workoutLogService = workoutLogService;
        this.challengeService = challengeService;
    }

    @InitBinder("workoutLog")
    public void configureBinding(WebDataBinder binder) {
        binder.setAllowedFields("athleteName", "challengeId", "durationMinutes",
                "caloriesBurned", "intensityLevel", "workoutDate", "notes");
    }

    private void populateForm(Model model) {
        model.addAttribute("challenges", challengeService.getAllChallenges().stream()
                .filter(Challenge::isActive).toList());
        model.addAttribute("intensityLevels", workoutLogService.getIntensityLevels());
        model.addAttribute("pageTitle", "FitSquad Hub - Log Workout");
    }

    @GetMapping("/log-workout")
    public String showLogForm(Model model) {
        model.addAttribute("workoutLog", new WorkoutLog());
        populateForm(model);
        return "log-workout";
    }

    @PostMapping("/log-workout")
    public String processLogWorkout(@Valid @ModelAttribute("workoutLog") WorkoutLog workoutLog,
                                   BindingResult bindingResult, Model model) {
        Challenge selectedChallenge = challengeService.getChallengeById(workoutLog.getChallengeId());
        if (!bindingResult.hasFieldErrors("challengeId")
                && (selectedChallenge == null || !selectedChallenge.isActive())) {
            bindingResult.rejectValue("challengeId", "challenge.invalid", "Please select an active challenge");
        }
        if (!bindingResult.hasFieldErrors("intensityLevel")
                && !workoutLogService.getIntensityLevels().contains(workoutLog.getIntensityLevel())) {
            bindingResult.rejectValue("intensityLevel", "intensity.invalid", "Please select a valid intensity level");
        }
        if (bindingResult.hasErrors()) {
            populateForm(model);
            return "log-workout";
        }
        workoutLogService.addLog(workoutLog, selectedChallenge);
        return "redirect:/workout-logs";
    }

    @GetMapping("/workout-logs")
    public String showWorkoutLogs(Model model) {
        model.addAttribute("workoutLogs", workoutLogService.getAllLogs());
        model.addAttribute("pageTitle", "FitSquad Hub - Workout Logs");
        return "workout-logs";
    }
}
