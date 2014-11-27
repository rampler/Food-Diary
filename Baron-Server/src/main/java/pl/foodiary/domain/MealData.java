package pl.foodiary.domain;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Mateusz on 2014-11-27.
 */
@Entity
@Table(name = "meal_data")
public class MealData {
	@Id
	@Type(type = "pg-uuid")
	private UUID id;

	@Column
	private String name;

	@Column
	@Type(type = "date")
	private Date consumptionDay;

	@ManyToOne
	private User user;

	public MealData() {
	}

	public MealData(UUID id, String name, Date consumptionDay, User user) {
		this.id = id;
		this.name = name;
		this.consumptionDay = consumptionDay;
		this.user = user;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getConsumptionDay() {
		return consumptionDay;
	}

	public void setConsumptionDay(Date consumptionDay) {
		this.consumptionDay = consumptionDay;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
