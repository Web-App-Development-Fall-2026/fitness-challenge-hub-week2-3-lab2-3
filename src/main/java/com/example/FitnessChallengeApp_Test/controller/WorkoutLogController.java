package com.example.FitnessChallengeApp_Test.controller;

import com.example.FitnessChallengeApp_Test.model.Challenge;
import com.example.FitnessChallengeApp_Test.model.WorkoutLog;
import com.example.FitnessChallengeApp_Test.service.ChallengeService;
import com.example.FitnessChallengeApp_Test.service.WorkoutLogService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for the Athlete Workout Logging feature.
 */
@Controller
public class WorkoutLogController {

    private final WorkoutLogService workoutLogService;
    private final ChallengeService challengeService;

    public WorkoutLogController(WorkoutLogService workoutLogService,
                                ChallengeService challengeService) {
        this.workoutLogService = workoutLogService;
        this.challengeService = challengeService;
    }

    // Only active challenges can be selected when logging a workout
    private List<Challenge> getActiveChallenges() {
        List<Challenge> active = new ArrayList<>();
        for (Challenge c : challengeService.getAllChallenges()) {
            if (c.isActive()) {
                active.add(c);
            }
        }
        return active;
    }

    @GetMapping("/log-workout")
    public String showLogWorkoutForm(Model model) {
        model.addAttribute("workoutLog", new WorkoutLog());
        model.addAttribute("challenges", getActiveChallenges());
        model.addAttribute("intensityLevels", workoutLogService.getIntensityLevels());
        model.addAttribute("pageTitle", "FitSquad Hub - Log Workout");
        return "log-workout";
    }

    @PostMapping("/log-workout")
    public String processLogWorkout(
            @Valid @ModelAttribute("workoutLog") WorkoutLog workoutLog,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("challenges", getActiveChallenges());
            model.addAttribute("intensityLevels", workoutLogService.getIntensityLevels());
            model.addAttribute("pageTitle", "FitSquad Hub - Log Workout");
            return "log-workout";
        }

        Challenge selectedChallenge = challengeService.getChallengeById(workoutLog.getChallengeId());
        workoutLogService.addLog(workoutLog, selectedChallenge);

        // Post-Redirect-Get pattern
        return "redirect:/workout-logs";
    }

    @GetMapping("/workout-logs")
    public String showWorkoutLogs(Model model) {
        model.addAttribute("workoutLogs", workoutLogService.getAllLogs());
        model.addAttribute("pageTitle", "FitSquad Hub - Workout Logs");
        return "workout-logs";
    }
}
