package com.junkyard.lankasmartmart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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

        ImageButton btnBack = findViewById(R.id.btnBack);
        Button btnSignUp = findViewById(R.id.btnSignUp);
        View textLogin = findViewById(R.id.textLogin);
        EditText inputFullName = findViewById(R.id.inputFullName);
        EditText inputEmail = findViewById(R.id.inputEmail);
        EditText inputPassword = findViewById(R.id.inputPassword);

        btnBack.setOnClickListener(v -> finish());

        btnSignUp.setOnClickListener(v -> {
            // Trim to remove accidental spaces
            String name = inputFullName.getText().toString().trim();
            String email = inputEmail.getText().toString().trim();
            String pass = inputPassword.getText().toString().trim();

            if (email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }

            // Firebase Auth call
            mAuth.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            // Account created! Now save name to Database using specific region URL
                            String userId = mAuth.getCurrentUser().getUid();
                            DatabaseReference db = FirebaseDatabase.getInstance("https://lanka-smartmart-default-rtdb.asia-southeast1.firebasedatabase.app/")
                                    .getReference("users");

                            User newUser = new User(name, email, pass);
                            db.child(userId).setValue(newUser);

                            Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show();
                            
                            // Check for admin redirection even after signup
                            if ("admin@gmail.com".equals(email)) {
                                startActivity(new Intent(SignupActivity.this, AdminActivity.class));
                            } else {
                                startActivity(new Intent(SignupActivity.this, ActivityHome.class));
                            }
                            finish(); // Finish SignupActivity
                        } else {
                            Toast.makeText(this, "Error: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        });

        textLogin.setOnClickListener(v -> finish());
    }
}
