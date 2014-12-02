package pl.foodiary.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.foodiary.domain.Exercise;
import pl.foodiary.repositories.ExerciseRepository;

import java.util.UUID;

/**
 * Created by Mateusz on 2014-12-02.
 */
@Controller
@RequestMapping("/exercise")
public class ExerciseController {

	@Autowired
	private ExerciseRepository exerciseRepository;

	@RequestMapping(value = "/get", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public Exercise getExercise(@RequestParam("id") UUID id) {
		return exerciseRepository.findOne(id);
	}

	@RequestMapping(value = "/create", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json")
	@ResponseBody
	public String createExercise(@RequestParam("name") String name, @RequestParam("unit") String unit) {
		Exercise exercise = new Exercise(UUID.randomUUID(), name, unit);
		exerciseRepository.save(exercise);
		return "{\"id\":\"" + exercise.getId() + "\"}";
	}

	@RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public Iterable<Exercise> listExercises(@RequestParam(value = "unit", required = false) String unit) {
		if (unit != null) return exerciseRepository.findByUnit(unit);
		return exerciseRepository.findAll();
	}

	@RequestMapping(value = "/delete", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json")
	@ResponseBody
	public String deleteExercise(@RequestParam("id") UUID id) {
		try {
			exerciseRepository.delete(exerciseRepository.findOne(id));
			return "{\"result\":true}";
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			return "{\"result\":false}";
		}
	}

//	@RequestMapping(value = "/update", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
//	@ResponseBody
//	public String updateExercise(@RequestParam("id") UUID id, @RequestParam(value = "name",required = false) String name, @RequestParam(value = "unit",required = false) String unit) {
//		try {
//			Exercise exercise = exerciseRepository.findOne(id);
//			if (name != null) exercise.setName(name);
//			if (unit != null) exercise.setUnit(unit);
//			exerciseRepository.save(exercise);
//			return "{\"result\":true}";
//		}
//		catch (Exception e) {
//			System.out.println(e.getMessage());
//			return "{\"result\":false}";
//		}
//	}
}
