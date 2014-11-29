package pl.foodiary.controllers;

/**
 * Created by Mateusz on 2014-11-27.
 */

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.foodiary.Application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.UUID;

@Controller
public class MainController {

	@RequestMapping("/")
	@ResponseBody
	String home() {
		return "<h1>Welcome on Food Diary Server!</h1><br />" +
				"<h2>LET'S THE DEATH MARCH BEGIN!</h2><br />" +
				(new Date().toString()) + "<br />" + Application.mainAPI;
//				"<h2>API:</h2>" +
//				"- /user/create - params: [String login, String password] - method GET - returns user UUID in json or null if exists//temporary<br />" +
//				"- /user/getId - params: [String login] - GET - returns user UUID in json or null if not exists<br />" +
//				"- /user/list - params: null - GET - returns List of Users<br />" +
//				"- /user/update - params: required: [UUID id] nonRequired: [String login, String password] - GET - return true/false in json <br />" +
//				"- /user/delete - params: [UUID id] - GET - returns true/false in json<br />" +
//				"- /product/list - params: null - GET - returns list of all products<br />" +
//				"- /product/categories - params: null - returns list of possible categories<br />" +
//				"- /product/create - params: [String name, Integer calories, Integer carbon, Integer protein, Integer fat, String category] - GET - returns UUID in json of product - if error - code 500<br />" +
//				"- /product/update - params: required: [UUID id] nonRequired: [String name, Integer calories, Integer carbon, Integer protein, Integer fat, String category] - GET - returns true/false in json<br />" +
//				"- /product/delete - params: [UUID id] - GET - returns true/false in json<br />" +
//				"- /meal/get - params: [UUID userId] - GET - returns list of user Meals<br />" +
//				"- /meal/create - params [String name, Date consumption_date, UUID user_id] - GET - returns UUID in json or null<br />" +
//				"- /meal/list - params: null - GET - returns list of meals<br />" +
//				"- /meal/update - params: required: [UUID id] nonRequired: [String name, Date consumption_date, UUID user_id] - GET - returns true/false in json <br />" +
//				"- /meal/delete - params: [UUID id] - GET - returns true/false  in json <br />" +
//				"" +
//				"<br /><br /><b>Watch out!</b><br /> - UUID and booleans are represented in json like:<br /><br />{\"id\":\"810e6366-c739-4af1-b1a2-54b762f5f318\"} and {\"result\":true}";
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
	public String listBaron(){
		return Application.mainAPI;
	}
	//TODO END

}
