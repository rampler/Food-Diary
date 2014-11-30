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
		String version = "0.9.2";
		return "<style type=\"text/css\">\n" +
				"table, td\n" +
				"{\n" +
				"    border-color: #000;\n" +
				"    border-style: solid;\n" +
				"}\n" +
				"\n" +
				"table\n" +
				"{\n" +
				"    border-width: 0 0 1px 1px;\n" +
				"    border-spacing: 0;\n" +
				"    border-collapse: collapse;\n" +
				"}\n" +
				"\n" +
				"td\n" +
				"{\n" +
				"    margin: 0;\n" +
				"    padding: 4px;\n" +
				"    border-width: 1px 1px 0 0;\n" +
				"    background-color: #eee;\n" +
				"}\n" +
				"</style>" +
				"<h1>Welcome on Food Diary Server - Baron!</h1>" +
				"<h2>LET'S THE DEATH MARCH BEGIN!</h2>" +
				(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date())) + "<br />Version: "+version+
				Application.mainAPI2+Application.mainAPI;
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

	@RequestMapping("/baron/update2")
	@ResponseBody
	public String update2Baron(@RequestParam("text") String api) {
		Application.mainAPI2 = api;
		try {
			PrintWriter out = new PrintWriter("mainScreen2.baronapi", "UTF-8");
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

	@RequestMapping(value = "/baron/show2", produces = "text/plain")
	@ResponseBody
	public String list2Baron() {
		return Application.mainAPI2;
	}
	//TODO END

}
