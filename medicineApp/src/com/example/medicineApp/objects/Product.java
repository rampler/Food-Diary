package com.example.medicineApp.objects;

/**
 * Created by Sabina on 2014-11-29.
 */
public class Product {

    private String id;
    private String name;
    private String calories;
    private String carbon;
    private String protein;
    private String fat;
    private String type;
    private Boolean checked;

    public Product(String id, String name, String calories, String carbon, String protein, String fat, String category, Boolean checked) {
        this.id = id;
        this.name = name;
        this.calories = calories;
        this.carbon = carbon;
        this.protein = protein;
        this.fat = fat;
        this.type = category;
        this.checked = checked;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Product)) return false;
        else if (((Product) obj).getId().equals(this.getId())) return true;
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
        return carbon;
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

    public Boolean isChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }
}
