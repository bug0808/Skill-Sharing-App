package com.example.mainactivity.ui.guides;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mainactivity.Guide;
import com.example.mainactivity.GuideDetailFragment;
import com.example.mainactivity.R;

import java.util.List;

public class GuidesAdapter extends RecyclerView.Adapter<GuidesAdapter.GuideViewHolder> {
    private final List<Guide> guides;

    public GuidesAdapter(List<Guide> guides) {
        this.guides = guides;
    }

    @NonNull
    @Override
    public GuideViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_guide, parent, false);
        return new GuideViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return guides.size();
    }

    static class GuideViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, descriptionTextView;

        GuideViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.text_guide_title);
            descriptionTextView = itemView.findViewById(R.id.text_guide_description);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull GuideViewHolder holder, int position) {
        Guide guide = guides.get(position);
        holder.titleTextView.setText(guide.getTitle());
        holder.descriptionTextView.setText(guide.getDescription());

        holder.itemView.setOnClickListener(v -> {
            // Navigate to GuideDetailFragment
            Fragment fragment = GuideDetailFragment.newInstance(guide.getTitle(), guide.getContent());
            ((FragmentActivity) v.getContext()).getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_layout, fragment) // Adjust container ID
                    .addToBackStack(null)
                    .commit();
        });
    }


}
