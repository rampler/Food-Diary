package pl.foodiary.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.foodiary.domain.Ingredient;
import pl.foodiary.domain.Meal;
import pl.foodiary.domain.User;
import pl.foodiary.exceptions.NotAuthorizedException;
import pl.foodiary.repositories.IngredientRepository;
import pl.foodiary.repositories.MealRepository;
import pl.foodiary.repositories.ProductRepository;
import pl.foodiary.services.SessionService;

import javax.servlet.http.HttpServletRequest;
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

	@Autowired
	private SessionService sessionService;

	//API 2.0
	@RequestMapping(value = "/add", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json")
	@ResponseBody
	public String addIngredient(HttpServletRequest request, @RequestParam("sessionId") UUID sessionId, @RequestParam("productId") UUID productId, @RequestParam("weight") Double weight, @RequestParam("mealId") UUID mealId) {
		try {
			User user = sessionService.checkSession(sessionId, request.getRemoteAddr());
			Meal meal = mealRepository.findOne(mealId);

			if (user.getId().equals(meal.getUserId())) {
				Ingredient ingredient = new Ingredient(UUID.randomUUID(), productRepository.findOne(productId), weight, meal);
				ingredientRepository.save(ingredient);
				return "{\"id\":\"" + ingredient.getId() + "\"}";
			}
			else throw new NotAuthorizedException();
		}
		catch (NotAuthorizedException ex) { throw ex; }
		catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	@RequestMapping(value = "/change", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json")
	@ResponseBody
	public String changeIngredient(HttpServletRequest request, @RequestParam("sessionId") UUID sessionId, @RequestParam("id") UUID id, @RequestParam(value = "weight", required = false) Double weight) {
		try {
			User user = sessionService.checkSession(sessionId, request.getRemoteAddr());
			Ingredient ingredient = ingredientRepository.findOne(id);

			if (user.getId().equals(ingredient.getMeal().getUserId())) {
				if (weight != null) ingredient.setWeight(weight);
				ingredientRepository.save(ingredient);
				return "{\"result\":true}";
			}
			else throw new NotAuthorizedException();
		}
		catch (NotAuthorizedException ex) { throw ex; }
		catch (Exception e) {
			System.out.println(e.getMessage());
			return "{\"result\":false}";
		}
	}

	@RequestMapping(value = "/erase", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json")
	@ResponseBody
	public String eraseIngredient(HttpServletRequest request, @RequestParam("sessionId") UUID sessionId, @RequestParam("id") UUID id) {
		try {
			User user = sessionService.checkSession(sessionId, request.getRemoteAddr());
			Ingredient ingredient = ingredientRepository.findOne(id);

			if (user.getId().equals(ingredient.getMeal().getUserId())) {
				ingredientRepository.delete(ingredient);
				return "{\"result\":true}";
			}
			else throw new NotAuthorizedException();
		}
		catch (NotAuthorizedException ex) { throw ex; }
		catch (Exception e) {
			System.out.println(e.getMessage());
			return "{\"result\":false}";
		}
	}

	@RequestMapping(value = "/getList", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public Iterable<Ingredient> getListOfIngredients(HttpServletRequest request, @RequestParam("sessionId") UUID sessionId, @RequestParam("mealId") UUID mealId) {
		User user = sessionService.checkSession(sessionId, request.getRemoteAddr());
		Meal meal = mealRepository.findOne(mealId);
		if (user.getId().equals(meal.getUserId())) return ingredientRepository.findByMeal(meal);
		else throw new NotAuthorizedException();
	}

	//API 1.0
	@RequestMapping(value = "/create", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String createIngredient(@RequestParam("product_id") UUID productId, @RequestParam("weight") Double weight, @RequestParam("meal_id") UUID mealId) {
		try {
			Ingredient ingredient = new Ingredient(UUID.randomUUID(), productRepository.findOne(productId), weight, mealRepository.findOne(mealId));
			ingredientRepository.save(ingredient);
			return "{\"id\":\"" + ingredient.getId() + "\"}";

		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public Iterable<Ingredient> listIngredients() {
		return ingredientRepository.findAll();
	}

	@RequestMapping(value = "/update", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String updateIngredient(@RequestParam("id") UUID id, @RequestParam(value = "product_id", required = false) UUID productId, @RequestParam(value = "weight", required = false) Double weight, @RequestParam(value = "meal_id", required = false) UUID mealId) {
		try {
			Ingredient ingredient = ingredientRepository.findOne(id);
			if (productId != null) ingredient.setProduct(productRepository.findOne(productId));
			if (weight != null) ingredient.setWeight(weight);
			if (mealId != null) ingredient.setMeal(mealRepository.findOne(mealId));
			return "{\"result\":true}";
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			return "{\"result\":false}";
		}
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String deleteIngredient(@RequestParam("id") UUID id) {
		try {
			ingredientRepository.delete(ingredientRepository.findOne(id));
			return "{\"result\":true}";
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			return "{\"result\":false}";
		}
	}
}
