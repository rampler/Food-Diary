package pl.foodiary.controllers;

/**
 * Created by Mateusz on 2014-11-27.
 */

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.foodiary.Application;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class MainController {

	@RequestMapping("/")
	@ResponseBody
	String home() {
		return "<h1>Welcome on Food Diary Server - Baron!</h1>" +
				"<h2>LET'S THE DEATH MARCH BEGIN!</h2>" +
				(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date())) + "<br />Version: 0.8.0" +
				Application.mainAPI;
	}

	//TODO to erase in final version
	@RequestMapping("/baron/update")
	@ResponseBody
	public String updateBaron(@RequestParam("text") String api) {
		Application.mainAPI = api;
		try {
			PrintWriter out = new PrintWriter("mainScreen.baronapi", "UTF-8");
			out.println(api);
			out.close();
		}
		catch (Exception e) {
			e.printStackTrace();
			return "failed!";
		}
		return "ok!";
	}

	@RequestMapping(value = "/baron/show", produces = "text/plain")
	@ResponseBody
	public String listBaron() {
		return Application.mainAPI;
	}
	//TODO END

}
