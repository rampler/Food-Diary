package pl.foodiary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.File;
import java.util.Scanner;

/**
 * Created by Mateusz on 2014-11-27.
 */
@EntityScan
@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableScheduling
public class Application {

	public static String mainAPI  = "";
	public static String mainAPI2 = "";

	public static void main(String[] args) throws Exception {
		//API
		File mainScreen = new File("mainScreen.baronapi");
		Scanner in = new Scanner(mainScreen);
		while (in.hasNext()) {
			mainAPI += in.nextLine();
		}
		in.close();
		File mainScreen2 = new File("mainScreen2.baronapi");
		Scanner in2 = new Scanner(mainScreen2);
		while (in2.hasNext()) {
			mainAPI2 += in2.nextLine();
		}
		in2.close();
		//END API

		SpringApplication.run(Application.class, args);
	}
}
