package com.example.mainactivity.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mainactivity.R;
import com.example.mainactivity.classes.Guide;

import java.util.List;

public class GuideAdapter extends RecyclerView.Adapter<GuideAdapter.GuideViewHolder> {
    private final List<Guide> guides;

    public GuideAdapter(List<Guide> guides) {
        this.guides = guides;
    }

    @NonNull
    @Override
    public GuideViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_guide, parent, false);
        return new GuideViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GuideViewHolder holder, int position) {
        Guide guide = guides.get(position);
        holder.titleTextView.setText(guide.getTitle());
        holder.descriptionTextView.setText(guide.getDescription());
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
}