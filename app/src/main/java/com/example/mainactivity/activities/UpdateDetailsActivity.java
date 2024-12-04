package com.example.mainactivity.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mainactivity.DatabaseHelper;
import com.example.mainactivity.R;
import com.example.mainactivity.classes.User;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class UpdateDetailsActivity extends AppCompatActivity {

    private EditText firstNameEditText, lastNameEditText, phoneEditText, emailEditText, dobEditText;
    private Button saveButton;
    private int userId;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_details);

        firstNameEditText = findViewById(R.id.firstName);
        lastNameEditText = findViewById(R.id.lastName);
        phoneEditText = findViewById(R.id.Phone);
        emailEditText = findViewById(R.id.Email);
        saveButton = findViewById(R.id.updateButton);
        dobEditText = findViewById(R.id.dob);

        databaseHelper = new DatabaseHelper(this);

        userId = getIntent().getIntExtra("userId", -1);

        if (userId != -1) {
            loadUserDetails(userId);
        } else {
            Toast.makeText(this, "Invalid user ID", Toast.LENGTH_SHORT).show();
            finish();
        }

        dobEditText.setOnClickListener(v -> showDatePickerDialog());
        saveButton.setOnClickListener(v -> saveDetails(userId));
    }

    private void loadUserDetails(int userId) {
        User user = databaseHelper.getUserByPersonalId(userId);

        if (user != null) {
            firstNameEditText.setText(user.getFirstName());
            lastNameEditText.setText(user.getLastName());
            phoneEditText.setText(user.getPhone());
            emailEditText.setText(user.getEmail());
            dobEditText.setText(user.getDateOfBirth());
        } else {
            Toast.makeText(this, "Failed to load user details", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void saveDetails(int userId) {
        String updatedFirstName = firstNameEditText.getText().toString().trim();
        String updatedLastName = lastNameEditText.getText().toString().trim();
        String updatedEmail = emailEditText.getText().toString().trim();
        String updatedPhone = phoneEditText.getText().toString().trim();
        String updatedDob = dobEditText.getText().toString().trim();

        User currentUser = databaseHelper.getUserByPersonalId(userId);

        if (currentUser == null) {
            Toast.makeText(this, "Error fetching user data", Toast.LENGTH_SHORT).show();
            return;
        }

        String finalFirstName = updatedFirstName.isEmpty() ? currentUser.getFirstName() : updatedFirstName;
        String finalLastName = updatedLastName.isEmpty() ? currentUser.getLastName() : updatedLastName;
        String finalEmail = updatedEmail.isEmpty() ? currentUser.getEmail() : updatedEmail;
        String finalPhone = updatedPhone.isEmpty() ? currentUser.getPhone() : updatedPhone;
        String finalDob = updatedDob.isEmpty() ? currentUser.getDateOfBirth() : updatedDob;

        boolean isUpdated = databaseHelper.updateUserDetails(userId, finalFirstName, finalLastName, finalEmail, finalPhone, finalDob);

        if (isUpdated) {
            Toast.makeText(this, "Details updated successfully", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Failed to update details", Toast.LENGTH_SHORT).show();
        }
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
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
