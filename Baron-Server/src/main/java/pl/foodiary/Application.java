package pl.foodiary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.util.Scanner;

/**
 * Created by Mateusz on 2014-11-27.
 */
@EntityScan
@Configuration
@ComponentScan
@EnableAutoConfiguration
public class Application {

	public static String mainAPI = "";

	public static void main(String[] args) throws Exception {
		//TODO to erase in final version
		File mainScreen = new File("mainScreen.baronapi");
		Scanner in = new Scanner(mainScreen);
		while(in.hasNext()) {
			mainAPI += in.nextLine();
		}
		in.close();
		//TODO END to erase in final version

		SpringApplication.run(Application.class, args);
	}
}
