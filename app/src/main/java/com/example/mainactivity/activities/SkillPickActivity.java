package com.example.mainactivity.activities;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mainactivity.DatabaseHelper;
import com.example.mainactivity.R;
import com.example.mainactivity.SkillAdapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public class SkillPickActivity extends AppCompatActivity {
    private SkillAdapter adapter;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skill_pick);

        int userId = getIntent().getIntExtra("USER_ID", -1);
        SearchView searchView = findViewById(R.id.searchView);
        RecyclerView recyclerView = findViewById(R.id.skillsRecyclerView);

        // Load skills
        List<String> skills = loadSkills(this);

        // Initialize adapter with first 20 skills
        adapter = new SkillAdapter(skills.subList(0, Math.min(20, skills.size())));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Search functionality
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<String> filteredSkills = skills.stream()
                        .filter(skill -> skill.toLowerCase().contains(newText.toLowerCase()))
                        .collect(Collectors.toList());

                // Update the existing adapter's data instead of creating a new adapter
                adapter.updateSkills(filteredSkills);
                return true;
            }
        });

        db = new DatabaseHelper(this);

        // Find the button by ID
        Button updateSkillsButton = findViewById(R.id.updateSkills);

        // Set the button's click listener
        updateSkillsButton.setOnClickListener(v -> {
            // Get the list of selected skills
            List<String> selectedSkills = adapter.getSelectedSkills();

            // Call your updateSkills function to store the selected skills
            db.updateSkills(userId, selectedSkills);
        });
    }

    public List<String> loadSkills(Context context) {
        List<String> skills = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(context.getAssets().open("skills.csv")))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Remove all double quotes and trim leading/trailing spaces
                line = line.replace("\"", "").trim();

                // Skip empty lines after trimming
                if (line.isEmpty()) {
                    continue;
                }

                // Check if the skill starts with a letter
                if (!line.isEmpty() && Character.isLetter(line.charAt(0))) {
                    // Add the skill to the list if it's not empty and starts with a letter
                    skills.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle exceptions
        }

        // Sort the skills alphabetically
        Collections.sort(skills);
        return skills;
    }


}
