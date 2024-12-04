package com.example.mainactivity.activities;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.mainactivity.fragments.DisplayEventsFragment;
import com.example.mainactivity.fragments.ProfileFragment;
import com.example.mainactivity.R;
import com.example.mainactivity.databinding.ActivityMainBinding;
import com.example.mainactivity.ui.guides.GuidesFragment;
import com.example.mainactivity.ui.home.FirstFragment;
import com.example.mainactivity.ui.notifications.NotificationsFragment;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private AppBarConfiguration appBarConfiguration;
    private int userId;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        userId = getIntent().getIntExtra("userId", -1);

        Bundle bundle = new Bundle();
        bundle.putInt("currUserId", userId);
        bundle.putInt("profUserId", userId);
        FirstFragment firstFragment = new FirstFragment();
        firstFragment.setArguments(bundle);
        replaceFragment(firstFragment);

        binding.bottomNavigationView.setBackgroundColor(ContextCompat.getColor(this, R.color.blueLight));

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    firstFragment.setArguments(bundle);
                    replaceFragment(firstFragment);
                    break;
                case R.id.navigation_dashboard:
                    GuidesFragment guidesFragment = new GuidesFragment();
                    guidesFragment.setArguments(bundle);
                    replaceFragment(guidesFragment);
                    break;
                case R.id.navigation_notifications:
                    DisplayEventsFragment displayEventsFragment = new DisplayEventsFragment();
                    displayEventsFragment.setArguments(bundle);
                    replaceFragment(displayEventsFragment);
                    break;
                case R.id.navigation_profile:
                    ProfileFragment profileFragment = new ProfileFragment();
                    profileFragment.setArguments(bundle);
                    replaceFragment(profileFragment);
                    break;
            }
            return true;
        });
    }
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
//    @Override
//    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        return NavigationUI.navigateUp(navController, appBarConfiguration)
//                || super.onSupportNavigateUp();
//    }
}