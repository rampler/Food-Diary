package pl.foodiary.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.foodiary.domain.User;
import pl.foodiary.repositories.UserRepository;

import java.util.UUID;

/**
 * Created by Mateusz on 2014-11-28.
 */
@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@RequestMapping(value = "/create", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String addUser(@RequestParam("login") String login, @RequestParam("password") String password) {
		try {
			User user = new User(UUID.randomUUID(), login, password);
			userRepository.save(user);
			return "{\"id\":\"" + user.getId() + "\"}";
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	@RequestMapping(value = "/getId", method = RequestMethod.GET)
	@ResponseBody
	public UUID getUserId(@RequestParam("login") String login) {
		return (userRepository.findOneByLogin(login) != null) ? userRepository.findOneByLogin(login).getId() : null;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public Iterable<User> listUsers() {
		return userRepository.findAll();
	}

	@RequestMapping(value = "/update", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String upadateUser(@RequestParam(value = "id", required = true) UUID id, @RequestParam(value = "login", required = false) String login, @RequestParam(value = "password", required = false) String password) {
		try {
			User user = userRepository.findOneById(id);
			if (login != null) user.setLogin(login);
			if (password != null) user.setPassword(password);
			userRepository.save(user);
			return "{\"result\":true}";
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			return "{\"result\":false}";
		}
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String deleteUser(@RequestParam("id") UUID id) {
		try {
			userRepository.delete(userRepository.findOneById(id));
			return "{\"result\":true}";
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			return "{\"result\":false}";
		}
	}

}
