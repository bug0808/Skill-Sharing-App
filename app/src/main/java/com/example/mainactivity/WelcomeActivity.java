package com.example.mainactivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.mainactivity.databinding.ActivityWelcomeBinding;

public class WelcomeActivity extends AppCompatActivity {

    private LinearLayout bottomPanel;
    private Button reviewButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        reviewButton = findViewById(R.id.reviewbutton);
        reviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WelcomeActivity.this, ReviewActivity.class);
                Toast.makeText(WelcomeActivity.this, "Navigating to Review Page", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });


    }
}