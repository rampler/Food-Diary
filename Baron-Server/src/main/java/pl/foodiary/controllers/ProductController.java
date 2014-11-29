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

	@RequestMapping(value = "/create", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String createProduct(@RequestParam("name") String name, @RequestParam("calories") Integer calories, @RequestParam("fat") Integer fat, @RequestParam("carbon") Integer carbon, @RequestParam("protein") Integer protein, @RequestParam("category") String category) {
		Product product = new Product(UUID.randomUUID(), name, calories, carbon, protein, fat, ProductCategory.valueOf(category));
		productRepository.save(product);
		return "{\"id\":\"" + product.getId() + "\"}";
	}

	@RequestMapping(value = "/categories", method = RequestMethod.GET)
	@ResponseBody
	public ProductCategory[] getProductCategories() {
		return ProductCategory.values();
	}

	@RequestMapping(value = "/update", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String updateProduct(@RequestParam("id") UUID id, @RequestParam(value = "name", required = false) String name, @RequestParam(value = "calories", required = false) Integer calories, @RequestParam(value = "fat", required = false) Integer fat, @RequestParam(value = "carbon", required = false) Integer carbon, @RequestParam(value = "protein", required = false) Integer protein, @RequestParam(value = "category", required = false) String category) {
		try {
			Product product = productRepository.findOneById(id);
			if (calories != null) product.setCalories(calories);
			if (name != null) product.setName(name);
			if (carbon != null) product.setCarbon(carbon);
			if (protein != null) product.setProtein(fat);
			if (fat != null) product.setFat(fat);
			if (category != null) product.setCategory(ProductCategory.valueOf(category));
			productRepository.save(product);
			return "{\"result\":true}";
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			return "{\"result\":false}";
		}
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String deleteProduct(@RequestParam("id") UUID id) {
		try {
			productRepository.delete(productRepository.findOneById(id));
			return "{\"result\":true}";
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			return "{\"result\":false}";
		}
	}

}
