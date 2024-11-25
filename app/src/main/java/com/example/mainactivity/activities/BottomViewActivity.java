package com.example.mainactivity.activities;

import android.os.Bundle;
import android.util.Log;

import com.example.mainactivity.R;
import com.example.mainactivity.ui.dashboard.DashboardFragment;
import com.example.mainactivity.ui.home.FirstFragment;
import com.example.mainactivity.ui.notifications.NotificationsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.mainactivity.databinding.ActivityBottomViewBinding;

public class BottomViewActivity extends AppCompatActivity {

    private ActivityBottomViewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityBottomViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = binding.navView;
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_bottom_view);

        // Link the BottomNavigationView with the NavController
        NavigationUI.setupWithNavController(navView, navController);
    }



}