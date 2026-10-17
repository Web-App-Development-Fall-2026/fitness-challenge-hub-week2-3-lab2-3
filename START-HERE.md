# Lab 2 & 3 — Great Opiti

Implemented the WorkoutLog model, in-memory service, three controller endpoints,
workout form with inline validation, logs dashboard with empty state and intensity
badges, and the bonus reusable footer on all four pages. Only active challenges
can receive workouts. Data is held in memory and resets when the app restarts.

## Run using VS Code on Windows
1. Extract this ZIP and open the extracted project folder in VS Code.
2. Install Java 17 or newer and the VS Code Extension Pack for Java if needed.
3. Open Terminal > New Terminal. Ensure the terminal folder contains pom.xml.
4. In PowerShell run: `.\mvnw.cmd spring-boot:run`
5. Open http://localhost:8080/log-workout in your browser.
6. View saved workouts at http://localhost:8080/workout-logs.
7. Stop the application with Ctrl+C.

## Test
Run `.\mvnw.cmd test` in PowerShell.
Automated tests cover validation boundaries, saving, challenge association,
ID generation, invalid challenge rejection, and validation failure handling.
Build verification here was blocked because Maven Central could not be reached
(DNS resolution failed). No successful automated test run is claimed.

## Manual checks
1. Open Workout Logs before submitting anything: the empty message should appear.
2. Submit the empty form: required field errors should appear.
3. Enter duration 4 and calories 4001: range errors should appear.
4. Enter Great Opiti, an active challenge, 30 minutes, 200 calories, Moderate,
   a workout date, and optional notes. Save: the dashboard should show the entry.
5. Refresh the dashboard: it should still show only one entry.
6. Check Challenges, Create Challenge, Log Workout, and Workout Logs for the footer.

## GitHub submission
Work is submitted on branch feature/lab2-3-great-opiti in the BigGreat1 fork.
Open the pull request against the instructor repository to review the changes.
