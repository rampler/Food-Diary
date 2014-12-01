package pl.foodiary.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.foodiary.domain.Meal;
import pl.foodiary.domain.User;
import pl.foodiary.exceptions.NotAuthorizedException;
import pl.foodiary.repositories.MealRepository;
import pl.foodiary.repositories.UserRepository;
import pl.foodiary.services.SessionService;

import javax.servlet.http.HttpServletRequest;
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

	@Autowired
	private SessionService sessionService;

	//API 2.0
	@RequestMapping(value = "/getList", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Iterable<Meal> getListOfUserMeals(HttpServletRequest request, @RequestParam("sessionId") UUID sessionId, @RequestParam(value = "consumptionDay", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
		User user = sessionService.checkSession(sessionId, request.getRemoteAddr());
		if (date == null) return mealRepository.findByUser(user);
		else return mealRepository.findByUserAndConsumptionDay(user, date);
	}

	@RequestMapping(value = "/add", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public String addMeal(HttpServletRequest request, @RequestParam("sessionId") UUID sessionId, @RequestParam("name") String name, @RequestParam("consumptionDay") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
		try {
			User user = sessionService.checkSession(sessionId, request.getRemoteAddr());
			Meal meal = new Meal(UUID.randomUUID(), name, date, user);
			mealRepository.save(meal);
			return "{\"id\":\"" + meal.getId() + "\"}";
		}
		catch (NotAuthorizedException ex) { throw ex; }
		catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	@RequestMapping(value = "/change", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public String changeMeal(HttpServletRequest request, @RequestParam("sessionId") UUID sessionId, @RequestParam("id") UUID id, @RequestParam(value = "name", required = false) String name, @RequestParam(value = "consumptionDay", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
		try {
			User user = sessionService.checkSession(sessionId, request.getRemoteAddr());
			Meal meal = mealRepository.findOneById(id);

			if (user.getId().equals(meal.getUserId())) {
				if (name != null) meal.setName(name);
				if (date != null) meal.setConsumptionDay(date);
				mealRepository.save(meal);
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

	@RequestMapping(value = "/erase", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public String eraseMeal(HttpServletRequest request, @RequestParam("sessionId") UUID sessionId, @RequestParam("id") UUID id) {
		try {
			User user = sessionService.checkSession(sessionId, request.getRemoteAddr());
			Meal meal = mealRepository.findOneById(id);

			if (user.getId().equals(meal.getUserId())) {
				mealRepository.delete(meal);
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

	//API 1.0
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
	public String createMeal(@RequestParam("name") String name, @RequestParam("consumption_date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date, @RequestParam("user_id") UUID userId) {
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
	public String updateMeal(@RequestParam("id") UUID id, @RequestParam(value = "name", required = false) String name, @RequestParam(value = "consumption_date", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date date, @RequestParam(value = "user_id", required = false) UUID userId) {
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
