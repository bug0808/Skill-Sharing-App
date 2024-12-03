package com.example.mainactivity.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.example.mainactivity.DatabaseHelper;
import com.example.mainactivity.R;
import com.example.mainactivity.fragments.DisplayEventsFragment;

public class EventFragment extends DialogFragment {

    protected EditText editEventTitle, editEventDescription, editEventDate, editEventLocation;
    protected Button saveEventButton;
    protected String userId;
    private DatabaseHelper eventsDatabaseHelper;

    public static EventFragment newInstance(String userId) {
        EventFragment fragment = new EventFragment();
        Bundle args = new Bundle();
        args.putString("userId", userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_event, container, false);

        if (getArguments() != null) {
            userId = getArguments().getString("userId");
        }

        editEventTitle = rootView.findViewById(R.id.editEventTitle);
        editEventDescription = rootView.findViewById(R.id.editEventDescription);
        editEventDate = rootView.findViewById(R.id.editEventDate);
        editEventLocation = rootView.findViewById(R.id.editEventLocation);
        saveEventButton = rootView.findViewById(R.id.saveEventButton);

        eventsDatabaseHelper = new DatabaseHelper(getContext());

        saveEventButton.setOnClickListener(v -> {
            String title = editEventTitle.getText().toString().trim();
            String description = editEventDescription.getText().toString().trim();
            String date = editEventDate.getText().toString().trim();
            String location = editEventLocation.getText().toString().trim();

            if (title.isEmpty() || description.isEmpty() || date.isEmpty() || location.isEmpty()) {
                Toast.makeText(getContext(), "All fields are required!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Insert event into the database
            eventsDatabaseHelper.insertEvent(title, description, date, location);

            Toast.makeText(getContext(), "Event saved!", Toast.LENGTH_SHORT).show();

            // Disable fields and notify parent fragment to update
            disableEditFields();
            notifyParentFragment();
            dismiss();
        });

        return rootView;
    }

    private void disableEditFields() {
        Log.d("EventFragment", "Disabling fields and hiding save button...");
        editEventTitle.setEnabled(false);
        editEventDescription.setEnabled(false);
        editEventDate.setEnabled(false);
        editEventLocation.setEnabled(false);
        saveEventButton.setVisibility(View.GONE);
        Log.d("EventFragment", "Fields disabled and save button hidden.");
    }

    private void notifyParentFragment() {
        if (getParentFragment() instanceof DisplayEventsFragment) {
            ((DisplayEventsFragment) getParentFragment()).fetchEventsFromDatabase();
        } else {
            Log.e("EventFragment", "Parent fragment is not DisplayEventsFragment!");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getDialog() != null) {
            getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }
}
