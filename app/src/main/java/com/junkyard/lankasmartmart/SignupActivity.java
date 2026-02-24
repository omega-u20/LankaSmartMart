package com.junkyard.lankasmartmart;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
        EditText inputFullName = findViewById(R.id.inputFullName);
        EditText inputEmail = findViewById(R.id.inputEmail);
        EditText inputPassword = findViewById(R.id.inputPassword);


        btnSignUp.setOnClickListener(v -> {
            String name = inputFullName.getText().toString(); //name input
            String email = inputEmail.getText().toString();  //email input
            String pass = inputPassword.getText().toString();  //password input

            User newUser = new User(name, email, pass);

            //remove bellow Log and do the DB sh*t
            Log.i("SignupActivity", "Name: " + name);
            Log.i("SignupActivity", "Email: " + email);
            Log.i("SignupActivity", "Password: " + pass);

            Intent intent = new Intent(SignupActivity.this, ActivityHome.class);
            startActivity(intent);
        });

        textLogin.setOnClickListener(v -> {
            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }
}