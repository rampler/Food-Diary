package pl.foodiary.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import pl.foodiary.services.SessionService;

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

	@Autowired
	private SessionService sessionService;

	private final static Logger log = LoggerFactory.getLogger(SessionController.class);

	@RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json")
	@ResponseBody
	public String login(HttpServletRequest request, @RequestParam("login") String login, @RequestParam("password") String password) {
		try {
			login = login.toLowerCase();
			User user = userRepository.findOneByLogin(login);
			if (user.getPassword().equals(password)) {
				Session session = new Session(UUID.randomUUID(), userRepository.findOneByLogin(login), new Date(), sessionService.getIpAddress(request));
				sessionRepository.save(session);
				return "{\"id\":\"" + session.getId() + "\"}";
			}
			else throw new NotAuthorizedException(login);
		}
		catch (NotAuthorizedException ex) { throw ex; }
		catch (Exception e) {
			log.error(e.getMessage());
			throw new NotAuthorizedException(login);
		}
	}

	@RequestMapping(value = "/register", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json")
	@ResponseBody
	public String register(@RequestParam("login") String login, @RequestParam("password") String password, @RequestParam("mailAddress") String email) {
		try {
			User user = new User(UUID.randomUUID(), login.toLowerCase(), password, email);
			userRepository.save(user);
			return "{\"result\":true}";
		}
		catch (Exception e) {
			log.error(e.getMessage());
			return "{\"result\":false}";
		}
	}

	@RequestMapping(value = "/logout", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public void logout(@RequestParam("sessionId") UUID sessionId) {
		sessionRepository.delete(sessionRepository.findOne(sessionId));
	}

}
