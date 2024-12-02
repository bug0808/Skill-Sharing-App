package com.example.mainactivity.adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mainactivity.R;
import com.example.mainactivity.classes.Events;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {
    private List<Events> eventsList;
    private OnEventDeleteListener deleteListener;

    public EventAdapter(List<Events> eventsList, OnEventDeleteListener deleteListener) {
        this.eventsList = eventsList;
        this.deleteListener = deleteListener;
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_event, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EventViewHolder holder, int position) {
        Events event = eventsList.get(position);
        holder.title.setText(event.getTitle());
        holder.description.setText(event.getDescription());
        holder.date.setText(event.getDate());
        holder.location.setText(event.getLocation());
        holder.userID.setText(event.getId());
        holder.deleteButton.setOnClickListener(v -> deleteListener.onEventDelete(Integer.parseInt(event.getId()), position));
    }

    @Override
    public int getItemCount() {
        return eventsList.size();
    }

    public void updateEvents(List<Events> newEvents) {
        this.eventsList.clear();
        this.eventsList.addAll(newEvents);
        notifyDataSetChanged();
    }

    public void removeEvent(int position) {
        eventsList.remove(position);
        notifyItemRemoved(position);
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder {
        TextView title, description, date, location, userID;
        Button deleteButton;
        public EventViewHolder(View itemView) {
            super(itemView);
            userID = itemView.findViewById(R.id.userID);
            title = itemView.findViewById(R.id.editEventTitle);
            description = itemView.findViewById(R.id.editEventDescription);
            date = itemView.findViewById(R.id.editEventDate);
            location = itemView.findViewById(R.id.editEventLocation);
            deleteButton = itemView.findViewById(R.id.deleteEventButton);
        }
    }


    public interface OnEventDeleteListener {
        void onEventDelete(int eventId, int position);
    }
}