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

	//API 2.0
	@RequestMapping(value = "/get", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public Product getProduct(@RequestParam("id") UUID id) {
		return productRepository.findOne(id);
	}

	//API 1.0 and 2.0
	@RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public Iterable<Product> listAllProducts(@RequestParam(value = "category", required = false) String category) {
		if (category != null) return productRepository.findByCategory(ProductCategory.valueOf(category));
		else return productRepository.findAll();
	}

	@RequestMapping(value = "/create", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json")
	@ResponseBody
	public String createProduct(@RequestParam("name") String name, @RequestParam("calories") Double calories, @RequestParam("fat") Double fat, @RequestParam("carbs") Double carbs, @RequestParam("protein") Double protein, @RequestParam("category") String category) {
		Product product = new Product(UUID.randomUUID(), name, calories, carbs, protein, fat, ProductCategory.valueOf(category));
		productRepository.save(product);
		return "{\"id\":\"" + product.getId() + "\"}";
	}

	@RequestMapping(value = "/categories", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ProductCategory[] getProductCategories() {
		return ProductCategory.values();
	}

	@RequestMapping(value = "/update", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json")
	@ResponseBody
	public String updateProduct(@RequestParam("id") UUID id, @RequestParam(value = "name", required = false) String name, @RequestParam(value = "calories", required = false) Double calories, @RequestParam(value = "fat", required = false) Double fat, @RequestParam(value = "carbs", required = false) Double carbs, @RequestParam(value = "protein", required = false) Double protein, @RequestParam(value = "category", required = false) String category) {
		try {
			Product product = productRepository.findOne(id);
			if (calories != null) product.setCalories(calories);
			if (name != null) product.setName(name);
			if (carbs != null) product.setCarbs(carbs);
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

	@RequestMapping(value = "/delete", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json")
	@ResponseBody
	public String deleteProduct(@RequestParam("id") UUID id) {
		try {
			productRepository.delete(productRepository.findOne(id));
			return "{\"result\":true}";
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			return "{\"result\":false}";
		}
	}

}
