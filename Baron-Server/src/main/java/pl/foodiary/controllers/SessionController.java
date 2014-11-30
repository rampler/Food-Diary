package pl.foodiary.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.foodiary.domain.Session;
import pl.foodiary.domain.User;
import pl.foodiary.exceptions.NotAuthorizedException;
import pl.foodiary.repositories.SessionRepository;
import pl.foodiary.repositories.UserRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Mateusz on 2014-11-30.
 */
@Controller
public class SessionController {
	@Autowired
	private SessionRepository sessionRepository;

	@Autowired
	private UserRepository userRepository;

	@RequestMapping(value = "/login",method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String login(HttpServletRequest request, @RequestParam("login") String login, @RequestParam("password") String password) {
		try {
			User user = userRepository.findOneByLogin(login);
			if (user.getPassword().equals(password)) {
				Session session = new Session(UUID.randomUUID(), userRepository.findOneByLogin(login), new Date(), request.getRemoteAddr());
				sessionRepository.save(session);
				return "{\"id\":\"" + session.getId() + "\"}";
			}
			else {
				throw new NotAuthorizedException(login);
			}
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			throw new NotAuthorizedException(login);
		}
	}

	@RequestMapping(value = "/register",method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String register(@RequestParam("login") String login, @RequestParam("password") String password, @RequestParam("mailAddress") String email) {
		try {
			User user = new User(UUID.randomUUID(), login.toLowerCase(), password, email);
			userRepository.save(user);
			return "{\"id\":\"" + user.getId() + "\"}";
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	@ResponseBody
	public void logout(@RequestParam("sessionId") UUID sessionId) {
		sessionRepository.delete(sessionRepository.findOneById(sessionId));
	}

}
