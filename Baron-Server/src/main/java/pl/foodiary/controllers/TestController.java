package pl.foodiary.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.foodiary.domain.Product;
import pl.foodiary.domain.ProductCategory;
import pl.foodiary.repositories.ProductRepository;

import java.util.UUID;

/**
 * Created by Mateusz on 2014-11-27.
 */
@Controller
@RequestMapping("/test")
public class TestController {

	@Autowired
	private ProductRepository productRepository;

	@RequestMapping("/product")
	@ResponseBody
	Product test() {
		Product product = new Product(UUID.randomUUID(), "Kurczak", 100, 101, 102, 103, ProductCategory.MEAT);
		productRepository.save(product);
		return product;
	}
}
