package com.example.mainactivity.fragments;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

import com.example.mainactivity.DatabaseHelper;
import com.example.mainactivity.R;
import com.example.mainactivity.activities.SkillPickActivity;
import com.example.mainactivity.activities.WelcomeActivity;
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

        firstNameEditText = view.findViewById(R.id.signup_firstName);
        lastNameEditText = view.findViewById(R.id.signup_lastName);
        phoneEditText = view.findViewById(R.id.signupPhone);
        dobEditText = view.findViewById(R.id.signup_dob);
        emailTextView = view.findViewById(R.id.details_email);
        signUpButton = view.findViewById(R.id.signupButton);

        Bundle args = getArguments();
        String email = null;
        String password = null;

        if (args != null && args.containsKey("email") && args.containsKey("password")) {
            email = args.getString("email", "");
            password = args.getString("password", "");
            emailTextView.setText(email);
        } else {
            Toast.makeText(requireContext(), "Error: Missing email or password.", Toast.LENGTH_SHORT).show();
            requireActivity().onBackPressed();
            return view;
        }

        TextView changeEmailTextView = view.findViewById(R.id.change_email_text);
        changeEmailTextView.setOnClickListener(v -> {
            if (isAdded()) {
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, new SignUpFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        dobEditText.setOnClickListener(v -> showDatePickerDialog());

        String finalEmail = email;
        String finalPassword = password;

        signUpButton.setOnClickListener(v -> {
            String firstName = firstNameEditText.getText().toString();
            String lastName = lastNameEditText.getText().toString();
            String phoneNumber = phoneEditText.getText().toString();
            String dob = dobEditText.getText().toString();

            if (TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName) ||
                    TextUtils.isEmpty(phoneNumber) || TextUtils.isEmpty(dob)) {
                Toast.makeText(requireContext(), "All fields are required.", Toast.LENGTH_SHORT).show();
                return;
            }

            DatabaseHelper dbHelper = new DatabaseHelper(getContext());
            int uniqueId = dbHelper.generateUniquePersonalId();

            User newUser = new User(firstName, lastName, finalEmail, finalPassword, phoneNumber, dob);
            newUser.setPersonalId(uniqueId);

            long userId = dbHelper.addUser(newUser);
            dbHelper.close();

            if (userId != -1) {
                Toast.makeText(requireContext(), "User registered successfully!", Toast.LENGTH_SHORT).show();
                Intent skillPickIntent = new Intent(requireActivity(), SkillPickActivity.class);
                    skillPickIntent.putExtra("USER_ID", newUser.getPersonalId());
                skillPickIntent.putExtra("IS_NEW", true);
                startActivity(skillPickIntent);
                requireActivity().finish();
            } else {
                Toast.makeText(requireContext(), "Error registering user.", Toast.LENGTH_SHORT).show();
                Intent welcomeIntent = new Intent(requireActivity(), WelcomeActivity.class);
                startActivity(welcomeIntent);
                requireActivity().finish();
            }
        });

        return view;
    }


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
