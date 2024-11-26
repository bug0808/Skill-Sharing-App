package com.example.mainactivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class WelcomeActivity extends AppCompatActivity {

    private LinearLayout bottomPanel;
    private Button emailConnectButton, facebookConnectButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome); // Ensure you have this layout file

        // Connect with Email Button
        emailConnectButton = findViewById(R.id.emailConnect);
        emailConnectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to LoginActivity
                Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        // Connect with Facebook Button
        facebookConnectButton = findViewById(R.id.facebookConnect);
        facebookConnectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Add Facebook connection logic here
            }
        });

    }
}
