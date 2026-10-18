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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class WorkoutLogController {

    private final WorkoutLogService workoutLogService;
    private final ChallengeService challengeService;

    // Constructor Injection
    public WorkoutLogController(
            WorkoutLogService workoutLogService,
            ChallengeService challengeService) {

        this.workoutLogService = workoutLogService;
        this.challengeService = challengeService;
    }

    // GET: Display Workout Logging Form
    @GetMapping("/log-workout")
    public String showLogWorkoutForm(Model model) {

        model.addAttribute("workoutLog", new WorkoutLog());

        model.addAttribute(
                "challenges",
                challengeService.getAllChallenges()
        );

        model.addAttribute(
                "intensityLevels",
                workoutLogService.getIntensityLevels()
        );

        model.addAttribute(
                "pageTitle",
                "Log Workout"
        );

        return "log-workout";
    }

    // POST: Process Workout Logging Form
    @PostMapping("/log-workout")
    public String processLogWorkout(
            @Valid @ModelAttribute("workoutLog") WorkoutLog workoutLog,
            BindingResult bindingResult,
            Model model) {

        // Check Validation Errors
        if (bindingResult.hasErrors()) {

            model.addAttribute(
                    "challenges",
                    challengeService.getAllChallenges()
            );

            model.addAttribute(
                    "intensityLevels",
                    workoutLogService.getIntensityLevels()
            );

            model.addAttribute(
                    "pageTitle",
                    "Log Workout"
            );

            return "log-workout";
        }

        // Get Selected Challenge
        Challenge selectedChallenge =
                challengeService.getChallengeById(
                        workoutLog.getChallengeId()
                );

        // Reject invalid challenge IDs
        if (selectedChallenge == null) {

            bindingResult.rejectValue(
                    "challengeId",
                    "invalid.challenge",
                    "Please select a valid active challenge"
            );

            model.addAttribute(
                    "challenges",
                    challengeService.getAllChallenges()
            );

            model.addAttribute(
                    "intensityLevels",
                    workoutLogService.getIntensityLevels()
            );

            model.addAttribute(
                    "pageTitle",
                    "Log Workout"
            );

            return "log-workout";
        }

        // Save Workout
        workoutLogService.addLog(
                workoutLog,
                selectedChallenge
        );

        // Redirect to Workout Dashboard
        return "redirect:/workout-logs";
    }

    // GET: Display All Workout Logs
    @GetMapping("/workout-logs")
    public String showWorkoutLogs(Model model) {

        model.addAttribute(
                "workoutLogs",
                workoutLogService.getAllLogs()
        );

        model.addAttribute(
                "pageTitle",
                "Workout Logs"
        );

        return "workout-logs";
    }
}