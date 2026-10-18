package com.example.FitnessChallengeApp_Test.controller;

import com.example.FitnessChallengeApp_Test.model.Challenge;
import com.example.FitnessChallengeApp_Test.service.ChallengeService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class WorkoutChallengeController {
    private final ChallengeService challengeService;

    public WorkoutChallengeController(ChallengeService challengeService) {
        this.challengeService = challengeService;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/challenges";
    }

    @GetMapping("/challenges")
    public String listChallenges(Model model) {
        model.addAttribute("challenges", challengeService.getAllChallenges());
        model.addAttribute("pageTitle", "Active Challenges");
        return "challenges";
    }

    @GetMapping("/create-challenge")
    public String showCreateChallenge(Model model) {
        model.addAttribute("challenge", new Challenge());
        model.addAttribute("pageTitle", "Create Challenge");
        model.addAttribute("categories", java.util.List.of("Cardio", "Strength", "Wellness", "Teamwork"));
        return "create-challenge";
    }

    @PostMapping("/create-challenge")
    public String createChallenge(@Valid @ModelAttribute("challenge") Challenge challenge,
                                  BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("pageTitle", "Create Challenge");
            model.addAttribute("categories", java.util.List.of("Cardio", "Strength", "Wellness", "Teamwork"));
            return "create-challenge";
        }
        challengeService.addChallenge(challenge);
        return "redirect:/challenges";
    }
}
