    package com.example.mainactivity.adapters;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.TextView;

    import androidx.annotation.NonNull;
    import androidx.recyclerview.widget.RecyclerView;

    import com.example.mainactivity.DatabaseHelper;
    import com.example.mainactivity.R;
    import com.example.mainactivity.classes.Profile;
    import com.example.mainactivity.classes.User;
    import com.example.mainactivity.classes.UserSkills;

    import java.util.List;

    public class ProfileSimAdapter extends RecyclerView.Adapter<ProfileSimAdapter.ProfileSimViewHolder> {

        private List<Profile> profiles;
        private OnItemClickListener onItemClickListener;

        // Interface for click events
        public interface OnItemClickListener {
            void onItemClick(Profile profile);
        }

        public ProfileSimAdapter(List<Profile> profiles) {
            this.profiles = profiles;
        }

        public void setOnItemClickListener(OnItemClickListener listener) {
            this.onItemClickListener = listener;
        }

        @NonNull
        @Override
        public ProfileSimViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_rec_item, parent, false);
            return new ProfileSimViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ProfileSimViewHolder holder, int position) {
            Profile profile = profiles.get(position);

            holder.profileFirstName.setText(profile.getFirstName());
            holder.profileLastName.setText(profile.getLastName());

            holder.itemView.setOnClickListener(v -> {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(profile);
                }
            });
        }

        @Override
        public int getItemCount() {
            return profiles.size();
        }

        public static class ProfileSimViewHolder extends RecyclerView.ViewHolder {
            TextView profileFirstName, profileLastName;

            public ProfileSimViewHolder(@NonNull View itemView) {
                super(itemView);
                profileFirstName = itemView.findViewById(R.id.text_first_name);
                profileLastName = itemView.findViewById(R.id.text_last_name);
            }
        }
    }
