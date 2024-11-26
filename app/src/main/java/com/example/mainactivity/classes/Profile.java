package com.example.mainactivity.classes;

public class Profile {
    private int id;
    private String firstName;
    private String lastName;

    // Constructor to initialize the Profile with only id, firstName, and lastName
    public Profile(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
