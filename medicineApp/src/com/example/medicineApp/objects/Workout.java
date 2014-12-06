package com.example.medicineApp.objects;

/**
 * Created by Sabina on 2014-12-06.
 */
public class Workout {

    private String id;
    private String exerciseId;
    private String repeats;
    private String quantity;
    private String workoutDate;
    private Exercise exercise = null;

    public Workout (String id, String exerciseId, String repeats, String quantity, String workoutDate) {
        this.id = id;
        this.exerciseId = exerciseId;
        this.repeats = repeats;
        this.quantity = quantity;
        this.workoutDate = workoutDate;
    }


    public String getId() {
        return id;
    }

    public String getExerciseId() {
        return exerciseId;
    }

    public String getRepeats() {
        return repeats;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getWorkoutDate() {
        return workoutDate;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }
}
