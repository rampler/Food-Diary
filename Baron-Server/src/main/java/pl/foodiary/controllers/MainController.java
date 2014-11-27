package pl.foodiary.controllers;

/**
 * Created by Mateusz on 2014-11-27.
 */

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@EnableAutoConfiguration
public class MainController {

	@RequestMapping("/")
	@ResponseBody
	String home() {
		return "Welcome on Food Diary Server!";
	}

}
