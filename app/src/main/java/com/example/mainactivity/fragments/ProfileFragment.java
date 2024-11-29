package com.example.mainactivity.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mainactivity.DatabaseHelper;
import com.example.mainactivity.R;
import com.example.mainactivity.activities.UpdateDetailsActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ProfileFragment extends Fragment {

    private TextView infoTab, reviewsTab, usernameTextView;
    private FloatingActionButton fab;
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
        fab = view.findViewById(R.id.fab);

        infoTab.setOnClickListener(v -> showFragment(new ProfileInfoFragment()));
        reviewsTab.setOnClickListener(v -> showFragment(new ProfileReviewsFragment()));

        fab.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), UpdateDetailsActivity.class);
            intent.putExtra("userId", userId);
            startActivity(intent);
        });

        if (savedInstanceState == null) {
            showFragment(new ProfileInfoFragment());
        }
        return view;
    }

    private void fetchUserName(int userId) {
        DatabaseHelper db = new DatabaseHelper(getContext());
        String username = db.getUserNameByPersonalId(userId);
        if (username != null) {
            usernameTextView.setText(username);
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
