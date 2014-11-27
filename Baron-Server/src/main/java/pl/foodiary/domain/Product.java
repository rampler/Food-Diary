package pl.foodiary.domain;

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

	@Column(length = 255)
	private String name;

	@Column
	private Integer calories;

	@Column
	private Integer carbon;

	@Column
	private Integer protein;

	@Column
	private Integer fat;

	@Column
	@Enumerated(EnumType.STRING)
	private ProductCategory category;

	public Product() {
	}

	public Product(UUID id, String name, Integer calories, Integer carbon, Integer protein, Integer fat, ProductCategory category) {
		this.id = id;
		this.name = name;
		this.calories = calories;
		this.carbon = carbon;
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

	public Integer getCalories() {
		return calories;
	}

	public void setCalories(Integer calories) {
		this.calories = calories;
	}

	public Integer getCarbon() {
		return carbon;
	}

	public void setCarbon(Integer carbon) {
		this.carbon = carbon;
	}

	public Integer getProtein() {
		return protein;
	}

	public void setProtein(Integer protein) {
		this.protein = protein;
	}

	public Integer getFat() {
		return fat;
	}

	public void setFat(Integer fat) {
		this.fat = fat;
	}

	public ProductCategory getCategory() {
		return category;
	}

	public void setCategory(ProductCategory category) {
		this.category = category;
	}
}
