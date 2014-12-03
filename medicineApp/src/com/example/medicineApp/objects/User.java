package com.example.medicineApp.objects;

/**
 * Created by Sabina on 2014-12-03.
 */
public class User {

    private String firstName;
    private String lastName;
    private String weight;
    private String age;
    private String calories;

    public User(String firstName, String lastName, String weight, String age, String calories) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.weight = weight;
        this.age = age;
        this.calories = calories;
    }


    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getWeight() {
        return weight;
    }

    public String getAge() {
        return age;
    }

    public String getCalories() {
        return calories;
    }
}
