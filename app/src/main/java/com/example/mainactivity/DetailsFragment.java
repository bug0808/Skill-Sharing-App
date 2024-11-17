package com.example.mainactivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.mainactivity.classes.User;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DetailsFragment extends Fragment {

    private TextView emailTextView;
    private EditText dobEditText;
    private EditText phoneEditText;
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText passwordEditText;
    private Button signUpButton;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        // Initialize UI components
        firstNameEditText = view.findViewById(R.id.signup_firstName);
        lastNameEditText = view.findViewById(R.id.signup_lastName);
        phoneEditText = view.findViewById(R.id.signupPhone);
        dobEditText = view.findViewById(R.id.signup_dob);
        emailTextView = view.findViewById(R.id.details_email);  // Initialize emailTextView
        signUpButton = view.findViewById(R.id.signupButton);  // Initialize signUpButton

        // Retrieve email and password from arguments (from SignUpFragment)
        final String email = getArguments() != null ? getArguments().getString("email") : "";
        final String password = getArguments() != null ? getArguments().getString("password") : "";

        // Set email in the TextView
        emailTextView.setText(email);  // Set the email entered in SignUpFragment

        // Set the "Change Email?" text clickable
        TextView changeEmailTextView = view.findViewById(R.id.change_email_text);
        changeEmailTextView.setOnClickListener(v -> {
            // Navigate back to the SignUpFragment
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, new SignUpFragment())
                    .addToBackStack(null)  // Optional: Add back stack if you want to navigate back
                    .commit();
        });

        // Date of Birth EditText click listener to show date picker
        dobEditText.setOnClickListener(v -> showDatePickerDialog());

        // SignUp button click listener
        signUpButton.setOnClickListener(v -> {
            String firstName = firstNameEditText.getText().toString();
            String lastName = lastNameEditText.getText().toString();
            String phoneNumber = phoneEditText.getText().toString();
            String dob = dobEditText.getText().toString();  // Get password entered


            User newUser = new User(firstName, lastName, email, password, phoneNumber, dob);

            DatabaseHelper dbHelper = new DatabaseHelper(getContext());
            long userId = dbHelper.addUser(newUser);

            // Optionally, handle success (e.g., navigate to the next screen or show a confirmation)
            if (userId != -1) {
                // User was inserted successfully
                Toast.makeText(getContext(), "User registered successfully!", Toast.LENGTH_SHORT).show();
            } else {
                // Error occurred
                Toast.makeText(getContext(), "Error registering user.", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    // Method to show DatePicker Dialog
    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                (DatePicker view, int year, int month, int dayOfMonth) -> {
                    calendar.set(year, month, dayOfMonth);
                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
                    String formattedDate = sdf.format(calendar.getTime());
                    dobEditText.setText(formattedDate);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }
}
