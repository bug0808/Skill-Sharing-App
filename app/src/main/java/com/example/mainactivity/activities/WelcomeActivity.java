package com.example.mainactivity.activities;

import android.content.Intent;
import android.os.Bundle;

import com.example.mainactivity.DatabaseHelper;
import com.example.mainactivity.R;
import com.example.mainactivity.classes.User;

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

        //User user = new User("Seham", "Ahmed", "ahme5540@mylaurier.ca", "Test123!", "1234567890", "11/10/2003");
        //int id = db.generateUniquePersonalId();
        //user.setPersonalId(id);
        //db.addUser(user);

        db.deleteUser(0);
        db.logAllUsers();
        db.logUserSkills(903618);
        //db.updateSkills(1,skills);
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