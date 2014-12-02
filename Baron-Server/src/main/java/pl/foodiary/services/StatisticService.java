package pl.foodiary.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.foodiary.common.ConsumptionReview;
import pl.foodiary.domain.Ingredient;
import pl.foodiary.domain.Meal;
import pl.foodiary.domain.Product;
import pl.foodiary.repositories.IngredientRepository;
import pl.foodiary.repositories.ProductRepository;

/**
 * Created by Mateusz on 2014-12-02.
 */
@Service
public class StatisticService {

	@Autowired
	private IngredientRepository ingredientRepository;

	@Autowired
	private ProductRepository productRepository;

	public ConsumptionReview calculateConsumptionReview(Iterable<Meal> meals) {
		ConsumptionReview consumptionReview = new ConsumptionReview();
		for (Meal meal : meals)
			consumptionReview.addConsumtionReview(calculateConsumptionReview(meal));
		return consumptionReview;
	}

	public ConsumptionReview calculateConsumptionReview(Meal meal) {
		Double calories = 0.0;
		Double carbs = 0.0;
		Double protein = 0.0;
		Double fat = 0.0;

		for (Ingredient ingredient : ingredientRepository.findByMeal(meal)) {
			Product product = productRepository.findOne(ingredient.getProductId());
			calories += (product.getCalories() / 100) * ingredient.getWeight();
			carbs += (product.getCarbs() / 100) * ingredient.getWeight();
			protein += (product.getProtein() / 100) * ingredient.getWeight();
			fat += (product.getFat() / 100) * ingredient.getWeight();
		}
		return new ConsumptionReview(calories, carbs, protein, fat);
	}
}
