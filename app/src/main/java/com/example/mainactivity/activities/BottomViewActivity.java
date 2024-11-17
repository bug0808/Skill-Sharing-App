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

import com.example.mainactivity.databinding.ActivityBottomViewBinding;

public class BottomViewActivity extends AppCompatActivity {

    private ActivityBottomViewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityBottomViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_bottom_view);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//        NavigationUI.setupWithNavController(binding.navView, navController);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new FirstFragment())
                    .commit();
        }

        navView.setOnNavigationItemSelectedListener(navListener);

    }

    private final BottomNavigationView.OnNavigationItemSelectedListener navListener = item ->{

        Fragment selectedFragment;
        int itemId = item.getItemId();

        switch(itemId){
            case R.id.navigation_home:
                selectedFragment = new FirstFragment();
                Log.d("bottom view","home clicked");
                break;
            case R.id.navigation_dashboard:
                selectedFragment = new DashboardFragment();
                Log.d("bottom view","dashboard clicked");
                break;
            case R.id.navigation_notifications:
                selectedFragment = new NotificationsFragment();
                break;
            default:
                return false;
        }
        if (selectedFragment != null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            return true;
        }
        return false;
    };

    public void switchToHome() {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragment_container, new FirstFragment()).commit();
    }

    public void switchToDashboard() {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragment_container, new DashboardFragment()).commit();
    }

}