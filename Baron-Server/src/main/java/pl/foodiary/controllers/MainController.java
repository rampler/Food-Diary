package pl.foodiary.controllers;

/**
 * Created by Mateusz on 2014-11-27.
 */
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
@EnableAutoConfiguration
public class MainController {

	@RequestMapping("/")
	@ResponseBody
	String home() {
		return "Welcome on Food Diary Server!";
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(MainController.class, args);
	}
}
