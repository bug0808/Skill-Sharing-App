package com.example.mainactivity.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.mainactivity.ProfileFragment;
import com.example.mainactivity.R;

public class ProfileActivity extends AppCompatActivity {

    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        if (getIntent() != null && getIntent().hasExtra("userId")) {
            userId = getIntent().getIntExtra("userId", -1);
        }

        if (savedInstanceState == null) {

            ProfileFragment profileFragment = new ProfileFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("userId", userId);
            profileFragment.setArguments(bundle);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, profileFragment); // Replace with the ID of your container
            transaction.commit();
        }
    }
}
