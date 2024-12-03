package com.example.mainactivity.activities;

import android.content.Intent;
import android.content.SharedPreferences;
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

        int user = db.getLoggedInUserId();
        Log.d("Welcome", "logged in user: " + user);

        if (user != -1) {
            Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
            intent.putExtra("userId", user);
            startActivity(intent);
            finish();
        }

        //User user = db.getUserByPersonalId(100);
        //Log.d("Welcome", "id: " + user.getPersonalId() + "first: " + user.getFirstName() + "last: " + user.getLastName() + "email: " + user.getEmail() + "phone: " + user.getPhone() +   "DOB: " + user.getDateOfBirth() + "Pass:" + user.getPassword());
        /**User sam = new User(db.generateUniquePersonalId(), "Seham", "Ahmed", "ahme5540@mylaurier.ca", "Test123!", "1111115092", "11/10/2003");
        db.addUser(sam);
        db.insertUserSkills(database, String.valueOf(sam.getPersonalId()), Arrays.asList("reading", "machine learning", "cooking", "android studio", "gaming"));

         User areesha = new User(db.generateUniquePersonalId(), "Areesha", "Yahya", "areeshayahya9@gmail.com", "Test123!", "1238904567", "4/18/2003");
         db.addUser(areesha);
         db.insertUserSkills(database, String.valueOf(areesha.getPersonalId()), Arrays.asList("SQL", "HTML", "Javascript", "C"));

        User eli = new User(db.generateUniquePersonalId(),  "Elijah", "Wilts", "ejwilts.ew@gmail.com", "Passw0rd#", "4561237890", "08/08/2003");
        db.addUser(eli);
        db.insertUserSkills(database, String.valueOf(eli.getPersonalId()), Arrays.asList("Java","python","Guitar","Woodworking"));

        User aaron = new User(db.generateUniquePersonalId(), "Aaron", "Langevin", "lang9150@mylaurier.ca", "Test123!", "888-585-2882", "06/11/2003");
        db.addUser(aaron);
        db.insertUserSkills(database, String.valueOf(aaron.getPersonalId()), Arrays.asList("Java", "python", "cooking", "big data", "gaming", "driving", "skating"));

        User liza = new User(db.generateUniquePersonalId(), "Liza", "Ahmed", "liza@gmail.com", "Test123!", "1234567890", "12/30/2008");
        db.addUser(liza);
        db.insertUserSkills(database, String.valueOf(liza.getPersonalId()), Arrays.asList("Biology", "Medicine", "gaming", "history", "health"));

        User rae = new User(db.generateUniquePersonalId(), "Raaya", "Ahmed", "raaya@gmail.com", "Test123!", "1234567890", "03/17/2007");
        db.addUser(rae);
        db.insertUserSkills(database, String.valueOf(rae.getPersonalId()), Arrays.asList("architecture", "art", "drawing", "gaming", "music"));

        User miko = new User(db.generateUniquePersonalId(), "Mekael", "Ahmed", "miko@gmail.com", "Test123!", "1234567890", "08/19/2013");
        db.addUser(miko);
        db.insertUserSkills(database, String.valueOf(miko.getPersonalId()), Arrays.asList("tornados", "biking", "airplane", "gaming", "music"));

        User yunis = new User(db.generateUniquePersonalId(), "Abdul-Rahman", "Mawlood-Yunis", "470@gmail.com", "Test123!", "1234567890", "08/19/1970");
        db.addUser(yunis);
        db.insertUserSkills(database, String.valueOf(yunis.getPersonalId()), Arrays.asList("Java", "Android Studio", "HTML", "Firebase", "Database", "Computer Science"));
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
        //db.logUserSkills(2);
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