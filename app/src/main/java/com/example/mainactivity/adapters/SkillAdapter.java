package com.example.mainactivity.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SkillAdapter extends RecyclerView.Adapter<SkillAdapter.SkillViewHolder> {
    private List<String> skills; // Current displayed skills
    private Set<String> selectedSkills; // Persistent set to track selected skills

    public SkillAdapter(List<String> skills) {
        this.skills = skills;
        this.selectedSkills = new HashSet<>(); // Initialize selected skills as a Set to avoid duplicates
    }

    public List<String> getSelectedSkills() {
        return new ArrayList<>(selectedSkills); // Return selected skills as a list
    }

    @NonNull
    @Override
    public SkillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1, parent, false);
        return new SkillViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SkillViewHolder holder, int position) {
        String skill = skills.get(position);
        holder.skillTextView.setText(skill);

        // Highlight the item if it's selected
        if (selectedSkills.contains(skill)) {
            holder.itemView.setBackgroundColor(Color.LTGRAY); // Change background to show it's selected
        } else {
            holder.itemView.setBackgroundColor(Color.TRANSPARENT); // Default background
        }

        // Toggle selection on item click
        holder.itemView.setOnClickListener(v -> {
            if (selectedSkills.contains(skill)) {
                selectedSkills.remove(skill); // Deselect the skill if it was selected
                holder.itemView.setBackgroundColor(Color.TRANSPARENT); // Reset background
            } else {
                selectedSkills.add(skill); // Select the skill if it was not selected
                holder.itemView.setBackgroundColor(Color.LTGRAY); // Change background to indicate selection
            }
        });
    }

    @Override
    public int getItemCount() {
        return skills.size();
    }

    // ViewHolder class for skills
    static class SkillViewHolder extends RecyclerView.ViewHolder {
        TextView skillTextView;

        SkillViewHolder(View itemView) {
            super(itemView);
            skillTextView = itemView.findViewById(android.R.id.text1);
        }
    }

    // Update the displayed skills without affecting the selected skills
    public void updateSkills(List<String> newSkills) {
        this.skills = newSkills; // Update the skills list
        notifyDataSetChanged(); // Refresh the RecyclerView with the new data
    }
}
