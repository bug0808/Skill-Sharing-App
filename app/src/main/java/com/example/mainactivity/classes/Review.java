package com.example.mainactivity.classes;

public class Review {
    private int reviewId;
    private int userId;
    private int reviewerId;
    private String reviewText;
    private int rating;
    private String date;

    // Constructor
    public Review(int userId, int reviewerId, String reviewText, int rating, String date) {
        this.userId = userId;
        this.reviewerId = reviewerId;
        this.reviewText = reviewText;
        this.rating = rating;
        this.date = date;
    }

    // Getters and setters
    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getReviewerId() {
        return reviewerId;
    }

    public void setReviewerId(int reviewerId) {
        this.reviewerId = reviewerId;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

