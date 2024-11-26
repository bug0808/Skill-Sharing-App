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
    private int rating = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        star1 = findViewById(R.id.star1);
        star2 = findViewById(R.id.star2);
        star3 = findViewById(R.id.star3);
        star4 = findViewById(R.id.star4);
        star5 = findViewById(R.id.star5);

        reviewEditText = findViewById(R.id.edittextReview);
        submitButton = findViewById(R.id.submitButton);
        cancelButton = findViewById(R.id.cancelButton);

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

        star1.setOnClickListener(starClickListener);
        star2.setOnClickListener(starClickListener);
        star3.setOnClickListener(starClickListener);
        star4.setOnClickListener(starClickListener);
        star5.setOnClickListener(starClickListener);

        submitButton.setOnClickListener(view -> submitReview());
        cancelButton.setOnClickListener(view -> finish());
    }

    private void updateStarImages() {
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

        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        int loggedInUserId = sharedPreferences.getInt("userId", -1);
        int userIdToReview = getIntent().getIntExtra("userIdToReview", -1);

        if (loggedInUserId == -1 || userIdToReview == -1) {
            Toast.makeText(this, "Error: Invalid user IDs", Toast.LENGTH_SHORT).show();
            return;
        }

        Review review = new Review(userIdToReview, loggedInUserId, reviewText, rating, getCurrentDate());

        long result = db.addReview(review);
        db.close();

        if (result != -1) {
            Toast.makeText(this, "Review submitted with " + rating + " stars", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error submitting review", Toast.LENGTH_SHORT).show();
        }

        rating = 0;
        updateStarImages();
        reviewEditText.setText("");
    }

    private String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sdf.format(new Date());
    }
}

