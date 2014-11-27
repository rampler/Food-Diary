package pl.foodiary.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.foodiary.domain.*;
import pl.foodiary.repositories.MealDataRepository;
import pl.foodiary.repositories.MealRepository;
import pl.foodiary.repositories.ProductRepository;
import pl.foodiary.repositories.UserRepository;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
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
	private MealRepository mealRepository;

	@Autowired
	private MealDataRepository mealDataRepository;

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
	Meal testMeal() {
		User user = new User(UUID.randomUUID(), "Testowy", "hahaha");
		userRepository.save(user);

		MealData mealData = new MealData(UUID.randomUUID(), "Wołowina z mlekiem", new Date(), user);
		mealDataRepository.save(mealData);

		Product product = new Product(UUID.randomUUID(), "Wołowina", 100, 101, 102, 103, ProductCategory.MEAT);
		productRepository.save(product);

		Meal meal = new Meal(UUID.randomUUID(), product, 300, mealData);
		mealRepository.save(meal);

		return meal;
	}
}
