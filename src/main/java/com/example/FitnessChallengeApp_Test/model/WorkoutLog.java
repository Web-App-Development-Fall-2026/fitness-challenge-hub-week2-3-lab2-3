import java.util.Date;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import org.springframework.boot.Data;
import org.springframework.boot.NoArgsConstructor;
import org.springframework.boot.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class WorkoutLog {
    private Long id;
    private Date createdAt = new Date();
    @NotBlank(message = "Name Required")
    
    @Size(min = 2, max = 40, message = "Name must be between 2 and 40 characters")
    private String athleteName;
    
    @NotNull(message = "Select an active challenge")
    private Long challengeId;
    private String challengeTitle;
    
    @NotNull(message = "Workout duration is required")
    @Min(value = 5, message = "Minimum duration must be 5 minutes")
    @Max(value = 360, message = "Maximum duration must be 6 hours")
    private Integer durationMinutes;

    @NotNull(message = "Calories burned is required")
    @Min(value = 10, message = "Calories burned must be at least 10 kcal")
    @Max(value = 4000, message = "Calories burned cannot exceed 4,000 kcal")
    private Integer caloriesBurned;

    @NotBlank(message = "Select intensity level")
    private String intensityLevel;

    @NotBlank(message = "Workout date required")
    private String workoutDate;

    @Size(max = 250, message = "Workout notes cannot exceed 250 characters")
    private String notes;
}