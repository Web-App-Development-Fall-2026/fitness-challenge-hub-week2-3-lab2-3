package com.example.FitnessChallengeApp_Test.controller;
import com.example.FitnessChallengeApp_Test.model.Challenge;import com.example.FitnessChallengeApp_Test.service.ChallengeService;import org.springframework.stereotype.Controller;import org.springframework.ui.Model;import org.springframework.web.bind.annotation.*;
@Controller public class WorkoutChallengeController {private final ChallengeService service;public WorkoutChallengeController(ChallengeService service){this.service=service;}
 @GetMapping("/") public String home(){return "redirect:/challenges";}
 @GetMapping("/challenges") public String challenges(Model m){m.addAttribute("challenges",service.getAllChallenges());m.addAttribute("pageTitle","Challenges");return "challenges";}
 @GetMapping("/create-challenge") public String form(Model m){m.addAttribute("challenge",new Challenge());m.addAttribute("pageTitle","Create Challenge");return "create-challenge";}
 @PostMapping("/create-challenge") public String create(@ModelAttribute Challenge challenge,Model m){if(challenge.getTitle()==null||challenge.getTitle().isBlank()){m.addAttribute("error","Challenge title is required");return "create-challenge";}challenge.setActive(true);service.addChallenge(challenge);return "redirect:/challenges";}}
