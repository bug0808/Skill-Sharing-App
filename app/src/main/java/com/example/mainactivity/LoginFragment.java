package com.example.mainactivity;

import android.content.Intent;
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

public class LoginFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        // Initialize UI components
        EditText loginName = view.findViewById(R.id.login_name);
        EditText password = view.findViewById(R.id.password);
        Button loginButton = view.findViewById(R.id.loginButton);

        // Set click listener for login button
        loginButton.setOnClickListener(v -> {
            String email = loginName.getText().toString();
            String pass = password.getText().toString();

            // Add your login logic here
                    // Validate input fields
                    if (validateInput(email, pass)) {
                        // Check login credentials (mock logic)
                        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
                        if (dbHelper.authenticateUser(email, pass)) {
                            // Navigate to MainActivity on successful login
                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            intent.putExtra("email", email); // Pass user email to MainActivity
                            startActivity(intent);

                            // Finish the current activity to prevent back navigation
                            requireActivity().finish();
                        } else {
                            // Show an error if login fails
                            Toast.makeText(getActivity(), "Invalid email or password", Toast.LENGTH_SHORT).show();
                        }
                        // For example, show a Toast message for testing
                        Toast.makeText(getActivity(), "Login clicked", Toast.LENGTH_SHORT).show();
                    }
        });
        return view;
    }

    // Method to validate input fields
    private boolean validateInput(String email, String pass) {
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(getActivity(), "Please enter a valid email", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (pass.isEmpty() || pass.length() < 6) {
            Toast.makeText(getActivity(), "Password not found", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
