package pl.foodiary.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.foodiary.domain.Product;
import pl.foodiary.domain.ProductCategory;
import pl.foodiary.repositories.ProductRepository;

import java.util.UUID;

/**
 * Created by Mateusz on 2014-11-28.
 */
@Controller
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private ProductRepository productRepository;

	@RequestMapping("/list")
	@ResponseBody
	public Iterable<Product> listAllProducts() {
		return productRepository.findAll();
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	@ResponseBody
	public UUID createProduct(@RequestParam("name") String name, @RequestParam("calories") Integer calories,
	                          @RequestParam("fat") Integer fat, @RequestParam("carbon") Integer carbon,
	                          @RequestParam("protein") Integer protein, @RequestParam("category") String category) {
		Product product = new Product(UUID.randomUUID(), name, calories, carbon, protein, fat, ProductCategory.valueOf(category));
		productRepository.save(product);
		return product.getId();
	}


}
