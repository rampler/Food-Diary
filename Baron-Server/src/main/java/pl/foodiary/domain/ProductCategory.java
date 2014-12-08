package pl.foodiary.domain;

import java.util.List;

/**
 * Created by Mateusz on 2014-11-27.
 */
public enum ProductCategory {
	MEAT("Mięso"), MILK_AND_EGGS("Nabiał i jaja"), VEGETABLES("Warzywa"), FISH("Ryby"), GRAIN_PRODUCTS("Produkty zbożowe"),
	FRUITS("Owoce"), DRINKS_AND_SWEETIES("Napoje i słodycze");

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
