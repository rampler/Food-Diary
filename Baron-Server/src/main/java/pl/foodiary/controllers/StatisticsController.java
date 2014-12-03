package pl.foodiary.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.foodiary.common.ConsumptionReview;
import pl.foodiary.common.Counters;
import pl.foodiary.domain.Meal;
import pl.foodiary.domain.User;
import pl.foodiary.exceptions.BadRequestException;
import pl.foodiary.exceptions.NotAuthorizedException;
import pl.foodiary.repositories.MealRepository;
import pl.foodiary.services.SessionService;
import pl.foodiary.services.StatisticService;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Mateusz on 2014-12-02.
 */
@Controller
@RequestMapping("/statistics")
public class StatisticsController {

	@Autowired
	private SessionService sessionService;

	@Autowired
	private MealRepository mealRepository;

	@Autowired
	private StatisticService statisticService;

	@RequestMapping(value = "/macronutrients", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ConsumptionReview getConsumptionReview(HttpServletRequest request, @RequestParam("sessionId") UUID sessionId, @RequestParam(value = "consumptionDay", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date consumptionDay, @RequestParam(value = "mealId", required = false) UUID mealId) {
		User user = sessionService.checkSession(sessionId, request.getRemoteAddr());
		if (mealId != null) {
			Meal meal = mealRepository.findOne(mealId);
			if (consumptionDay != null && !consumptionDay.equals(meal.getConsumptionDay()))
				throw new BadRequestException("'consumptionDay' is not equal Meal's 'consumptionDay'. Erase parametes 'consumptionDay' or 'mealId'");

			if (user.getId().equals(meal.getUserId())) return statisticService.calculateConsumptionReview(meal);
			else throw new NotAuthorizedException();
		}
		else {
			Iterable<Meal> meals;
			if (consumptionDay != null) meals = mealRepository.findByUserAndConsumptionDay(user, consumptionDay);
			else meals = mealRepository.findByUser(user);
			return statisticService.calculateConsumptionReview(meals);
		}
	}

	@RequestMapping(value = "/counters", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public Counters getCounters(HttpServletRequest request, @RequestParam(value = "sessionId", required = false) UUID sessionId, @RequestParam(value = "date", required = false)@DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
		if (sessionId != null) {
			User user = sessionService.checkSession(sessionId, request.getRemoteAddr());
			if(date != null) return statisticService.calculateUserCounters(user, date);
			return statisticService.calculateUserCounters(user);
		}
		else if(date != null) return statisticService.calculateAllCounters(date);
		else return statisticService.calculateAllCounters();
	}
}
