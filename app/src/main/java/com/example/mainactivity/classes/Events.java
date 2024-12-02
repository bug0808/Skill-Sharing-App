package com.example.mainactivity.classes;


public class Events {
    protected String userId;
    private String title;
    private String description;
    private String date;
    private String location;


    public Events(String userId, String title, String description, String date, String location) {
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.date = date;
        this.location = location;
    }

    public String getId() {
        return userId;
    }

    public void setId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}