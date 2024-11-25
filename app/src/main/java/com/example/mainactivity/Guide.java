package com.example.mainactivity;

public class Guide {
    private String title;
    private String description;

    // Constructor
    public Guide(String title, String description) {
        this.title = title;
        this.description = description;
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    // Setters (optional, if you plan to modify the fields)
    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
