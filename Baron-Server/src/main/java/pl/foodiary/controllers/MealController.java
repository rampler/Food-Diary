package pl.foodiary.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.foodiary.domain.Meal;
import pl.foodiary.domain.MealData;
import pl.foodiary.domain.User;
import pl.foodiary.repositories.MealDataRepository;
import pl.foodiary.repositories.MealRepository;
import pl.foodiary.repositories.UserRepository;

import java.util.UUID;

/**
 * Created by Mateusz on 2014-11-28.
 */
@Controller
@RequestMapping("/meal")
public class MealController {

	@Autowired
	private MealDataRepository mealDataRepository;

	@Autowired
	private UserRepository userRepository;

	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ResponseBody
	public Iterable<MealData> getUsersMeals(@RequestParam("userId") UUID userId) {
		User user = userRepository.findOneById(userId);
		return mealDataRepository.findByUser(user);
	}
}
