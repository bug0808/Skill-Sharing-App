package com.example.mainactivity.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mainactivity.DatabaseHelper;
import com.example.mainactivity.R;
import com.example.mainactivity.adapters.ReviewAdapter;
import com.example.mainactivity.activities.ReviewActivity;
import com.example.mainactivity.classes.Review;

import java.util.List;

public class ProfileReviewsFragment extends Fragment {

    private RecyclerView recyclerView;
    private ReviewAdapter adapter;
    private List<Review> reviews;
    private TextView noReviewsTextView;
    private Button reviewButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_reviews, container, false);

        recyclerView = view.findViewById(R.id.reviewsRecyclerView);
        noReviewsTextView = view.findViewById(R.id.noReviewsTextView);
        reviewButton = view.findViewById(R.id.writeReviewButton);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        // Fetch reviews from the database
        DatabaseHelper db = new DatabaseHelper(getContext());
        int userId = getArguments().getInt("userId");
        reviews = db.getReviewsForUser(userId);

        // Check if the reviews list is empty
        if (reviews.isEmpty()) {
            noReviewsTextView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            noReviewsTextView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);

            adapter = new ReviewAdapter(getContext(), reviews);
            recyclerView.setAdapter(adapter);
        }

        reviewButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ReviewActivity.class);
            intent.putExtra("userId", userId);
            //intent.putExtra("userIdToReview", userIdToReview);
            startActivity(intent);
        });
    
        return view;
    }
}
