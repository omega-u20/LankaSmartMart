package com.junkyard.lankasmartmart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);

        Button btnSignUp = findViewById(R.id.btnSignUp);
        View textLogin = findViewById(R.id.textLogin);

        btnSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(SignupActivity.this, ActivityHome.class);
            startActivity(intent);
        });

        textLogin.setOnClickListener(v -> {
            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }
}