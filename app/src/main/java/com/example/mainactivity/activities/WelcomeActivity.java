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

        User user = db.getUserByPersonalId(100);
        Log.d("Welcome", "id: " + user.getPersonalId() + "first: " + user.getFirstName() + "last: " + user.getLastName() + "email: " + user.getEmail() + "phone: " + user.getPhone() +   "DOB: " + user.getDateOfBirth() + "Pass:" + user.getPassword());
        /**User sam = new User(db.generateUniquePersonalId(), "Seham", "Ahmed", "ahme5540@mylaurier.ca", "Test123!", "1111115092", "11/10/2003");
        db.addUser(sam);
        db.insertUserSkills(database, String.valueOf(sam.getPersonalId()), Arrays.asList("reading", "machine learning", "cooking", "android studio", "gaming"));

        User user1 = new User(db.generateUniquePersonalId(), "Aaron", "Langevin", "lang9150@mylaurier.ca", "Test123!", "8885852882", "06/11/2003");
        db.addUser(user1);
        db.insertUserSkills(database, String.valueOf(user1.getPersonalId()), Arrays.asList(("Java", "python", "cooking", "gaming", "driving", "skating"));

        User user2 = new User(db.generateUniquePersonalId(), "Test", "2", "test1@gmail.com", "Test123!", "1234567890", "10/11/2001");
        db.addUser(user2);
        db.insertUserSkills(database, String.valueOf(user2.getPersonalId()), Arrays.asList("trumpet", "baking", "gardening", "sql", "juggling"));

        User user3 = new User(db.generateUniquePersonalId(), "Test", "3", "test2@gmail.com", "Test123!", "1234567890", "10/11/2001");
        db.addUser(user3);
        db.insertUserSkills(database, String.valueOf(user3.getPersonalId()), Arrays.asList("biking", "gym", "hiking", "lifting", "gaming"));

        User user4 = new User(db.generateUniquePersonalId(), "Test", "4", "test3@gmail.com", "Test123!", "1234567890", "10/11/2001");
        db.addUser(user4);
        db.insertUserSkills(database, String.valueOf(user4.getPersonalId()), Arrays.asList("Java", "python", "figma", "machine learning", "singing"));

        User user5 = new User(db.generateUniquePersonalId(), "Test", "5", "test4@gmail.com", "Test123!", "1234567890", "10/11/2001");
        db.addUser(user5);
        db.insertUserSkills(database, String.valueOf(user5.getPersonalId()), Arrays.asList("Java", "hairstyling", "soccer", "basketball", "golf"));
**/

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
        db.logUserSkills(2);
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