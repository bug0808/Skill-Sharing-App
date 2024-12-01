package com.example.mainactivity.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mainactivity.EventAdapter;
import com.example.mainactivity.EventFragment;
import com.example.mainactivity.R;
import com.example.mainactivity.classes.Events;
import com.example.mainactivity.EventsDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class DisplayEvents extends AppCompatActivity implements EventAdapter.OnEventDeleteListener {  // Implement OnEventDeleteListener

    private EventAdapter eventAdapter;
    private EventsDatabaseHelper eventsDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_events);

        eventsDatabaseHelper = new EventsDatabaseHelper(this);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        eventAdapter = new EventAdapter(new ArrayList<>(), this);
        recyclerView.setAdapter(eventAdapter);

        Button eventCreateButton = findViewById(R.id.EventCreate);
        eventCreateButton.setOnClickListener(v -> {

            EventFragment eventFragmentInstance = EventFragment.newInstance("user123");
            eventFragmentInstance.show(getSupportFragmentManager(), "EventFragment");
        });
        fetchEventsFromDatabase();
    }

    public void fetchEventsFromDatabase() {
        if (eventsDatabaseHelper != null) {
            List<Events> eventsList = eventsDatabaseHelper.getAllEvents();
            if (eventsList != null) {
                eventAdapter.updateEvents(eventsList);
            }
        }
    }


    @Override
    public void onEventDelete(int eventId, int position) {
        if (eventsDatabaseHelper != null) {
            boolean deleted = eventsDatabaseHelper.deleteEvent(eventId);
            if (deleted) {
                eventAdapter.removeEvent(position);
            } else {
                Log.e("DisplayEvents", "Failed to delete event with ID " + eventId);
            }
        }
    }
}