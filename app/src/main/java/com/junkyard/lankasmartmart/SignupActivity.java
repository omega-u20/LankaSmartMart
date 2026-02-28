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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        Button btnSignUp = findViewById(R.id.btnSignUp);
        View textLogin = findViewById(R.id.textLogin);
        EditText inputFullName = findViewById(R.id.inputFullName);
        EditText inputEmail = findViewById(R.id.inputEmail);
        EditText inputPassword = findViewById(R.id.inputPassword);

        btnSignUp.setOnClickListener(v -> {
            String name = inputFullName.getText().toString();
            String email = inputEmail.getText().toString();
            String pass = inputPassword.getText().toString();

            if (email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }

            // Firebase Auth call
            mAuth.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            // Account created! Now save name to Database
                            String userId = mAuth.getCurrentUser().getUid();
                            DatabaseReference db = FirebaseDatabase.getInstance().getReference("users");

                            User newUser = new User(name, email, pass);
                            db.child(userId).setValue(newUser);

                            Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignupActivity.this, ActivityHome.class));
                            finish(); // Finish SignupActivity
                        } else {
                            Toast.makeText(this, "Error: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        });

        textLogin.setOnClickListener(v -> {
            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }
}
