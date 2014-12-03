package pl.foodiary.common;

/**
 * Created by Mateusz on 2014-12-02.
 */
public class ConsumptionReview {

	private Double calories = 0.0;
	private Double carbs    = 0.0;
	private Double protein  = 0.0;
	private Double fat      = 0.0;

	public ConsumptionReview() {}

	public ConsumptionReview(Double calories, Double carbs, Double protein, Double fat) {
		this.calories = calories;
		this.carbs = carbs;
		this.protein = protein;
		this.fat = fat;
	}

	public Double getCalories() {
		return calories;
	}

	public Double getCarbs() {
		return carbs;
	}

	public Double getProtein() {
		return protein;
	}

	public Double getFat() {
		return fat;
	}

	public void addConsumtionReview(ConsumptionReview consumptionReview2) {
		this.calories += consumptionReview2.calories;
		this.carbs += consumptionReview2.carbs;
		this.protein += consumptionReview2.protein;
		this.fat += consumptionReview2.fat;
	}
}
