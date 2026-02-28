package com.junkyard.lankasmartmart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        Button btnSignIn = findViewById(R.id.btnSignIn);
        View textRegister = findViewById(R.id.textRegister);
        EditText inputEmail = findViewById(R.id.inputEmail);
        EditText inputPassword = findViewById(R.id.inputPassword);

        btnSignIn.setOnClickListener(v -> {
            String email = inputEmail.getText().toString();
            String password = inputPassword.getText().toString();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(LoginActivity.this, ActivityHome.class));
                            finish(); // Close login activity
                        } else {
                            Toast.makeText(this, "Login Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        textRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(intent);
        });
    }
}
