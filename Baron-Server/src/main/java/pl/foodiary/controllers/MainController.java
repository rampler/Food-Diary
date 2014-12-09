package pl.foodiary.controllers;

/**
 * Created by Mateusz on 2014-11-27.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.foodiary.Application;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class MainController {

	private final static Logger log = LoggerFactory.getLogger(MainController.class);

	@RequestMapping("/")
	@ResponseBody
	String home() {
		String version = "0.12.7";
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
				(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date())) + "<br />Version: " + version +
				Application.mainAPI2 + Application.mainAPI;
	}
}
