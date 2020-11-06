package com.example.logqualy.model;

import java.io.Serializable;

public class Products implements Serializable {
    private String id;
    private String title;
    private String description;
    private String date;

    public Products(String title, String description, String date){
        this.date = date;
        this.description = description;
        this.title = title;
    }

    public Products(){}

    public String getId() {
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

    public void setId(String id) {
        this.id = id;
    }
}
