package com.example.FitnessChallengeApp_Test.controller;

import com.example.FitnessChallengeApp_Test.model.Challenge;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for FitSquad Hub challenges view.
 * Handles root ("/") and "/challenges" endpoints.
 */
@Controller
public class WorkoutChallengeController {

    @GetMapping({"/", "/challenges"})
    public String showChallenges(Model model) {
        // Instantiate challenges list
        List<Challenge> challenges = new ArrayList<>();

        // Populate with 6 hardcoded fitness challenges using constructor
        challenges.add(new Challenge(
                1L,
                "30-Day Core Crusher",
                "Strength & Core",
                "Sculpt and fortify core stability with progressively escalating daily plank variations, hollow holds, and dynamic abdominal circuits.",
                "15 mins daily plank & core circuit",
                30,
                450,
                "Intermediate",
                "https://images.unsplash.com/photo-1571019613454-1cb2f99b2d8b?auto=format&fit=crop&w=800&q=80",
                true
        ));

        challenges.add(new Challenge(
                2L,
                "HIIT Inferno Blast",
                "Cardio & HIIT",
                "Torch calories, build cardiovascular endurance, and spike metabolic rate with high-intensity Tabata and explosive plyometric intervals.",
                "25 mins explosive interval training",
                21,
                500,
                "Advanced",
                "https://images.unsplash.com/photo-1601422407692-ec4eeec1d9b3?auto=format&fit=crop&w=800&q=80",
                true
        ));

        challenges.add(new Challenge(
                3L,
                "Iron Titan Powerlifting",
                "Hypertrophy & Power",
                "Focus on fundamental compound movements—barbell squat, bench press, deadlift, and overhead press—to construct raw functional power.",
                "4 heavy compound lifting sessions/wk",
                45,
                750,
                "Advanced",
                "https://images.unsplash.com/photo-1517838277536-f5f99be501cd?auto=format&fit=crop&w=800&q=80",
                true
        ));

        challenges.add(new Challenge(
                4L,
                "Zen Flow Mobility & Yoga",
                "Flexibility & Recovery",
                "Decompress tight joints, increase deep hip and spine mobility, and master breathwork through restorative vinyasa recovery sessions.",
                "20 mins mindful stretching & flow",
                14,
                250,
                "Beginner",
                "https://images.unsplash.com/photo-1544367567-0f2fcb009e0b?auto=format&fit=crop&w=800&q=80",
                false
        ));

        challenges.add(new Challenge(
                5L,
                "Sunrise 5K Sprint Series",
                "Cardio Endurance",
                "Build weekly running volume through structured pace drills, tempo intervals, and weekend outdoor endurance runs.",
                "Accumulate 25 km running per week",
                28,
                400,
                "Intermediate",
                "https://images.unsplash.com/photo-1486218119243-13883505764c?auto=format&fit=crop&w=800&q=80",
                true
        ));

        challenges.add(new Challenge(
                6L,
                "Hydration & 10K Habit Builder",
                "Habit & Wellness",
                "Dial in foundational wellness habits by consistently hitting 10,000 steps every day alongside optimal hydration discipline.",
                "10,000 steps + 3L water daily",
                30,
                300,
                "Beginner",
                "https://images.unsplash.com/photo-1552674605-db6ffd4facb5?auto=format&fit=crop&w=800&q=80",
                false
        ));

        // Pass attributes to Spring Model
        model.addAttribute("challenges", challenges);
        model.addAttribute("pageTitle", "FitSquad Hub - Fitness Challenge Platform");

        // Return Thymeleaf template view
        return "challenges";
    }
}
