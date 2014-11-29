package pl.foodiary.domain;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

/**
 * Created by Mateusz on 2014-11-27.
 */
@Entity
@Table(name = "ingredient")
public class Ingredient {
	@Id
	@Type(type = "pg-uuid")
	private UUID id;

	@OneToOne
	private Product product;

	@Column
	private int weight;

	@ManyToOne
	private Meal meal;

	public Ingredient() {
	}

	public Ingredient(UUID id, Product product, int weight, Meal meal) {
		this.id = id;
		this.product = product;
		this.weight = weight;
		this.meal = meal;
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

	public Meal getMeal() {
		return meal;
	}

	public void setMeal(Meal meal) {
		this.meal = meal;
	}
}
