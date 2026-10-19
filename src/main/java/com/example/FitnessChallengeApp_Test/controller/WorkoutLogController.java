package com.example.FitnessChallengeApp_Test.controller;

import com.example.FitnessChallengeApp_Test.model.WorkoutLog;
import com.example.FitnessChallengeApp_Test.service.WorkoutLogService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class WorkoutLogController {

    private final WorkoutLogService workoutLogService;

    public WorkoutLogController(WorkoutLogService workoutLogService) {
        this.workoutLogService = workoutLogService;
    }

    @GetMapping("/log-workout")
    public String showWorkoutForm(Model model) {
        model.addAttribute("workoutLog", new WorkoutLog());
        return "log-workout";
    }

    @PostMapping("/log-workout")
    public String saveWorkout(
            @Valid @ModelAttribute("workoutLog") WorkoutLog workoutLog,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "log-workout";
        }

        workoutLogService.addWorkoutLog(workoutLog);
        return "redirect:/workout-logs";
    }

    @GetMapping("/workout-logs")
    public String showWorkoutLogs(Model model) {
        model.addAttribute(
                "workoutLogs",
                workoutLogService.getAllWorkoutLogs()
        );

        return "workout-logs";
    }
}