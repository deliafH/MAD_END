package com.example.myapplication.model;

import java.io.Serializable;

public class Medicine implements Serializable {
    private int id;
    private String name;
    private float price;
    private Use use;
    private String type;

    public Medicine(){

    }

    public Medicine(int id, String name, float price, Use use, String type) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.use = use;
        this.type = type;
    }

    public Medicine(String name, float price, Use use, String type) {
        this.name = name;
        this.price = price;
        this.use = use;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Use getUses() {
        return use;
    }

    public void setUses(Use use) {
        this.use = use;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
