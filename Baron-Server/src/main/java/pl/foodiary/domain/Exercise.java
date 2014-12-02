package pl.foodiary.domain;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

/**
 * Created by Mateusz on 2014-12-02.
 */
@Entity
@Table(name = "exercise")
public class Exercise {
	@Id
	@Type(type = "pg-uuid")
	private UUID id;

	@Column(unique = true)
	private String name;

	@Column
	private String unit;

	public Exercise() {
	}

	public Exercise(UUID id, String name, String unit) {
		this.id = id;
		this.name = name;
		this.unit = unit;
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

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
}
