package com.example.mainactivity;

public class User {
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String email;
    private String phone;
    private String password;
    private int personalId;

    // Static variable to hold the last assigned ID
    private static int lastAssignedId = 0;

    // Constructor
    public User(String firstName, String lastName, String dateOfBirth, String email, String phone, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.personalId = generatePersonalId(); // Assign a unique ID
    }

    // Method to generate a unique personal ID
    private int generatePersonalId() {
        lastAssignedId++;  // Increment the static variable
        return lastAssignedId;
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

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPersonalId(int personalId) {
        this.personalId = personalId;
    }

    // Optional: Add method to reset the last assigned ID (useful if you want to reset it on app restart)
    public static void resetLastAssignedId() {
        lastAssignedId = 0;
    }
}
