package com.junkyard.lankasmartmart;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        Button SignIn = findViewById(R.id.btnSignIn);
        View textRegister = findViewById(R.id.textRegister);
        EditText inputEmail = findViewById(R.id.inputEmail);
        EditText inputPassword = findViewById(R.id.inputPassword);

        SignIn.setOnClickListener(v -> {
            String email = inputEmail.getText().toString(); //email input
            String pass = inputPassword.getText().toString();  //password input

            // Remove bellow Log and do the DB sh*t
            Log.i("LoginActivity", "Email: " + email);
            Log.i("LoginActivity", "Password: " + pass);

            Intent intent = new Intent(LoginActivity.this, ActivityHome.class);
            startActivity(intent);
        });

        textRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(intent);
        });
    }
}