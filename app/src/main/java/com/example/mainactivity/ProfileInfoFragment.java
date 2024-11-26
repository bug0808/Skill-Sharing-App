package com.example.mainactivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

import com.example.mainactivity.activities.SkillPickActivity;

import java.util.ArrayList;
import java.util.List;

public class ProfileInfoFragment extends Fragment {

    private LinearLayout skillsLayout;
    private Button editSkillsButton;
    private List<String> userSkills = new ArrayList<>();
    private int userId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userId = getArguments().getInt("userId", -1);  // Default to -1 if not found
        }
        if (userId != -1) {
            userSkills = getUserSkills(getContext(), userId);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_info, container, false);

        GridLayout skillsLayout = view.findViewById(R.id.skills_grid);
        editSkillsButton = view.findViewById(R.id.updateSkillsButton);

        if (userSkills != null) {
            for (String skill : userSkills) {
                TextView skillTextView = new TextView(getContext());
                skillTextView.setText(skill);
                skillTextView.setPadding(16, 8, 16, 8);
                skillTextView.setBackgroundResource(R.drawable.bubble_textview);
                skillTextView.setTextSize(16);
                skillsLayout.addView(skillTextView);
            }
        }

        editSkillsButton.setOnClickListener(v -> {
            Intent skillPickIntent = new Intent(requireActivity(), SkillPickActivity.class);
            skillPickIntent.putExtra("USER_ID", userId);
            skillPickIntent.putExtra("IS_NEW", false);
            startActivity(skillPickIntent);
        });
        return view;
    }

    public List<String> getUserSkills(Context context, int userId) {
        List<String> skills = new ArrayList<>();
        if (userId != -1) {  // Ensure that userId is valid
            DatabaseHelper dbHelper = new DatabaseHelper(context);
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            skills = dbHelper.getUserSkills(db, userId);
            db.close();
        }
        return skills;
    }
}
