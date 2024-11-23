package com.example.mainactivity.classes;

public class User {
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String email;
    private String phone;
    private String password;
    private int personalId;

    //static variables
    private static int lastAssignedId = 0;

    // Constructor
    public User(String firstName, String lastName, String email, String password, String phone, String dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.personalId = generatePersonalId(lastAssignedId); // Assign a unique ID
        this.phone = phone;
        this.password = password;
    }

    // Method to generate a unique ID
    private int generatePersonalId(int lastId) {
        lastId = lastId++;
        return (lastId++); // Generates a unique identifier
    }

    // Getters
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public int getPersonalId() {
        return personalId;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }


    // Setters (if needed)
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPersonalId(int personalId) {
        this.personalId = personalId;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}