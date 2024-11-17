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
    private int userId; // The user ID passed from ProfileFragment

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Initialize the TextView to display the username
        usernameTextView = view.findViewById(R.id.userName);

        // Get the user ID passed from the ProfileFragment
        if (getArguments() != null) {
            userId = getArguments().getInt("userId");
            // Now use the userId to fetch the username from the database
            fetchUserName(userId);
        }
        return view;
    }

    private void fetchUserName(int userId) {
        DatabaseHelper db = new DatabaseHelper(getContext());
        String username = db.getUserNameByPersonalId(userId); // Use your method to get the name by userId
        if (username != null) {
            usernameTextView.setText(username); // Display the username in the TextView
        }
    }
}

