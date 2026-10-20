
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

import java.util.List;

@Controller
public class WorkoutLogController {

    private final WorkoutLogService workoutLogService;
    private final ChallengeService challengeService;

    public WorkoutLogController(
            WorkoutLogService workoutLogService,
            ChallengeService challengeService) {

        this.workoutLogService = workoutLogService;
        this.challengeService = challengeService;
    }

    // Show workout logging form
    @GetMapping("/log-workout")
    public String showLogWorkoutForm(Model model) {

        model.addAttribute("workoutLog", new WorkoutLog());

        model.addAttribute("challenges", getActiveChallenges());

        model.addAttribute("intensityLevels",
                workoutLogService.getIntensityLevels());

        model.addAttribute("pageTitle", "Log Workout");

        return "log-workout";
    }

    // Process workout form submission
    @PostMapping("/log-workout")
    public String processLogWorkout(
            @Valid @ModelAttribute("workoutLog") WorkoutLog workoutLog,
            BindingResult bindingResult,
            Model model) {

        Challenge selectedChallenge = null;

        if (workoutLog.getChallengeId() != null) {
            selectedChallenge = challengeService.getChallengeById(
                    workoutLog.getChallengeId());

            if (selectedChallenge == null ||
                    !selectedChallenge.isActive()) {

                bindingResult.rejectValue(
                        "challengeId",
                        "invalid.challenge",
                        "Please select an active challenge");
            }
        }

        if (bindingResult.hasErrors()) {

            model.addAttribute("challenges", getActiveChallenges());

            model.addAttribute("intensityLevels",
                    workoutLogService.getIntensityLevels());

            model.addAttribute("pageTitle", "Log Workout");

            return "log-workout";
        }

        workoutLogService.addLog(workoutLog, selectedChallenge);

        return "redirect:/workout-logs";
    }

    // Display saved workout logs
    @GetMapping("/workout-logs")
    public String showWorkoutLogs(Model model) {

        model.addAttribute("workoutLogs",
                workoutLogService.getAllLogs());

        model.addAttribute("pageTitle", "Workout Logs");

        return "workout-logs";
    }

    // Get active challenges only
    private List<Challenge> getActiveChallenges() {

        return challengeService.getAllChallenges()
                .stream()
                .filter(Challenge::isActive)
                .toList();
    }
}
