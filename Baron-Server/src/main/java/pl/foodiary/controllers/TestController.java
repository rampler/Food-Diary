package pl.foodiary.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.foodiary.domain.*;
import pl.foodiary.repositories.IngredientRepository;
import pl.foodiary.repositories.MealRepository;
import pl.foodiary.repositories.ProductRepository;
import pl.foodiary.repositories.UserRepository;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Mateusz on 2014-11-27.
 */
@Controller
@RequestMapping("/test")
public class TestController {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private IngredientRepository ingredientRepository;

	@Autowired
	private MealRepository mealRepository;

	@Autowired
	private UserRepository userRepository;

	@RequestMapping("/product")
	@ResponseBody
	Product testProduct() {
		Product product = new Product(UUID.randomUUID(), "Kurczak", 100, 101, 102, 103, ProductCategory.MEAT);
		productRepository.save(product);
		return product;
	}

	@RequestMapping("/meal")
	@ResponseBody
	Ingredient testMeal() {
		User user = new User(UUID.randomUUID(), "Testowy", "hahaha");
		userRepository.save(user);

		Meal mealData = new Meal(UUID.randomUUID(), "Wołowina z mlekiem", new Date(), user);
		mealRepository.save(mealData);

		Product product = new Product(UUID.randomUUID(), "Wołowina", 100, 101, 102, 103, ProductCategory.MEAT);
		productRepository.save(product);

		Ingredient meal = new Ingredient(UUID.randomUUID(), product, 300, mealData);
		ingredientRepository.save(meal);

		return meal;
	}
}
