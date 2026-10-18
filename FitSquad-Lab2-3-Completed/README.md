# FitSquad Hub — CPAN-228 Lab 2 & 3

This is a complete, standalone Spring Boot project implementing the lab requirements:
- WorkoutLog model with Jakarta Bean Validation
- In-memory WorkoutLogService with thread-safe storage and generated IDs
- WorkoutLogController GET/POST routes and Post-Redirect-Get
- Thymeleaf form with inline validation feedback
- Workout logs dashboard with an empty state and intensity badges
- Shared footer fragment used on all application pages
- Existing challenge creation/listing flow so the workout form has active challenges

## Requirements
- JDK 17 or newer
- Maven 3.9+ installed
- Internet access on first build so Maven can download dependencies

## Run on Windows
Open this folder in VS Code, then open Terminal > New Terminal and run:

```powershell
mvn test
mvn spring-boot:run
```

If your computer uses the Maven Wrapper from the instructor's starter repository, you can instead run:
```powershell
.\mvnw.cmd test
.\mvnw.cmd spring-boot:run
```

## Pages
- http://localhost:8080/challenges
- http://localhost:8080/create-challenge
- http://localhost:8080/log-workout
- http://localhost:8080/workout-logs

## Important submission note
Your instructor's instructions ask you to fork the provided GitHub repository, create a feature branch, push the changes, and open a pull request. This ZIP is a complete standalone project based on the lab requirements, not a byte-for-byte copy of the instructor's repository (the starter repository could not be downloaded in this environment). If you already have the instructor's fork cloned, the safest route is to copy the new/updated Java files and templates into that fork, preserving its `.mvn`, `mvnw`, `mvnw.cmd`, existing static assets, README, and cheat sheets.

## Git submission commands
Run these commands from your existing clone of your own fork (not from a fresh unrelated repository):
```powershell
git status
git checkout -b feature/lab2-3-harpreet-kaur
git add .
git commit -m "Lab 2 and 3: Implement workout logging and footer fragment"
git push -u origin feature/lab2-3-harpreet-kaur
```
Then open your fork on GitHub and create a pull request against the instructor's original repository.
