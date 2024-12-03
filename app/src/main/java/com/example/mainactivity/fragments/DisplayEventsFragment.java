package com.example.mainactivity.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mainactivity.R;
import com.example.mainactivity.adapters.EventAdapter;
import com.example.mainactivity.classes.Events;
import com.example.mainactivity.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class DisplayEventsFragment extends Fragment implements EventAdapter.OnEventDeleteListener {

    private EventAdapter eventAdapter;
    private DatabaseHelper eventsDatabaseHelper;
    private int userId;

    public DisplayEventsFragment() {
    }

    public static DisplayEventsFragment newInstance() {
        return new DisplayEventsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_display_events, container, false);

        userId = getArguments().getInt("currUserId", -1);

        eventsDatabaseHelper = new DatabaseHelper(requireContext());
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        eventAdapter = new EventAdapter(new ArrayList<>(), this);
        recyclerView.setAdapter(eventAdapter);

        Button eventCreateButton = view.findViewById(R.id.EventCreate);
        eventCreateButton.setOnClickListener(v -> {
            EventFragment eventFragmentInstance = EventFragment.newInstance(String.valueOf(userId));
            eventFragmentInstance.show(getParentFragmentManager(), "EventFragment");
        });

        fetchEventsFromDatabase();
        return view;
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
                Log.e("DisplayEventsFragment", "Failed to delete event with ID " + eventId);
            }
        }
    }
}
