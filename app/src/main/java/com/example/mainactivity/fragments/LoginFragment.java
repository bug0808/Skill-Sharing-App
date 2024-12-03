package com.example.mainactivity.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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
import com.example.mainactivity.activities.MainActivity;
import com.example.mainactivity.classes.User;

public class LoginFragment extends Fragment {

    private EditText loginName;
    private EditText password;
    private Button loginButton;
    private int valid, userId;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        loginName = view.findViewById(R.id.login_name);
        password = view.findViewById(R.id.password);
        loginButton = view.findViewById(R.id.loginButton);

        loginButton.setOnClickListener(v -> {
            String email = loginName.getText().toString().trim();
            String pass = password.getText().toString().trim();

            if (validateInput(email, pass)) {
                DatabaseHelper db = new DatabaseHelper(getContext());

                valid = db.validateLogin(email, pass);

                if (valid != -1) {
                    User user = db.getUserByEmail(email);
                    userId = user.getPersonalId();
                    db.setUserLoggedIn(String.valueOf(userId), true);

                    Log.d("Login", "UserId:" + userId);
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    intent.putExtra("userId", userId);
                    startActivity(intent);
                    getActivity().finish();

                } else {
                    Toast.makeText(getActivity(), "Invalid email or password", Toast.LENGTH_SHORT).show();
                }
                db.close();
            }
        });

        return view;
    }

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
