package pl.foodiary.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
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

	@ManyToOne
	private Product product;

	@Column
	private Integer weight;

	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Meal meal;

	public Ingredient() {
	}

	public Ingredient(UUID id, Product product, Integer weight, Meal meal) {
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

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public Meal getMeal() {
		return meal;
	}

	public void setMeal(Meal meal) {
		this.meal = meal;
	}

	@JsonProperty("meal")
	public UUID getMealId() { return meal.getId(); }

	@JsonProperty("product")
	public UUID getProductId() { return product.getId(); }
}
