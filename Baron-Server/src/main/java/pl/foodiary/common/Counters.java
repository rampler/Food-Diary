package pl.foodiary.common;

/**
 * Created by Mateusz on 2014-12-03.
 */
public class Counters {
	private long products = 0;
	private long ingredients = 0;
	private long meals = 0;
	private long exercises = 0;
	private long workouts = 0;

	public Counters() {
	}

	public Counters(long products, long ingredients, long meals, long exercises, long workouts) {
		this.products = products;
		this.ingredients = ingredients;
		this.meals = meals;
		this.exercises = exercises;
		this.workouts = workouts;
	}

	public long getProducts() {
		return products;
	}

	public void setProducts(long products) {
		this.products = products;
	}

	public long getIngredients() {
		return ingredients;
	}

	public void setIngredients(long ingredients) {
		this.ingredients = ingredients;
	}

	public long getMeals() {
		return meals;
	}

	public void setMeals(long meals) {
		this.meals = meals;
	}

	public long getExercises() {
		return exercises;
	}

	public void setExercises(long exercises) {
		this.exercises = exercises;
	}

	public long getWorkouts() {
		return workouts;
	}

	public void setWorkouts(long workouts) {
		this.workouts = workouts;
	}
}
