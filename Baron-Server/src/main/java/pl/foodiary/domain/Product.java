package pl.foodiary.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

/**
 * Created by Mateusz on 2014-11-27.
 */
@Entity
@Table(name = "product")
public class Product {

	@Id
	@Type(type = "pg-uuid")
	private UUID id;

	@Column(length = 255, unique = true)
	private String name;

	@Column
	private Double calories;

	@Column
	private Double carbs;

	@Column
	private Double protein;

	@Column
	private Double fat;

	@Column
	@Enumerated(EnumType.STRING)
	private ProductCategory category;

	public Product() {
	}

	public Product(UUID id, String name, Double calories, Double carbs, Double protein, Double fat, ProductCategory category) {
		this.id = id;
		this.name = name;
		this.calories = calories;
		this.carbs = carbs;
		this.protein = protein;
		this.fat = fat;
		this.category = category;
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

	public Double getCalories() {
		return calories;
	}

	public void setCalories(Double calories) {
		this.calories = calories;
	}

	public Double getCarbs() {
		return carbs;
	}

	public void setCarbs(Double carbs) {
		this.carbs = carbs;
	}

	public Double getProtein() {
		return protein;
	}

	public void setProtein(Double protein) {
		this.protein = protein;
	}

	public Double getFat() {
		return fat;
	}

	public void setFat(Double fat) {
		this.fat = fat;
	}

	public ProductCategory getCategory() {
		return category;
	}

	public void setCategory(ProductCategory category) {
		this.category = category;
	}

	@JsonProperty("category")
	public String getCategoryName() {
		return category.getName();
	}
}
