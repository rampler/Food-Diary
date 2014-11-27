package pl.foodiary.domain;

import org.hibernate.annotations.Type;

import javax.persistence.*;
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

	@OneToOne
	private Product product;

	@Column
	private int weight;

	@ManyToOne
	private MealData mealData;

	public Meal() {
	}

	public Meal(UUID id, Product product, int weight, MealData mealData) {
		this.id = id;
		this.product = product;
		this.weight = weight;
		this.mealData = mealData;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public MealData getMealData() {
		return mealData;
	}

	public void setMealData(MealData mealData) {
		this.mealData = mealData;
	}
}
