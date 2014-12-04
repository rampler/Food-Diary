package pl.foodiary.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import pl.foodiary.common.ConsumptionReview;
import pl.foodiary.common.Counters;
import pl.foodiary.common.MainStatistics;
import pl.foodiary.domain.Ingredient;
import pl.foodiary.domain.Meal;
import pl.foodiary.domain.Product;
import pl.foodiary.domain.User;
import pl.foodiary.repositories.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created by Mateusz on 2014-12-02.
 */
@Service
public class StatisticService {

	@Autowired
	private IngredientRepository ingredientRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private MealRepository mealRepository;

	@Autowired
	private ExerciseRepository exerciseRepository;

	@Autowired
	private WorkoutRepository workoutRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private final static Logger log = LoggerFactory.getLogger(StatisticService.class);

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

	public Counters calculateAllCounters() {
		return new Counters(productRepository.count(), ingredientRepository.count(), mealRepository.count(), exerciseRepository.count(), workoutRepository.count());
	}

	public Counters calculateAllCounters(Date date) {
		Counters counters = new Counters();
		counters.setMeals(mealRepository.countByConsumptionDay(date));

		long ingretientsCounter = 0;
		for (Meal meal : mealRepository.findByConsumptionDay(date)) {
			ingretientsCounter += ingredientRepository.countByMeal(meal);
		}
		counters.setIngredients(ingretientsCounter);

		long productsCounter = jdbcTemplate.query("select count(distinct(product_id)) from ingredient where meal_id in (select id from meal where consumption_day = '" + date + "')", new RowMapper<Long>() {
			@Override
			public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getLong("count");
			}
		}).get(0);
		counters.setProducts(productsCounter);

		counters.setWorkouts(workoutRepository.countByWorkoutDate(date));

		long exercisesCounter = jdbcTemplate.query("select count(distinct(exercise_id)) from workout where workout_date  = '" + date + "'", new RowMapper<Long>() {
			@Override
			public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getLong("count");
			}
		}).get(0);
		counters.setExercises(exercisesCounter);

		return counters;
	}

	public Counters calculateUserCounters(User user) {
		Counters counters = new Counters();
		counters.setMeals(mealRepository.countByUser(user));

		long ingretientsCounter = 0;
		for (Meal meal : mealRepository.findByUser(user)) {
			ingretientsCounter += ingredientRepository.countByMeal(meal);
		}
		counters.setIngredients(ingretientsCounter);

		long productsCounter = jdbcTemplate.query("select count(distinct(product_id)) from ingredient where meal_id in (select id from meal where user_id = '" + user.getId() + "')", new RowMapper<Long>() {
			@Override
			public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getLong("count");
			}
		}).get(0);
		counters.setProducts(productsCounter);

		counters.setWorkouts(workoutRepository.countByUser(user));

		long exercisesCounter = jdbcTemplate.query("select count(distinct(exercise_id)) from workout where user_id  = '" + user.getId() + "'", new RowMapper<Long>() {
			@Override
			public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getLong("count");
			}
		}).get(0);
		counters.setExercises(exercisesCounter);

		return counters;
	}

	public Counters calculateUserCounters(User user, Date date) {
		Counters counters = new Counters();
		counters.setMeals(mealRepository.countByUserAndConsumptionDay(user, date));

		long ingretientsCounter = 0;
		for (Meal meal : mealRepository.findByUserAndConsumptionDay(user, date)) {
			ingretientsCounter += ingredientRepository.countByMeal(meal);
		}
		counters.setIngredients(ingretientsCounter);

		long productsCounter = jdbcTemplate.query("select count(distinct(product_id)) from ingredient where meal_id in (select id from meal where user_id = '" + user.getId() + "' and consumption_day = '" + date + "')", new RowMapper<Long>() {
			@Override
			public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getLong("count");
			}
		}).get(0);
		counters.setProducts(productsCounter);

		counters.setWorkouts(workoutRepository.countByUserAndWorkoutDate(user, date));

		long exercisesCounter = jdbcTemplate.query("select count(distinct(exercise_id)) from workout where user_id  = '" + user.getId() + "' and workout_date = '" + date + "'", new RowMapper<Long>() {
			@Override
			public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getLong("count");
			}
		}).get(0);
		counters.setExercises(exercisesCounter);

		return counters;
	}

	public MainStatistics calculateMainStatistics() {
		MainStatistics mainStatistics = new MainStatistics();
		mainStatistics.setUsersCounter(userRepository.count());

		long unactiveUsers = jdbcTemplate.query("select count(id) from user_data where id not in (select user_id from profile)", new RowMapper<Long>() {
			@Override
			public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getLong("count");
			}
		}).get(0);
		mainStatistics.setUnactiveUsersCounter(unactiveUsers);

		long onlineUsers = jdbcTemplate.query("select count(id) from session where last_activity_date > current_timestamp - INTERVAL '30 mins'", new RowMapper<Long>() {
			@Override
			public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getLong("count");
			}
		}).get(0);
		mainStatistics.setOnlineUsersCounter(onlineUsers);

		return mainStatistics;
	}
}
