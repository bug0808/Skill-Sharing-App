package com.example.mainactivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mainactivity.DatabaseHelper;
import com.example.mainactivity.R;
import com.example.mainactivity.ReviewAdapter;
import com.example.mainactivity.classes.Review;

import java.util.List;

public class ProfileReviewsFragment extends Fragment {

    private RecyclerView recyclerView;
    private ReviewAdapter adapter;
    private List<Review> reviews;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_reviews, container, false);

        recyclerView = view.findViewById(R.id.reviewsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Fetch reviews from database
        DatabaseHelper db = new DatabaseHelper(getContext());
        int userId = getArguments().getInt("userId"); // Pass this from the previous fragment/activity
        reviews = db.getReviewsForUser(userId);

        // Set up adapter
        adapter = new ReviewAdapter(getContext(), reviews);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
