package pl.foodiary.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

/**
 * Created by Mateusz on 2014-11-30.
 */
@Entity
@Table(name = "profile", uniqueConstraints = @UniqueConstraint(columnNames = "user_id"))
public class Profile {
	@Id
	@Type(type = "pg-uuid")
	private UUID id;

	@Column
	private String firstName;

	@Column
	private String lastName;

	@Column
	private Double weight;

	@Column
	private Double caloriesCounter;

	@Column
	private Integer age;

	@OneToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	private User user;

	public Profile() {
	}

	public Profile(UUID id, String firstName, String lastName, Double weight, Double caloriesCounter, Integer age, User user) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.weight = weight;
		this.caloriesCounter = caloriesCounter;
		this.age = age;
		this.user = user;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Double getCaloriesCounter() {
		return caloriesCounter;
	}

	public void setCaloriesCounter(Double caloriesCounter) {
		this.caloriesCounter = caloriesCounter;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@JsonProperty("user")
	public UUID getUserId() {
		return user.getId();
	}
}
