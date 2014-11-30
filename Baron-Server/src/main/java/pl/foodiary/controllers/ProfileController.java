package pl.foodiary.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.foodiary.domain.Profile;
import pl.foodiary.repositories.ProfileRepository;
import pl.foodiary.repositories.UserRepository;

import java.util.UUID;

/**
 * Created by Mateusz on 2014-11-30.
 */
@Controller
@RequestMapping("/profile")
public class ProfileController {
	@Autowired
	private ProfileRepository profileRepository;

	@Autowired
	private UserRepository userRepository;

	@RequestMapping(value = "/create", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String createProfile(@RequestParam("first_name") String firstName, @RequestParam("last_name") String lastName, @RequestParam("weight") Double weight, @RequestParam("calories_counter") Double caloriesCounter, @RequestParam("age") Integer age, @RequestParam("user_id") UUID userId) {
		try {
			Profile profile = new Profile(UUID.randomUUID(), firstName, lastName, weight, caloriesCounter, age, userRepository.findOneById(userId));
			profileRepository.save(profile);
			return "{\"id\":\"" + profile.getId() + "\"}";
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	@RequestMapping(value = "/isUserHave",method = RequestMethod.GET,produces = "application/json")
	@ResponseBody
	public String isUserHaveProfile(@RequestParam("user_id") UUID userId) {
		try {
			Long count = profileRepository.countByUser(userRepository.findOneById(userId));
			if(count == 1)
				return "{\"result\":true}";
			else
				return "{\"result\":false}";
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			return "{\"result\":false}";
		}
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public Iterable<Profile> listProfiles() {
		return profileRepository.findAll();
	}

	@RequestMapping(value = "/update", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String updateProfile(@RequestParam("id") UUID id, @RequestParam(value = "first_name", required = false) String firstName, @RequestParam(value = "last_name", required = false) String lastName, @RequestParam(value = "weight", required = false) Double weight, @RequestParam(value = "calories_counter", required = false) Double caloriesCounter, @RequestParam(value = "age", required = false) Integer age, @RequestParam(value = "user_id", required = false) UUID userId) {
		try {
			Profile profile = profileRepository.findOneById(id);
			if (firstName != null) profile.setFirstName(firstName);
			if (lastName != null) profile.setLastName(lastName);
			if (weight != null) profile.setWeight(weight);
			if (caloriesCounter != null) profile.setCaloriesCounter(caloriesCounter);
			if (age != null) profile.setAge(age);
			if (userId != null) profile.setUser(userRepository.findOneById(userId));
			profileRepository.save(profile);
			return "{\"result\":true}";
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			return "{\"result\":false}";
		}
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String deleteProfile(@RequestParam("id") UUID id) {
		try {
			profileRepository.delete(profileRepository.findOneById(id));
			return "{\"result\":true}";
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			return "{\"result\":false}";
		}
	}

}
