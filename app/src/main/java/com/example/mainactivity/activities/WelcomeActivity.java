package com.example.mainactivity.activities;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.example.mainactivity.DatabaseHelper;
import com.example.mainactivity.R;
import com.example.mainactivity.SimilarityUtil;
import com.example.mainactivity.classes.User;
import com.example.mainactivity.classes.UserSkills;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WelcomeActivity extends AppCompatActivity {

    private LinearLayout bottomPanel;
    private Button emailConnectButton, facebookConnectButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);


        DatabaseHelper db = new DatabaseHelper(this);
        SQLiteDatabase database = db.getReadableDatabase();


        /** User user = db.getUserByPersonalId(555812);
        Log.e("Welc", user.getPersonalId() + user.getFirstName() + user.getLastName());
        List<UserSkills> users = new ArrayList<>();

        // Add users and their skills
        UserSkills user1 = new UserSkills(1, Arrays.asList("Java", "Python", "Machine Learning"));
        UserSkills user2 = new UserSkills(2, Arrays.asList("Python", "Data Science"));
        UserSkills user3 = new UserSkills(3, Arrays.asList("Java", "Kotlin", "Android"));
        UserSkills user4 = new UserSkills(4, Arrays.asList("Machine Learning", "Cooking", "Baking"));
        UserSkills user5 = new UserSkills(5, Arrays.asList("Baking", "Gaming", "Android"));
        UserSkills user6 = new UserSkills(6, Arrays.asList("Python", "Machine Learning", "Android"));

        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
        users.add(user5);
        users.add(user6);

        User test = db.getUserByPersonalId(555812);
        UserSkills testU = new UserSkills(test.getPersonalId(), db.getUserSkills(database, test.getPersonalId()));

        users.add(testU);

        // Update similarity scores for all users
        SimilarityUtil.updateSimilarityScores(users);
        // Find top 2 matches for user1
        List<UserSkills> topMatches = SimilarityUtil.getTopMatches(testU, users, 4);

        System.out.println("Top matches for ID: " + test.getPersonalId());
        for (UserSkills match : topMatches) {
            Log.e("Welcome Activity", "User ID: " + match.getUserId() + ", Similarity: " + match.getSimilarityScore());
        }
        **/

        db.logAllUsers();
        db.logUserSkills(555812);
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