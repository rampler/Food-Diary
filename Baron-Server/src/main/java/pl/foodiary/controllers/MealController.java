package pl.foodiary.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.foodiary.domain.Meal;
import pl.foodiary.domain.User;
import pl.foodiary.repositories.MealRepository;
import pl.foodiary.repositories.UserRepository;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Mateusz on 2014-11-28.
 */
@Controller
@RequestMapping("/meal")
public class MealController {

	@Autowired
	private MealRepository mealRepository;

	@Autowired
	private UserRepository userRepository;

	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ResponseBody
	public Iterable<Meal> getUsersMeals(@RequestParam("userId") UUID userId) {
		User user = userRepository.findOneById(userId);
		return mealRepository.findByUser(user);
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public Iterable<Meal> listMeals() {
		return mealRepository.findAll();
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String createMeal(@RequestParam("name") String name, @RequestParam("consumption_date") Date date, @RequestParam("user_id") UUID userId) {
		try {
			Meal meal = new Meal(UUID.randomUUID(), name, date, userRepository.findOneById(userId));
			mealRepository.save(meal);
			return "{\"id\":\"" + meal.getId() + "\"}";
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	@RequestMapping(value = "/update", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String updateMeal(@RequestParam("id") UUID id, @RequestParam(value = "name", required = false) String name, @RequestParam(value = "consumption_date", required = false) Date date, @RequestParam(value = "user_id", required = false) UUID userId) {
		try {
			Meal meal = mealRepository.findOneById(id);
			if (name != null) meal.setName(name);
			if (date != null) meal.setConsumptionDay(date);
			if (userId != null) meal.setUser(userRepository.findOneById(userId));
			mealRepository.save(meal);
			return "{\"result\":true}";
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			return "{\"result\":false}";
		}
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String deleteMeal(@RequestParam("id") UUID id) {
		try {
			mealRepository.delete(mealRepository.findOneById(id));
			return "{\"result\":true}";
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			return "{\"result\":false}";
		}
	}
}
