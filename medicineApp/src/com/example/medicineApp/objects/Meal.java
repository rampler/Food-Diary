package com.example.medicineApp.objects;

/**
 * Created by Sabina on 2014-12-05.
 */
public class Meal {

    private String id;
    private String name;
    private String consumptionDay;

    public Meal(String id, String name, String consumptionDay) {
        this.id = id;
        this.name = name;
        this.consumptionDay = consumptionDay;
    }

    public String getId() {
        return id;
    }

    public String getConsumptionDay() {
        return consumptionDay;
    }

    public String getName() {
        return name;
    }
}
