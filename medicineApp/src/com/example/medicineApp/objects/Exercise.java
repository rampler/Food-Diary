package com.example.medicineApp.objects;

/**
 * Created by Sabina on 2014-12-06.
 */
public class Exercise {

    private String id;
    private String name;
    private String unit;

    public Exercise (String id, String name, String unit) {
        this.id = id;
        this.name = name;
        this.unit = unit;
    }


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUnit() {
        return unit;
    }
}
