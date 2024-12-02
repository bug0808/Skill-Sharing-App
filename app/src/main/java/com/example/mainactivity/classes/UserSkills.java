package com.example.mainactivity.classes;

import java.util.List;

public class UserSkills implements Comparable<UserSkills> {
    private int userId;
    private List<String> skills;
    private double similarityScore;

    public UserSkills(int userId, List<String> skills) {
        this.userId = userId;
        this.skills = skills;
        this.similarityScore = 0.0;
    }

    public int getUserId() {
        return userId;
    }

    public List<String> getSkills() {
        return skills;
    }

    public double getSimilarityScore() {
        return similarityScore;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public void setSimilarityScore(double similarityScore) {
        this.similarityScore = similarityScore;
    }

    @Override
    public String toString() {
        return "UserSkills{" +
                "userId='" + userId + '\'' +
                ", skills=" + skills +
                '}';
    }

    @Override
    public int compareTo(UserSkills other) {
        return Double.compare(this.similarityScore, other.similarityScore);
    }
}
