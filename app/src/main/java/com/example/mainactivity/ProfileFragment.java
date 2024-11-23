package com.example.mainactivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {

    private TextView usernameTextView;
    private int userId; // The user ID passed to this fragment

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Initialize the TextView to display the username
        usernameTextView = view.findViewById(R.id.userName);

        // Get the user ID passed to this fragment
        if (getArguments() != null) {
            userId = getArguments().getInt("userId");

            // Fetch and display the username using the userId
            fetchUserName(userId);

            // Set up a click listener or logic to show the reviews fragment
            showReviewsFragment();
        }

        TextView reviewsTab = view.findViewById(R.id.tabReviews);
        reviewsTab.setOnClickListener(v -> showReviewsFragment());


        return view;
    }

    private void fetchUserName(int userId) {
        DatabaseHelper db = new DatabaseHelper(getContext());
        String username = db.getUserNameByPersonalId(userId); // Use your method to fetch the username
        if (username != null) {
            usernameTextView.setText(username); // Display the username
        }
    }

    private void showReviewsFragment() {
        // Create and pass the userId to ProfileReviewsFragment
        Fragment profileReviewsFragment = new ProfileReviewsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("userId", userId); // Pass the userId to the reviews fragment
        profileReviewsFragment.setArguments(bundle);

        // Replace the current fragment with ProfileReviewsFragment
        requireActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, profileReviewsFragment)
                .commit();
    }
}
