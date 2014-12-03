package com.example.medicineApp.helpers;

import com.example.medicineApp.objects.Product;
import com.example.medicineApp.objects.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sabina on 2014-11-20.
 */
public class Globals {

    private static Globals instance = null;
    private List<Product> products = new ArrayList<Product>();
    private String sessionId = "";
    private static final String ServerURL = "http://foodiary.herokuapp.com";
    private boolean hasProfile = false;
    private User user;

    private Globals(){}

    public static String getServerURL() {
        return ServerURL;
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public List<Product> getProducts() {
        return products;
    }

    public static synchronized Globals getInstance(){
        if(instance == null){
            instance = new Globals();
        }
        return instance;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public boolean isHasProfile() {
        return hasProfile;
    }

    public void setHasProfile(boolean hasProfile) {
        this.hasProfile = hasProfile;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
