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

public class SignUpFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        // Initialize UI components
        EditText signupEmail = view.findViewById(R.id.signup_email);
        EditText signupPassword = view.findViewById(R.id.signup_password);
        EditText signupConfirmPassword = view.findViewById(R.id.signup_confirm_password);
        Button signupButton = view.findViewById(R.id.signupButton);

        // Set click listener for signup button
        signupButton.setOnClickListener(v -> {
            String email = signupEmail.getText().toString();
            String password = signupPassword.getText().toString();
            String confirmPassword = signupConfirmPassword.getText().toString();

            // Add your signup logic here

            //check for valid email/safe password

            // For example, show a Toast message for testing
            Toast.makeText(getActivity(), "Sign Up clicked", Toast.LENGTH_SHORT).show();
        });

        return view;


        //create a new user subclass with the data gathered.
    }
}
