package pl.foodiary.controllers;

/**
 * Created by Mateusz on 2014-11-27.
 */

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
public class MainController {

	@RequestMapping("/")
	@ResponseBody
	String home() {
		return "<h1>Welcome on Food Diary Server!</h1><br />" +
				"<h2>LET'S THE DEATH MARCH BEGIN!</h2><br />" +
				(new Date().toString()) + "<br />" +
				"<h2>API:</h2>" +
				"- /user/create - params: [String login, String password] - method GET - returns user UUID or null if exists//temporary<br />" +
				"- /user/getId - params: [String login] - GET - returns user UUID or null if not exists";
	}

}
