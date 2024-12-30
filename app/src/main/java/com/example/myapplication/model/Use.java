package com.example.myapplication.model;

import java.io.Serializable;

public class Use implements Serializable {
    private int id;
    private String name;
    private String description;

    public Use() {
    }

    public Use(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Use(String name, String description) {
        this.name = name;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
