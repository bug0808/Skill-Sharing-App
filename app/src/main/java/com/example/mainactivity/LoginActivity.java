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
import androidx.core.content.ContextCompat;
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
    private TextView loginTextView;
    private TextView signupTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize TextViews after setContentView
        loginTextView = findViewById(R.id.loginTextView);
        signupTextView = findViewById(R.id.signupTextView);

        // Load the LoginFragment by default when the activity is created
        if (savedInstanceState == null) {
            loadFragment(new LoginFragment()); // Ensures LoginFragment is shown first
            highlightSelectedOption(loginTextView); // Highlight the login option by default
        }

        // Set click listeners to switch fragments and toggle highlight
        loginTextView.setOnClickListener(v -> {
            loadFragment(new LoginFragment());
            highlightSelectedOption(loginTextView);
        });

        signupTextView.setOnClickListener(v -> {
            loadFragment(new SignUpFragment());
            highlightSelectedOption(signupTextView);
        });
    }

    private void loadFragment(Fragment fragment) {
        // Replace the fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }

    private void highlightSelectedOption(TextView selectedTextView) {
        // Set color for selected and unselected states
        loginTextView.setSelected(false);
        signupTextView.setSelected(false);
        loginTextView.setTextColor(ContextCompat.getColor(this, R.color.blueDark));
        signupTextView.setTextColor(ContextCompat.getColor(this, R.color.blueDark));
        // Select the clicked TextView
        selectedTextView.setSelected(true);
        selectedTextView.setTextColor(ContextCompat.getColor(this, R.color.blueLightest));
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