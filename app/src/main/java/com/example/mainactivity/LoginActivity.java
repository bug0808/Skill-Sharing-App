package com.example.mainactivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.mainactivity.LoginFragment;
import com.example.mainactivity.R;
import com.example.mainactivity.SignUpFragment;

public class LoginActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private ImageButton nextButton;
    private ImageButton homeButton;
    private ImageButton backButton;
    private Button loginButton;
    private EditText password;
    private EditText email;

    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Load the LoginFragment by default when the activity is created
        if (savedInstanceState == null) {
            loadFragment(new LoginFragment()); // This line ensures LoginFragment is shown first
        }

        // Set click listeners for Login and Sign Up options
        TextView loginTextView = findViewById(R.id.loginTextView);
        TextView signupTextView = findViewById(R.id.signupTextView);

        loginTextView.setOnClickListener(v -> loadFragment(new LoginFragment()));
        signupTextView.setOnClickListener(v -> loadFragment(new SignUpFragment()));
    }

    private void loadFragment(Fragment fragment) {
        // Replace the fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }

    //validate the email and password input
    /** private boolean validateInput(String email, String password) {
        if (email.isEmpty()) {
            this.email.setError(getString(R.string.emailReq));
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            this.email.setError(getString(R.string.emailInv));
            return false;
        }
        if (password.isEmpty()) {
            this.password.setError(getString(R.string.passwordReq));
            return false;
        }
        if (password.length() < 6) {
            this.password.setError(getString(R.string.passwordInv));
            return false;
        }
        return true;
    }
    **/

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy called");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState called");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(TAG, "onRestoreInstanceState called");
    }
}