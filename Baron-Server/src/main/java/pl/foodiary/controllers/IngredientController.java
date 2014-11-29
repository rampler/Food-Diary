package pl.foodiary.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.foodiary.domain.Ingredient;
import pl.foodiary.repositories.IngredientRepository;
import pl.foodiary.repositories.MealRepository;
import pl.foodiary.repositories.ProductRepository;

import java.util.UUID;

/**
 * Created by Mateusz on 2014-11-29.
 */
@Controller
@RequestMapping("/ingredient")
public class IngredientController {

	@Autowired
	private IngredientRepository ingredientRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private MealRepository mealRepository;

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	@ResponseBody
	public UUID createIngredient(@RequestParam("product_id") UUID productId, @RequestParam("weight") Integer weight, @RequestParam("meal_id") UUID mealId) {
		try{
			Ingredient ingredient = new Ingredient(UUID.randomUUID(), productRepository.findOneById(productId), weight, mealRepository.findOneById(mealId));
			ingredientRepository.save(ingredient);
			return ingredient.getId();

		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public Iterable<Ingredient> listIngredients() {
		return ingredientRepository.findAll();
	}

	@RequestMapping(value = "/update", method = RequestMethod.GET)
	@ResponseBody
	public boolean updateIngredient(@RequestParam("id") UUID id, @RequestParam(value = "product_id", required = false) UUID productId, @RequestParam(value = "weight", required = false) Integer weight, @RequestParam(value = "meal_id", required = false) UUID mealId) {
		try{
			Ingredient ingredient = ingredientRepository.findOneById(id);
			if(productId != null) ingredient.setProduct(productRepository.findOneById(productId));
			if(weight != null) ingredient.setWeight(weight);
			if(mealId != null) ingredient.setMeal(mealRepository.findOneById(mealId));
			return true;
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	@ResponseBody
	public boolean deleteIngredient(@RequestParam("id") UUID id) {
		try{
			ingredientRepository.delete(ingredientRepository.findOneById(id));
			return true;
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
}
