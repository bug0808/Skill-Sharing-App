package com.example.mainactivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ReviewActivity extends AppCompatActivity {

    private ImageView star1, star2, star3, star4, star5;
    private EditText reviewEditText;
    private Button submitButton, cancelButton;
    private int rating = 0; // This will store the selected rating out of 5

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reviewlayout);

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
            switch (view.getId()) {
                case R.id.star1: rating = 1; break;
                case R.id.star2: rating = 2; break;
                case R.id.star3: rating = 3; break;
                case R.id.star4: rating = 4; break;
                case R.id.star5: rating = 5; break;
            }
            updateStarImages();
        };

        star1.setOnClickListener(starClickListener);
        star2.setOnClickListener(starClickListener);
        star3.setOnClickListener(starClickListener);
        star4.setOnClickListener(starClickListener);
        star5.setOnClickListener(starClickListener);

        //set submit button to get review
        submitButton.setOnClickListener(view -> submitReview());
        //set up cancel button
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
        if (reviewText.isEmpty()){
          Toast.makeText(this, "Please write a review", Toast.LENGTH_SHORT).show();
            return;
        }

        // Here you would save the review to the database or backend
        // For now, show a confirmation message
        Toast.makeText(this, "Review submitted with " + rating + " stars", Toast.LENGTH_SHORT).show();

        // Reset review input fields
        rating = 0;
        updateStarImages();
        reviewEditText.setText("");
    }}
