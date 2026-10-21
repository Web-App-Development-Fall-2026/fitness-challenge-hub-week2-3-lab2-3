package com.example.FitnessChallengeApp_Test.model;
import lombok.*;import jakarta.validation.constraints.*;import java.util.Date;
@Data @NoArgsConstructor @AllArgsConstructor public class WorkoutLog {
 private Long id;private Date createdAt=new Date();
 @NotBlank(message="Athlete name is required") @Size(min=2,max=40,message="Athlete name must be between 2 and 40 characters") private String athleteName;
 @NotNull(message="Please select an active challenge") private Long challengeId;
 private String challengeTitle;
 @NotNull(message="Workout duration is required") @Min(value=5,message="Workout duration must be at least 5 minutes") @Max(value=360,message="Workout duration cannot exceed 360 minutes") private Integer durationMinutes;
 @NotNull(message="Calories burned is required") @Min(value=10,message="Calories burned must be at least 10 kcal") @Max(value=4000,message="Calories burned cannot exceed 4,000 kcal") private Integer caloriesBurned;
 @NotBlank(message="Please select an intensity level") private String intensityLevel;
 @NotBlank(message="Workout date is required") private String workoutDate;
 @Size(max=250,message="Workout notes cannot exceed 250 characters") private String notes;

    public void setId(long incrementAndGet) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void setChallengeId(Object id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void setChallengeTitle(Object title) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Object getCreatedAt() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void setCreatedAt(Date date) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Object getId() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
