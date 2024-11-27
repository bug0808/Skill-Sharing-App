package com.example.mainactivity.activities;

import android.content.Intent;
import android.os.Bundle;

import com.example.mainactivity.DatabaseHelper;
import com.example.mainactivity.R;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class WelcomeActivity extends AppCompatActivity {

    private LinearLayout bottomPanel;
    private Button emailConnectButton, facebookConnectButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        List<String> skills = new ArrayList<>();
        skills.add("Java");
        skills.add("Python");

        DatabaseHelper db = new DatabaseHelper(this);
        //db.deleteUser(0);
        db.logAllUsers();
        db.logUserSkills(1);
        db.updateSkills(1,skills);
        db.close();

        emailConnectButton = findViewById(R.id.emailConnect);
        emailConnectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}