package com.example.mainactivity;

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
            // For example, show a Toast message for testing
            Toast.makeText(getActivity(), "Login clicked", Toast.LENGTH_SHORT).show();
        });

        return view;
    }
}
