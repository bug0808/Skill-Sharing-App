package com.example.mainactivity.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.mainactivity.DatabaseHelper;
import com.example.mainactivity.R;
import com.example.mainactivity.activities.UpdateDetailsActivity;

import com.example.mainactivity.activities.WelcomeActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ProfileFragment extends Fragment {

    private TextView infoTab, reviewsTab, usernameTextView;
    private Button logout;
    private FloatingActionButton fab;
    private int userId, profUserId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        DatabaseHelper db = new DatabaseHelper(getContext());
        usernameTextView = view.findViewById(R.id.userName);

        if (getArguments() != null) {
            userId = getArguments().getInt("currUserId");
            profUserId = getArguments().getInt("profUserId");
            Log.d("ProfileFragment", "userId: " + userId + ", profUserId: " + profUserId);
            fetchUserName(profUserId);
        }

        infoTab = view.findViewById(R.id.tabInfo);
        reviewsTab = view.findViewById(R.id.tabReviews);
        fab = view.findViewById(R.id.fab);
        logout = view.findViewById(R.id.logoutButton);

        if (savedInstanceState == null) {
            ProfileInfoFragment profileInfoFragment = new ProfileInfoFragment();
            showFragment(profileInfoFragment);
            highlightSelectedOption(infoTab);
        }

        infoTab.setOnClickListener(v -> {
            ProfileInfoFragment profileInfoFragment = new ProfileInfoFragment();
            showFragment(profileInfoFragment);
            highlightSelectedOption(infoTab);
        });
        reviewsTab.setOnClickListener(v -> {
            ProfileReviewsFragment profileReviewsFragment = new ProfileReviewsFragment();
            showFragment(profileReviewsFragment);
            highlightSelectedOption(reviewsTab);
        });

        //check if the user viewing owns the profile
        if(userId == profUserId) {
            logout.setVisibility(View.VISIBLE);
            logout.setOnClickListener(v -> {
                db.setUserLoggedIn(String.valueOf(userId), false);
                Intent intent = new Intent(requireContext(), WelcomeActivity.class);
                startActivity(intent);
                getActivity().finish();
            });

            fab.setVisibility(View.VISIBLE);
            fab.setOnClickListener(v -> {
                Intent intent = new Intent(requireContext(), UpdateDetailsActivity.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
            });
        }

        if (savedInstanceState == null)
            showFragment(new ProfileInfoFragment());
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
        bundle.putInt("profUserId", profUserId);

        fragment.setArguments(bundle);

        requireActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }

    private void highlightSelectedOption(TextView selectedTextView) {
        infoTab.setSelected(false);
        reviewsTab.setSelected(false);
        infoTab.setTextColor(ContextCompat.getColor(getContext(), R.color.blueDark));
        reviewsTab.setTextColor(ContextCompat.getColor(getContext(), R.color.blueDark));

        selectedTextView.setSelected(true);
        selectedTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.blueLightest));
    }
}
