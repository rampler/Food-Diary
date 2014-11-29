package pl.foodiary.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.ser.std.UUIDSerializer;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Mateusz on 2014-11-27.
 */
@Entity
@Table(name = "meal")
public class Meal {
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

	public Meal() {
	}

	public Meal(UUID id, String name, Date consumptionDay, User user) {
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

	@JsonProperty("user")
	public UUID getUserId() {
		return user.getId();
	}
}
