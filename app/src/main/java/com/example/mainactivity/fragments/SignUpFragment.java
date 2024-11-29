package com.example.mainactivity.fragments;

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

import com.example.mainactivity.DatabaseHelper;
import com.example.mainactivity.R;

public class SignUpFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        EditText signupEmail = view.findViewById(R.id.signup_email);
        EditText signupPassword = view.findViewById(R.id.signup_password);
        EditText signupConfirmPassword = view.findViewById(R.id.signup_confirm_password);
        Button signupButton = view.findViewById(R.id.signupButton);

        signupButton.setOnClickListener(v -> {
            String email = signupEmail.getText().toString().trim();
            String password = signupPassword.getText().toString();
            String confirmPassword = signupConfirmPassword.getText().toString();

            if (validateInput(email, password, confirmPassword)) {
                DatabaseHelper db = new DatabaseHelper(getContext());

                if (db.checkIfEmailExists(email)) {
                    Toast.makeText(getContext(), "Email already registered", Toast.LENGTH_SHORT).show();
                } else {
                    DetailsFragment detailsFragment = new DetailsFragment();

                    Bundle bundle = new Bundle();
                    bundle.putString("email", email);
                    bundle.putString("password", password);
                    detailsFragment.setArguments(bundle);

                    requireActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragmentContainer, detailsFragment)
                            .commit();
                }
                db.close();
            }
        });

        return view;
    }

    private boolean validateInput(String email, String password, String confirmPassword) {
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(getActivity(), "Please enter a valid email", Toast.LENGTH_SHORT).show();
            return false;
        }

        String passwordPattern = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[@#$%^&+=!]).{6,}$";

        if (password.isEmpty() || !password.matches(passwordPattern)) {
            Toast.makeText(getActivity(), "Password must be at least 6 characters and include an uppercase letter, a number, and a special character", Toast.LENGTH_LONG).show();
            return false;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(getActivity(), "Passwords do not match", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
