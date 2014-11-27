package pl.foodiary.controllers;

/**
 * Created by Mateusz on 2014-11-27.
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import pl.foodiary.domain.Product;
import pl.foodiary.domain.ProductCategory;
import pl.foodiary.repositories.ProductRepository;

import java.util.UUID;

@Configuration
@ComponentScan
@Controller
@EnableAutoConfiguration
public class MainController {

	@Autowired
	private ProductRepository productRepository;

	@RequestMapping("/")
	@ResponseBody
	String home() {
		return "Welcome on Food Diary Server!";
	}

	@RequestMapping("/test2")
	@ResponseBody
	Product test(){
		Product product = new Product(UUID.randomUUID(), "Kurczak", 100, 101, 102, 103, ProductCategory.MEAT);
		productRepository.save(product);
		return product;
	}
}
