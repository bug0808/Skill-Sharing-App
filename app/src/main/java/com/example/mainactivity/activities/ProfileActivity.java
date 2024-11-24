package com.example.mainactivity.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.mainactivity.ProfileFragment;
import com.example.mainactivity.R;

public class ProfileActivity extends AppCompatActivity {

    private int userId; // The user ID to be passed to the fragment

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile); // Make sure this layout has a container for the fragment

        // Get the userId passed from the previous activity (e.g., via intent or bundle)
        if (getIntent() != null && getIntent().hasExtra("userId")) {
            userId = getIntent().getIntExtra("userId", -1); // Replace -1 with a default value if necessary
        }

        // Check if the fragment is not already loaded to avoid reloading on configuration changes
        if (savedInstanceState == null) {
            // Create an instance of ProfileFragment and pass the userId to it
            ProfileFragment profileFragment = new ProfileFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("userId", userId); // Pass the userId to the fragment
            profileFragment.setArguments(bundle);

            // Load the fragment dynamically using FragmentTransaction
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, profileFragment); // Replace with the ID of your container
            transaction.commit();
        }
    }
}
