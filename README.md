# Lab 1 - Thymeleaf Demo

## Course Information
- **Course:** CPAN 228
- **Topic:** Implementing Controllers & Thymeleaf Templates

---

## Getting Started
This is a starter template for your lab assignments. Follow the GitHub setup below before beginning your work.

### GitHub Setup

1. **Fork the Repository**
   - Go to the repository on GitHub: [https://github.com/Web-App-Development-Fall-2026/fitness-challenge-hub-week1-2-lab1](https://github.com/Web-App-Development-Fall-2026/fitness-challenge-hub-week1-2-lab1)
   - Click the **"Fork"** button in the top-right corner
   - This creates your own copy of the project

2. **Clone Your Fork**
   ```bash
   git clone https://github.com/YOUR-USERNAME/fitness-challenge-hub-week1-2-lab1.git
   cd fitness-challenge-hub-week1-2-lab1
   ```

3. **Add Upstream Remote**
   ```bash
   git remote add upstream https://github.com/Web-App-Development-Fall-2026/fitness-challenge-hub-week1-2-lab1.git
   ```

4. **Pull Latest Changes**
   ```bash
   git pull upstream main
   ```

5. **Create a Feature Branch**
   ```bash
   git checkout -b feature/lab1-yourname
   ```
   *(Replace `yourname` with your actual name, e.g., `feature/lab1-john-doe`)*

---

## Lab 1 Assignment
### About Controller Implementation
Implement a new `AboutController` with the following requirements:

#### Endpoint Requirements
- **Path:** `localhost:8080/about`
- **HTTP Method:** `GET`
- **Return:** HTML template displaying content about FitSquad Hub and the community workout challenges.

#### Template Requirements
- **Heading:** Display an `<h1>` tag using `th:text` to pass a dynamic title like "Welcome to the FitSquad Hub!" from your controller.
- **Description:** Add a description of FitSquad Hub and what makes it special (e.g., community fitness quests, tracking duration & XP reward points, difficulty tiers from Beginner to Advanced).
- **Thymeleaf Usage:** Use at least one Thymeleaf expression (like `th:text` or `th:utext`) to display dynamic data passed from the controller.
- **Styling:** Be creative! Use any HTML elements you like. Make it visually appealing using the existing `style.css` in `/css/style.css`.
- **Static Assets (CSS & Images):** If you want to add your own CSS files or images, make sure to place them in the `src/main/resources/static/` folder. Spring Boot automatically serves files from this location. You can reference them in your templates using Thymeleaf's URL syntax, e.g., `<link rel="stylesheet" th:href="@{/css/your-style.css}">` or `<img th:src="@{/your-image.png}">`.
- Include at least 2-3 sentences of meaningful content.

#### Example (Not required to use this content)
```html
<h1 th:text="${pageTitle}">FitSquad Hub</h1>
<p>FitSquad Hub is a community fitness challenge platform designed to help squad members track workouts, earn XP, and stay accountable across monthly quests...</p>
<!-- Add more creative content here if you want :)-->
```

---

## Development Workflow
1. Create your feature branch with your name
2. Make changes for the lab assignment
3. Test locally:
   ```bash
   ./mvnw spring-boot:run
   ```
   *(or `.\mvnw.cmd spring-boot:run` on Windows)*
4. Commit your changes:
   ```bash
   git add .
   git commit -m "Lab 1: Implement About Controller"
   ```
5. Push to your fork:
   ```bash
   git push origin feature/lab1-yourname
   ```
6. Create a pull request

---

## Resources
- **Thymeleaf Cheat Sheet:** Common Thymeleaf syntax and patterns (see [`THYMELEAF_CHEAT_SHEET.md`](THYMELEAF_CHEAT_SHEET.md))
- [Spring Boot Documentation](https://docs.spring.io/spring-boot/reference/)
- [Thymeleaf Official Docs](https://www.thymeleaf.org/doc/tutorials/3.1/usingthymeleaf.html)

### Thymeleaf Reference Quick Table

| Attribute | Purpose |
| :--- | :--- |
| `th:text` | Display variable (escaped HTML) |
| `th:utext` | Display variable (raw HTML) |
| `th:href` | Generate URLs |
| `th:src` | Bind image/resource URLs |
| `th:value` | Bind form input values |
| `th:each` | Loop through collections |
| `th:if` | Conditional rendering (true) |
| `th:unless` | Conditional rendering (false) |
| `th:switch` / `th:case` | Multiple conditions |
| `th:object` | Bind form to model object |
| `th:field` | Bind form input to model field |
| `th:errors` | Display field validation errors |
| `th:class` | Conditional CSS classes |
| `th:style` | Conditional inline styles |
| `th:with` | Define local variables |
| `th:insert` | Include fragment as child |
| `th:replace` | Replace element with fragment |

**Expressions:**
- `@{}` : URL expression
- `${}` : Variable expression
- `*{}` : Object variable expression
- `#{}` : Utility object expression
