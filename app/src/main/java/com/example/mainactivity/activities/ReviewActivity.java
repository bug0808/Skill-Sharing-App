package com.example.mainactivity.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mainactivity.DatabaseHelper;
import com.example.mainactivity.R;
import com.example.mainactivity.classes.Review;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ReviewActivity extends AppCompatActivity {

    private DatabaseHelper db;
    private ImageView star1, star2, star3, star4, star5;
    private EditText reviewEditText;
    private Button submitButton, cancelButton;
    private int rating = 0; // This will store the selected rating out of 5

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        // Initialize the ImageViews for stars
        star1 = findViewById(R.id.star1);
        star2 = findViewById(R.id.star2);
        star3 = findViewById(R.id.star3);
        star4 = findViewById(R.id.star4);
        star5 = findViewById(R.id.star5);

        //initialize edit text
        reviewEditText = findViewById(R.id.edittextReview);
        submitButton = findViewById(R.id.submitButton);
        cancelButton = findViewById(R.id.cancelButton);

        // Set up listeners for each star to set the rating
        View.OnClickListener starClickListener = view -> {
            if (view.getId() == R.id.star1) {
                rating = 1;
            } else if (view.getId() == R.id.star2) {
                rating = 2;
            } else if (view.getId() == R.id.star3) {
                rating = 3;
            } else if (view.getId() == R.id.star4) {
                rating = 4;
            } else if (view.getId() == R.id.star5) {
                rating = 5;
            }
            updateStarImages();
        };

        // Assign the listener to each star image
        star1.setOnClickListener(starClickListener);
        star2.setOnClickListener(starClickListener);
        star3.setOnClickListener(starClickListener);
        star4.setOnClickListener(starClickListener);
        star5.setOnClickListener(starClickListener);

        // Set submit button to get review
        submitButton.setOnClickListener(view -> submitReview());

        // Set up cancel button
        cancelButton.setOnClickListener(view -> finish());
    }

    private void updateStarImages() {
        // Update star images based on rating
        star1.setImageResource(rating >= 1 ? R.drawable.starfilled : R.drawable.starhollow);
        star2.setImageResource(rating >= 2 ? R.drawable.starfilled : R.drawable.starhollow);
        star3.setImageResource(rating >= 3 ? R.drawable.starfilled : R.drawable.starhollow);
        star4.setImageResource(rating >= 4 ? R.drawable.starfilled : R.drawable.starhollow);
        star5.setImageResource(rating >= 5 ? R.drawable.starfilled : R.drawable.starhollow);
    }

    private void submitReview() {
        String reviewText = reviewEditText.getText().toString();

        if (rating == 0) {
            Toast.makeText(this, "Please select a star rating", Toast.LENGTH_SHORT).show();
            return;
        }
        if (reviewText.isEmpty()) {
            Toast.makeText(this, "Please write a review", Toast.LENGTH_SHORT).show();
            return;
        }

        // Retrieve logged-in user's ID from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        int loggedInUserId = sharedPreferences.getInt("userId", -1);  // Get logged-in user's ID

        // Get the user ID of the person being reviewed (passed from another activity)
        int userIdToReview = getIntent().getIntExtra("userIdToReview", -1);  // Get ID of user being reviewed

        if (loggedInUserId == -1 || userIdToReview == -1) {
            Toast.makeText(this, "Error: Invalid user IDs", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a new Review object
        Review review = new Review(userIdToReview, loggedInUserId, reviewText, rating, getCurrentDate());

        // Save the review to the database
        long result = db.addReview(review);
        db.close();

        if (result != -1) {
            // Review successfully inserted into the database
            Toast.makeText(this, "Review submitted with " + rating + " stars", Toast.LENGTH_SHORT).show();
        } else {
            // Handle error in inserting review
            Toast.makeText(this, "Error submitting review", Toast.LENGTH_SHORT).show();
        }

        // Reset review input fields
        rating = 0;
        updateStarImages();
        reviewEditText.setText(""); // Clear the review text
    }


    // Helper method to get current date in a standard format
    private String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sdf.format(new Date());
    }
}

