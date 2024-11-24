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

    private TextView infoTab, reviewsTab, usernameTextView;
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
        }

        // Initialize tab views
        infoTab = view.findViewById(R.id.tabInfo);
        reviewsTab = view.findViewById(R.id.tabReviews);

        // Set click listeners for tabs
        infoTab.setOnClickListener(v -> showFragment(new ProfileInfoFragment()));
        reviewsTab.setOnClickListener(v -> showFragment(new ProfileReviewsFragment()));


        // Optionally, set the initial fragment
        if (savedInstanceState == null) {
            showFragment(new ProfileInfoFragment());
        }

        return view;
    }

    private void fetchUserName(int userId) {
        DatabaseHelper db = new DatabaseHelper(getContext());
        String username = db.getUserNameByPersonalId(userId); // Use your method to fetch the username
        if (username != null) {
            usernameTextView.setText(username); // Display the username
        }
    }

    private void showFragment(Fragment fragment) {
        // Create and pass the userId to the target fragment
        Bundle bundle = new Bundle();
        bundle.putInt("userId", userId);
        fragment.setArguments(bundle);

        // Replace the current fragment with the target fragment
        requireActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }
}
