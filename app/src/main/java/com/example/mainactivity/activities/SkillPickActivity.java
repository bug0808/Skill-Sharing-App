package com.example.mainactivity.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mainactivity.DatabaseHelper;
import com.example.mainactivity.R;
import com.example.mainactivity.adapters.SkillAdapter;
import com.example.mainactivity.classes.User;

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
        boolean IS_NEW = getIntent().getBooleanExtra("IS_NEW",false);

        SearchView searchView = findViewById(R.id.searchView);
        RecyclerView recyclerView = findViewById(R.id.skillsRecyclerView);

        List<String> skills = loadSkills(this);

        adapter = new SkillAdapter(skills.subList(0, Math.min(20, skills.size())));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

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

                adapter.updateSkills(filteredSkills);
                return true;
            }
        });

        DatabaseHelper db = new DatabaseHelper(this);

        Button updateSkillsButton = findViewById(R.id.updateSkills);

        updateSkillsButton.setOnClickListener(v -> {
            List<String> selectedSkills = adapter.getSelectedSkills();

            db.updateSkills(userId, selectedSkills);

            Intent main = new Intent(this, MainActivity.class);
            main.putExtra("userId", userId);
            startActivity(main);
        });

        db.close();
    }

    public List<String> loadSkills(Context context) {
        List<String> skills = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(context.getAssets().open("skills.csv")))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.replace("\"", "").trim();


                if (line.isEmpty()) {
                    continue;
                }
                if (!line.isEmpty() && Character.isLetter(line.charAt(0))) {
                    skills.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Collections.sort(skills);
        return skills;
    }


}
