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

/**
 * Controller for FitSquad Hub challenges.
 * Handles challenge browsing, form rendering, and submission with Bean Validation.
 */
@Controller
public class WorkoutChallengeController {

    private final ChallengeService challengeService;

    public WorkoutChallengeController(ChallengeService challengeService) {
        this.challengeService = challengeService;
    }

    @GetMapping({"/", "/challenges"})
    public String showChallenges(Model model) {
        model.addAttribute("challenges", challengeService.getAllChallenges());
        model.addAttribute("pageTitle", "FitSquad Hub - Fitness Challenge Platform");
        return "challenges";
    }

    @GetMapping("/create-challenge")
    public String showCreateForm(Model model) {
        model.addAttribute("challenge", new Challenge());
        model.addAttribute("categories", challengeService.getCategories());
        model.addAttribute("difficulties", challengeService.getDifficulties());
        model.addAttribute("pageTitle", "FitSquad Hub - Create New Challenge");
        return "create-challenge";
    }

    @PostMapping("/create-challenge")
    public String processCreateChallenge(
            @Valid @ModelAttribute("challenge") Challenge challenge,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", challengeService.getCategories());
            model.addAttribute("difficulties", challengeService.getDifficulties());
            model.addAttribute("pageTitle", "FitSquad Hub - Create New Challenge");
            return "create-challenge";
        }

        challengeService.addChallenge(challenge);
        return "redirect:/challenges";
    }
}
