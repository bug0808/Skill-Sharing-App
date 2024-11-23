package com.example.mainactivity.classes;

import java.util.List;

public class UserSkills {
    private String userId;         // Stores the user's ID
    private List<String> skills;   // Stores the list of skills

    // Constructor
    public UserSkills(String userId, List<String> skills) {
        this.userId = userId;
        this.skills = skills;
    }

    // Getter for userId
    public String getUserId() {
        return userId;
    }

    // Setter for userId
    public void setUserId(String userId) {
        this.userId = userId;
    }

    // Getter for skills
    public List<String> getSkills() {
        return skills;
    }

    // Setter for skills
    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    @Override
    public String toString() {
        return "UserSkills{" +
                "userId='" + userId + '\'' +
                ", skills=" + skills +
                '}';
    }
}
