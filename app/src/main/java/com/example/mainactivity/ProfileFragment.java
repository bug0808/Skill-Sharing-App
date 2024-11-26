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
    private int userId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        usernameTextView = view.findViewById(R.id.userName);

        if (getArguments() != null) {
            userId = getArguments().getInt("userId");

            fetchUserName(userId);
        }

        infoTab = view.findViewById(R.id.tabInfo);
        reviewsTab = view.findViewById(R.id.tabReviews);

        infoTab.setOnClickListener(v -> showFragment(new ProfileInfoFragment()));
        reviewsTab.setOnClickListener(v -> showFragment(new ProfileReviewsFragment()));

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
        Bundle bundle = new Bundle();
        bundle.putInt("userId", userId);
        fragment.setArguments(bundle);

        requireActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }
}
