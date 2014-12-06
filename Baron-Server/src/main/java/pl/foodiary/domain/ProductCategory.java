package pl.foodiary.domain;

import java.util.List;

/**
 * Created by Mateusz on 2014-11-27.
 */
public enum ProductCategory {
	MEAT("Mięso"), MILK("Nabiał"), VEGETABLE("Warzywa"), FISH("Ryby");

	private String name;

	ProductCategory(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public static ProductCategory getProductCategoryFromName(String name) {
		for(ProductCategory productCategory : ProductCategory.values()) {
			if(productCategory.getName().equals(name))
				return productCategory;
		}
		return null;
	}
}
