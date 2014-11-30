package com.example.medicineApp;

/**
 * Created by Sabina on 2014-11-29.
 */
public class Product {

    private String id;
    private String name;
    private String calories;
    private String carbs;
    private String protein;
    private String fat;
    private String type;

    public Product(String id, String name, String calories, String carbon, String protein, String fat, String category) {
        this.id = id;
        this.name = name;
        this.calories = calories;
        this.carbs = carbon;
        this.protein = protein;
        this.fat = fat;
        this.type = category;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Product)) return false;
        else if (((Product) obj).getId().equals(this.getId()) && ((Product) obj).getName().equals(this.getName())) return true;
        return false;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCalories() {
        return calories;
    }

    public String getCarbon() {
        return carbs;
    }

    public String getProtein() {
        return protein;
    }

    public String getFat() {
        return fat;
    }

    public String getType() {
        return type;
    }
}
