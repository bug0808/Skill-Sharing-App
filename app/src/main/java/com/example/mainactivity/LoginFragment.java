package com.example.mainactivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mainactivity.activities.HomeActivity;
import com.example.mainactivity.activities.MainActivity;
import com.example.mainactivity.activities.ProfileActivity;

public class LoginFragment extends Fragment {

    private EditText loginName;
    private EditText password;
    private Button loginButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        // Initialize UI components
        loginName = view.findViewById(R.id.login_name);
        password = view.findViewById(R.id.password);
        loginButton = view.findViewById(R.id.loginButton);

        // Set click listener for login button
        loginButton.setOnClickListener(v -> {
            String email = loginName.getText().toString().trim();
            String pass = password.getText().toString().trim();

            // Validate the inputs before attempting login
            if (validateInput(email, pass)) {
                // Check credentials in the database
                DatabaseHelper db = new DatabaseHelper(getActivity());
                int userId = db.validateLogin(email, pass);  // Get the user ID

                if (userId != -1) {
                    // Login successful, proceed to the home page
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserSession", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("userId", userId);  // Store the userId when logged in
                    editor.apply();

                    // Start the HomeActivity
                    Intent intent = new Intent(getActivity(), ProfileActivity.class); // Replace with your home activity
                    startActivity(intent);
                    getActivity().finish(); // Finish the login fragment or activity so user can't go back
                } else {
                    // Invalid login credentials
                    Toast.makeText(getActivity(), "Invalid email or password", Toast.LENGTH_SHORT).show();
                }
                db.close();
            }
        });

        return view;
    }

    // Method to validate the input fields
    private boolean validateInput(String email, String password) {
        if (email.isEmpty()) {
            loginName.setError("Email is required");
            return false;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            loginName.setError("Invalid email address");
            return false;
        }
        if (password.isEmpty()) {
            this.password.setError("Password is required");
            return false;
        }
        return true;
    }
}
