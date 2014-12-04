package pl.foodiary.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.foodiary.domain.User;
import pl.foodiary.domain.Workout;
import pl.foodiary.exceptions.NotAuthorizedException;
import pl.foodiary.repositories.ExerciseRepository;
import pl.foodiary.repositories.WorkoutRepository;
import pl.foodiary.services.SessionService;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Mateusz on 2014-12-02.
 */
@Controller
@RequestMapping("/workout")
public class WorkoutController {
	@Autowired
	private WorkoutRepository workoutRepository;

	@Autowired
	private ExerciseRepository exerciseRepository;

	@Autowired
	private SessionService sessionService;

	private final static Logger log = LoggerFactory.getLogger(WorkoutController.class);

	@RequestMapping(value = "/setDone", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json")
	@ResponseBody
	public String setDone(HttpServletRequest request, @RequestParam("sessionId") UUID sessionId, @RequestParam("id") UUID id, @RequestParam("done") Boolean done) {
		try {
			User user = sessionService.checkSession(sessionId, request);
			Workout workout = workoutRepository.findOne(id);

			if (user.getId().equals(workout.getUserId())) {
				workout.setDone(done);
				workoutRepository.save(workout);
				return "{\"result\":true}";
			}
			else throw new NotAuthorizedException();
		}
		catch (NotAuthorizedException ex) {throw ex;}
		catch (Exception e) {
			log.error(e.getMessage());
			return "{\"result\":false}";
		}
	}

	@RequestMapping(value = "/add", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json")
	@ResponseBody
	public String addWorkout(HttpServletRequest request, @RequestParam("sessionId") UUID sessionId, @RequestParam("exerciseId") UUID exerciseId, @RequestParam("repeats") Integer repeats, @RequestParam("quantity") Double quantity, @RequestParam("workoutDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date workoutDate) {
		try {
			User user = sessionService.checkSession(sessionId, request);
			Workout workout = new Workout(UUID.randomUUID(), user, exerciseRepository.findOne(exerciseId), repeats, quantity, workoutDate, false);
			workoutRepository.save(workout);
			return "{\"id\":\"" + workout.getId() + "\"}";
		}
		catch (NotAuthorizedException ex) { throw ex; }
		catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}

	@RequestMapping(value = "/change", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json")
	@ResponseBody
	public String changeWorkout(HttpServletRequest request, @RequestParam("sessionId") UUID sessionId, @RequestParam("id") UUID id, @RequestParam(value = "repeats", required = false) Integer repeats, @RequestParam(value = "quantity", required = false) Double quantity, @RequestParam(value = "workoutDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date workoutDate, @RequestParam(value = "done", required = false) Boolean done) {
		try {
			User user = sessionService.checkSession(sessionId, request);
			Workout workout = workoutRepository.findOne(id);

			if (user.getId().equals(workout.getUserId())) {
				if (repeats != null) workout.setRepeats(repeats);
				if (quantity != null) workout.setQuantity(quantity);
				if (workoutDate != null) workout.setWorkoutDate(workoutDate);
				if (done != null) workout.setDone(done);
				workoutRepository.save(workout);
				return "{\"result\":true}";
			}
			else throw new NotAuthorizedException();
		}
		catch (NotAuthorizedException ex) { throw ex; }
		catch (Exception e) {
			log.error(e.getMessage());
			return "{\"result\":false}";
		}
	}

	@RequestMapping(value = "/erase", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json")
	@ResponseBody
	public String eraseWorkout(HttpServletRequest request, @RequestParam("sessionId") UUID sessionId, @RequestParam("id") UUID id) {
		try {
			User user = sessionService.checkSession(sessionId, request);
			Workout workout = workoutRepository.findOne(id);

			if (user.getId().equals(workout.getUserId())) {
				workoutRepository.delete(workout);
				return "{\"result\":true}";
			}
			else throw new NotAuthorizedException();
		}
		catch (NotAuthorizedException ex) { throw ex; }
		catch (Exception e) {
			log.error(e.getMessage());
			return "{\"result\":false}";
		}
	}

	@RequestMapping(value = "getList", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public Iterable<Workout> getListOfWorkouts(HttpServletRequest request, @RequestParam("sessionId") UUID sessionId, @RequestParam(value = "workoutDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date workoutDate) {
		User user = sessionService.checkSession(sessionId, request);
		if (workoutDate != null) return workoutRepository.findByUserAndWorkoutDate(user, workoutDate);
		return workoutRepository.findByUser(user);
	}
}
