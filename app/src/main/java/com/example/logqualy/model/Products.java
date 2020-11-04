package com.example.logqualy.model;

import java.io.Serializable;

public class Products implements Serializable {
    private String title;
    private String description;
    private String date;
    private int id;

    Products(String title, String description, String date){
        this.date = date;
        this.description = description;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setId(int id) {
        this.id = id;
    }
}
