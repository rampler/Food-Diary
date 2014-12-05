package pl.foodiary.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Mateusz on 2014-12-02.
 */
@Entity
@Table(name = "workout")
@JsonIgnoreProperties({"user"})
public class Workout {
	@Id
	@Type(type = "pg-uuid")
	private UUID id;

	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	private User user;

	@ManyToOne
	private Exercise exercise;

	@Column
	private Integer repeats;

	@Column
	private Double quantity;

	@Column
	@Type(type = "date")
	private Date workoutDate;

	@Column
	private Boolean done;

	public Workout() {
	}

	public Workout(UUID id, User user, Exercise exercise, Integer repeats, Double quantity, Date workoutDate, Boolean done) {
		this.id = id;
		this.user = user;
		this.exercise = exercise;
		this.repeats = repeats;
		this.quantity = quantity;
		this.workoutDate = workoutDate;
		this.done = done;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Exercise getExercise() {
		return exercise;
	}

	public void setExercise(Exercise exercise) {
		this.exercise = exercise;
	}

	public Integer getRepeats() {
		return repeats;
	}

	public void setRepeats(Integer repeats) {
		this.repeats = repeats;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Date getWorkoutDate() {
		return workoutDate;
	}

	public void setWorkoutDate(Date workoutDate) {
		this.workoutDate = workoutDate;
	}

	public Boolean getDone() {
		return done;
	}

	public void setDone(Boolean done) {
		this.done = done;
	}

	@JsonProperty("exercise")
	public UUID getExerciseId() { return exercise.getId(); }

	@JsonProperty("user")
	public UUID getUserId() {
		return user.getId();
	}
}
