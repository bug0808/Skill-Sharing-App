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

import com.example.mainactivity.R;
import com.example.mainactivity.activities.DisplayEvents;

public class EventFragment extends DialogFragment {

    protected EditText editEventTitle, editEventDescription, editEventDate, editEventLocation;
    protected Button saveEventButton;
    protected String userId;
    private EventsDatabaseHelper eventsDatabaseHelper;
    private DisplayEvents parentActivity;

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

        parentActivity = (DisplayEvents) getActivity();

        editEventTitle = rootView.findViewById(R.id.editEventTitle);
        editEventDescription = rootView.findViewById(R.id.editEventDescription);
        editEventDate = rootView.findViewById(R.id.editEventDate);
        editEventLocation = rootView.findViewById(R.id.editEventLocation);
        saveEventButton = rootView.findViewById(R.id.saveEventButton);

        eventsDatabaseHelper = new EventsDatabaseHelper(getContext());

        // Set the behavior of the Save button
        saveEventButton.setOnClickListener(v -> {
            // Get event details from the EditTexts
            String title = editEventTitle.getText().toString();
            String description = editEventDescription.getText().toString();
            String date = editEventDate.getText().toString();
            String location = editEventLocation.getText().toString();

            // Save the event to the database
            eventsDatabaseHelper.insertEvent(title, description, date, location);

            // Show a confirmation Toast
            Toast.makeText(getContext(), "Event saved!", Toast.LENGTH_SHORT).show();

            // Disable the EditText fields and hide the save button after saving
            disableEditFields();

            // Optionally, update the RecyclerView in the parent activity
            parentActivity.fetchEventsFromDatabase();

            // Close the dialog after saving
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

    @Override
    public void onResume() {
        super.onResume();
        if (getDialog() != null) {
            getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }
}