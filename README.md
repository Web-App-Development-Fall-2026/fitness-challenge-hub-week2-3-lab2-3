# Lab 2&3 - Form Processing, Model Binding & Jakarta Bean Validation

## Course Information
- **Course:** CPAN-228 Web Application Development
- **Topic:** Form Validation (Jakarta Bean Validation), Thymeleaf Two-Way Model Binding, In-Memory Service Architecture, Post-Redirect-Get (PRG), and Thymeleaf Fragments
- **Application Theme:** FitSquad Hub (Fitness Challenge Platform)

---

## Architecture & Classroom Context

In the class demonstration, we built the **Challenge Creation** feature using Spring Boot MVC and Thymeleaf:

1. **Domain Model & Validation:** [`Challenge.java`](file:///src/main/java/com/example/FitnessChallengeApp_Test/model/Challenge.java) with Jakarta Bean Validation constraints (`@NotBlank`, `@Size`, `@Min`, `@Max`).
2. **In-Memory Service Layer:** [`ChallengeService.java`](file:///src/main/java/com/example/FitnessChallengeApp_Test/service/ChallengeService.java) as a Spring `@Service` managing an in-memory collection of challenges with thread-safe access and atomic ID generation.
3. **Controller Layer:** [`WorkoutChallengeController.java`](file:///src/main/java/com/example/FitnessChallengeApp_Test/controller/WorkoutChallengeController.java) handling `GET /challenges`, `GET /create-challenge`, and `POST /create-challenge` with the **Post-Redirect-Get (PRG)** pattern and `BindingResult` validation inspection.
4. **Thymeleaf Form Binding:** [`create-challenge.html`](file:///src/main/resources/templates/create-challenge.html) with two-way form binding (`th:object`, `th:field`), dynamic select options (`th:each`), and inline feedback (`th:errors`, `th:classappend`).
5. **Thymeleaf Fragments:** Reusable navigation bar [`navbar.html`](file:///src/main/resources/templates/fragments/navbar.html) included across templates via `<div th:replace="~{fragments/navbar :: navbar}"></div>`.

---

## Your Assignment (Lab 2)

For this lab assignment, you will implement the **Athlete Workout Logging** subsystem from end-to-end. You will create the domain model with Jakarta Bean Validation, build the in-memory service and controller layers, and craft the Thymeleaf views.

Additionally, as **Extra Work (Bonus Challenge)**, you will extract and integrate a reusable **Footer Fragment** across all application views.

---

## Lab Architecture & File Structure

You will implement the following components:

```
src/main/java/com/example/FitnessChallengeApp_Test/
├── model/
│   └── WorkoutLog.java                   <-- Step 1: Model with Jakarta Bean Validation
├── service/
│   └── WorkoutLogService.java            <-- Step 2: In-Memory Service layer
├── controller/
│   └── WorkoutLogController.java         <-- Step 3: Controller with GET & POST endpoints
src/main/resources/templates/
├── fragments/
│   ├── navbar.html                       <-- Provided: Navigation header fragment
│   └── footer.html                       <-- Extra Work: Reusable footer fragment
├── log-workout.html                      <-- Step 4a: Form template with validation feedback
└── workout-logs.html                     <-- Step 4b: Dashboard listing logged workouts
```

---

## Getting Started

### GitHub Setup

1. **Fork the Repository**
   - Click the **"Fork"** button on GitHub to create your personal copy.

2. **Clone Your Fork**
   ```bash
   git clone https://github.com/YOUR-USERNAME/fitness-challenge-hub-week2-3-lab2-3.git
   cd fitness-challenge-hub-week2-3-lab2-3
   ```

3. **Add Upstream Remote**
   ```bash
   git remote add upstream https://github.com/Web-App-Development-Fall-2026/fitness-challenge-hub-week2-3-lab2-3.git
   ```

4. **Pull Latest Changes**
   ```bash
   git pull upstream main
   ```

5. **Create a Feature Branch**
   ```bash
   git checkout -b feature/lab2-3-yourname
   ```
   *(Replace `yourname` with your actual name, e.g., `feature/lab2-3-john-doe`)*

---

## Lab Requirements

### Step 1: Domain Model (`WorkoutLog.java`)

Create `WorkoutLog.java` inside `src/main/java/com/example/FitnessChallengeApp_Test/model/`:
- Annotate the class with `@Data`, `@NoArgsConstructor`, and `@AllArgsConstructor`.
- Define the following fields with their corresponding Jakarta Bean Validation annotations:

| Field Name | Type | Validation Constraints & Rules |
| :--- | :--- | :--- |
| `id` | `Long` | Unique identifier (assigned by service) |
| `createdAt` | `Date` | Timestamp defaulted to `new Date()` |
| `athleteName` | `String` | `@NotBlank(message = "Athlete name is required")`<br>`@Size(min = 2, max = 40, message = "Athlete name must be between 2 and 40 characters")` |
| `challengeId` | `Long` | `@NotNull(message = "Please select an active challenge")` |
| `challengeTitle` | `String` | Associated challenge title (populated upon saving) |
| `durationMinutes` | `Integer` | `@NotNull(message = "Workout duration is required")`<br>`@Min(value = 5, message = "Workout duration must be at least 5 minutes")`<br>`@Max(value = 360, message = "Workout duration cannot exceed 360 minutes")` |
| `caloriesBurned` | `Integer` | `@NotNull(message = "Calories burned is required")`<br>`@Min(value = 10, message = "Calories burned must be at least 10 kcal")`<br>`@Max(value = 4000, message = "Calories burned cannot exceed 4,000 kcal")` |
| `intensityLevel` | `String` | `@NotBlank(message = "Please select an intensity level")` |
| `workoutDate` | `String` | `@NotBlank(message = "Workout date is required")` |
| `notes` | `String` | `@Size(max = 250, message = "Workout notes cannot exceed 250 characters")` |

---

### Step 2: In-Memory Service Layer (`WorkoutLogService.java`)

Create `WorkoutLogService.java` inside `src/main/java/com/example/FitnessChallengeApp_Test/service/`:
- Annotate the class with `@Service`.
- Maintain a thread-safe in-memory collection (e.g., `CopyOnWriteArrayList<WorkoutLog>` or `List<WorkoutLog>`) and an `AtomicLong idGenerator = new AtomicLong(0);`.
- Implement the following methods:
  - `List<WorkoutLog> getAllLogs()`: Returns a list copy of all logged workouts (most recent first or insertion order).
  - `WorkoutLog getLogById(Long id)`: Retrieves a specific log by its ID, or returns `null` if not found.
  - `WorkoutLog addLog(WorkoutLog log, Challenge challenge)`:
    - Assign a unique ID using `idGenerator.incrementAndGet()`.
    - If `challenge != null`, associate the challenge info:
      ```java
      log.setChallengeId(challenge.getId());
      log.setChallengeTitle(challenge.getTitle());
      ```
    - Ensure `createdAt` is initialized if `null`.
    - Store the log in the in-memory list and return the saved object.
  - `List<String> getIntensityLevels()`: Returns `List.of("Low", "Moderate", "High", "Extreme")`.

---

### Step 3: Controller Layer (`WorkoutLogController.java`)

Create `WorkoutLogController.java` inside `src/main/java/com/example/FitnessChallengeApp_Test/controller/`:
- Annotate with `@Controller`.
- Inject `WorkoutLogService` and `ChallengeService` via constructor.
- Implement the following three endpoints:

#### 1. `GET /log-workout`
- Add an empty `WorkoutLog` object to the model under attribute name `"workoutLog"`.
- Add all active challenges from `challengeService.getAllChallenges()` under attribute name `"challenges"`.
- Add intensity levels from `workoutLogService.getIntensityLevels()` under attribute name `"intensityLevels"`.
- Add page title under `"pageTitle"`.
- Return view name `"log-workout"`.

#### 2. `POST /log-workout`
- Method signature:
  ```java
  @PostMapping("/log-workout")
  public String processLogWorkout(
          @Valid @ModelAttribute("workoutLog") WorkoutLog workoutLog,
          BindingResult bindingResult,
          Model model
  )
  ```
  *(Remember: `BindingResult` must immediately follow the `@Valid` model attribute!)*
- **On Validation Failure (`bindingResult.hasErrors()`):**
  - Re-populate `"challenges"` and `"intensityLevels"` on the `model`.
  - Return view name `"log-workout"` to display the form with inline error messages.
- **On Validation Success:**
  - Retrieve the selected `Challenge` using `challengeService.getChallengeById(workoutLog.getChallengeId())`.
  - Save the log using `workoutLogService.addLog(workoutLog, selectedChallenge)`.
  - Return `"redirect:/workout-logs"` (**Post-Redirect-Get** pattern).

#### 3. `GET /workout-logs`
- Retrieve all logs from `workoutLogService.getAllLogs()` and add to model under `"workoutLogs"`.
- Add page title under `"pageTitle"`.
- Return view name `"workout-logs"`.

---

### Step 4: Thymeleaf Templates

#### 4a. Workout Logging Form (`src/main/resources/templates/log-workout.html`)
- Include the navbar fragment:
  ```html
  <div th:replace="~{fragments/navbar :: navbar}"></div>
  ```
- Bind the form to `workoutLog`:
  ```html
  <form th:action="@{/log-workout}" th:object="${workoutLog}" method="post" novalidate>
  ```
- Two-way binding for all input fields using `th:field="*{fieldName}"`.
- Challenge selection dropdown:
  ```html
  <select id="challengeId" th:field="*{challengeId}" class="form-select" th:classappend="${#fields.hasErrors('challengeId')} ? 'is-invalid' : ''">
      <option value="">-- Choose an Active Challenge --</option>
      <option th:each="c : ${challenges}" th:value="${c.id}" th:text="${c.title} + ' (' + ${c.category} + ')'"></option>
  </select>
  <div class="invalid-feedback" th:if="${#fields.hasErrors('challengeId')}" th:errors="*{challengeId}"></div>
  ```
- Intensity level dropdown or radio buttons bound to `*{intensityLevel}` from `${intensityLevels}`.
- Inline validation error messages for every validated field:
  ```html
  <div class="invalid-feedback" th:if="${#fields.hasErrors('durationMinutes')}" th:errors="*{durationMinutes}"></div>
  ```
- Apply visual error borders using `th:classappend="${#fields.hasErrors('fieldName')} ? 'is-invalid' : ''"`.

#### 4b. Workout Logs Dashboard (`src/main/resources/templates/workout-logs.html`)
- Include the navbar fragment:
  ```html
  <div th:replace="~{fragments/navbar :: navbar}"></div>
  ```
- Render a table or card layout listing logged workouts with:
  - Athlete Name
  - Challenge Title
  - Workout Date
  - Duration (minutes)
  - Calories Burned
  - Intensity Level badge (color-coded)
  - Notes
- **Empty State Defense:**
  - Display an alert/empty state illustration when `#lists.isEmpty(workoutLogs)` is true:
    ```html
    <div th:if="${#lists.isEmpty(workoutLogs)}" class="alert alert-info text-center py-4">
        <i class="bi bi-info-circle fs-3 d-block mb-2"></i>
        <h5>No Workouts Logged Yet</h5>
        <p>Be the first squad member to complete a quest!</p>
        <a th:href="@{/log-workout}" class="btn btn-primary rounded-pill mt-2">Log First Workout</a>
    </div>
    ```
  - Display the table/cards when `th:unless="${#lists.isEmpty(workoutLogs)}"`.

---

## Thymeleaf Footer Fragment

To keep templates DRY (Don't Repeat Yourself) and practice template modularization, implement a reusable **Footer Fragment**:

1. **Create the Fragment File:**
   Create `src/main/resources/templates/fragments/footer.html`:
   ```html
   <!DOCTYPE html>
   <html lang="en" xmlns:th="http://www.thymeleaf.org">
   <body>
       <footer th:fragment="footer" class="container text-center mt-5 pt-4 border-top border-secondary border-opacity-25 text-secondary small">
           <div class="d-flex flex-column flex-md-row justify-content-between align-items-center gap-2">
               <div>
                   &copy; 2026 <span class="text-light fw-semibold">FitSquad Hub</span> &bull; Empowering Fitness Communities.
               </div>
               <div class="text-muted">
                   Spring Boot MVC &bull; CPAN-228 &bull; Thymeleaf Engine
               </div>
           </div>
       </footer>
   </body>
   </html>
   ```

2. **Replace Hard-Coded Footers in Existing Templates:**
   In both `src/main/resources/templates/challenges.html` and `src/main/resources/templates/create-challenge.html`, replace the hardcoded `<footer>` elements with:
   ```html
   <div th:replace="~{fragments/footer :: footer}"></div>
   ```

3. **Include the Footer in Your New Templates:**
   Add `<div th:replace="~{fragments/footer :: footer}"></div>` to the bottom of:
   - `src/main/resources/templates/log-workout.html`
   - `src/main/resources/templates/workout-logs.html`

---

## Running & Testing

### Run Application Locally
```bash
./mvnw spring-boot:run
```
*(On Windows: `.\mvnw.cmd spring-boot:run`)*

- **FitSquad Hub Challenges:** [http://localhost:8080/challenges](http://localhost:8080/challenges)
- **Create Challenge Form:** [http://localhost:8080/create-challenge](http://localhost:8080/create-challenge)
- **Log Workout Form:** [http://localhost:8080/log-workout](http://localhost:8080/log-workout)
- **Workout Logs Dashboard:** [http://localhost:8080/workout-logs](http://localhost:8080/workout-logs)

### Run Automated Unit Tests
```bash
./mvnw test
```
*(On Windows: `.\mvnw.cmd test`)*

---

## Submission Workflow

1. Check git status and add your changes:
   ```bash
   git status
   git add .
   ```
2. Commit with a descriptive message:
   ```bash
   git commit -m "Lab 2 & 3: Implement Workout Logging with Form Validation and Footer Fragment"
   ```
3. Push to your GitHub fork:
   ```bash
   git push origin feature/lab2-3-yourname
   ```
4. Open a Pull Request against the main repository.

## Resources & Cheat Sheets
- **Validation Cheat Sheet:** Quick reference for Jakarta Bean Validation constraints, Controller error handling, and Thymeleaf error rendering (see [`VALIDATION_CHEAT_SHEET.md`](VALIDATION_CHEAT_SHEET.md))
- **Thymeleaf Cheat Sheet:** Comprehensive syntax reference for loops, conditionals, expressions, and fragments (see [`THYMELEAF_CHEAT_SHEET.md`](THYMELEAF_CHEAT_SHEET.md))
- [Spring Boot Reference Documentation](https://docs.spring.io/spring-boot/reference/)
- [Thymeleaf Official Tutorial](https://www.thymeleaf.org/doc/tutorials/3.1/usingthymeleaf.html)

---

## Validation & Thymeleaf Reference Quick Table

| Concept / Annotation | Description |
| :--- | :--- |
| `@Valid` | Triggers Jakarta Bean Validation on controller model attributes |
| `BindingResult` | Holds validation results; must immediately follow the `@Valid` argument |
| `@NotBlank` | String must not be null and must contain at least one non-whitespace character |
| `@Size(min, max)` | Constrains string length or collection size |
| `@NotNull` | Value must not be null (used for numbers, dates, objects) |
| `@Min` / `@Max` | Constrains numeric values to minimum / maximum thresholds |
| `"redirect:/..."` | Post-Redirect-Get (PRG) pattern preventing duplicate submissions on page refresh |
| `th:object="${...}"` | Binds a `<form>` element to a backing model attribute |
| `th:field="*{...}"` | Binds an input/select/textarea to a property of `th:object` |
| `th:errors="*{...}"` | Renders validation error text associated with a specific field |
| `${#fields.hasErrors('...')}` | Evaluates to `true` if the specified field has any validation errors |
| `th:fragment="name"` | Declares a reusable template fragment |
| `th:replace="~{path :: name}"` | Replaces the host tag with the specified fragment |
| `th:insert="~{path :: name}"` | Inserts the specified fragment inside the host tag |
