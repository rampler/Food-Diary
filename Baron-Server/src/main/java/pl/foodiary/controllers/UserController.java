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

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	@ResponseBody
	public UUID addUser(@RequestParam("login") String login, @RequestParam("password") String password) {
		try {
			User user = new User(UUID.randomUUID(), login, password);
			userRepository.save(user);
			return user.getId();
		}
		catch (Exception e) {
			return null;
		}
	}

	@RequestMapping(value = "/getId", method = RequestMethod.GET)
	@ResponseBody
	public UUID getUserId(@RequestParam("login") String login) {
		return (userRepository.findOneByLogin(login) != null)?userRepository.findOneByLogin(login).getId():null;
	}

}
