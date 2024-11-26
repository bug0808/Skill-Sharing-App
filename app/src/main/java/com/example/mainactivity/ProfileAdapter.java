package com.example.mainactivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mainactivity.R;
import com.example.mainactivity.classes.Profile;
import com.example.mainactivity.classes.User;

import java.util.List;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.UserViewHolder> {

    private final List<Profile> profileList;

    public ProfileAdapter(List<Profile> userList) {
        this.profileList = userList;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_item, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        // Bind data to the ViewHolder
        Profile profile = profileList.get(position);
        holder.firstNameTextView.setText(profile.getFirstName());
        holder.lastNameTextView.setText(profile.getLastName());
    }

    @Override
    public int getItemCount() {
        return profileList.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        private final TextView firstNameTextView;
        private final TextView lastNameTextView;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            firstNameTextView = itemView.findViewById(R.id.text_first_name);
            lastNameTextView = itemView.findViewById(R.id.text_last_name);
        }
    }
}
