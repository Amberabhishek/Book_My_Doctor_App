package com.amber.bookmydoctor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import android.widget.Toast;

public class LoginPageActivity extends Activity {

    HomeFragment homeFragment = new HomeFragment();
    EditText emailEditText, passwordEditText;
    Button loginButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        // Check if the user is already logged in
        SharedPreferences sharedPref = getSharedPreferences("login_status", Context.MODE_PRIVATE);
        boolean isLoggedIn = sharedPref.getBoolean("isLoggedIn", false);

        if (isLoggedIn) {
            // User is already logged in, so navigate to the dashboard
            Intent intent = new Intent(LoginPageActivity.this, DashPageActivity.class);
            startActivity(intent);
            finish(); // Close the current activity
        } else {
            // Continue with the login page
            // ...
        }

        Button getStartedButton = findViewById(R.id.register);
        getStartedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Define the intent to start the new activity here
                Intent intent = new Intent(LoginPageActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        TextView forgotPasswordTextView = findViewById(R.id.forgot_password);
        forgotPasswordTextView.setText(Html.fromHtml("<u>I forgot my password</u>"));
        forgotPasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Define the intent to start the new activity for password recovery here
                Intent passwordRecoveryIntent = new Intent(LoginPageActivity.this, ForgotPageActivity.class);
                startActivity(passwordRecoveryIntent);
            }
        });

        emailEditText = findViewById(R.id.email_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);
        loginButton = findViewById(R.id.login_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                loginUser(email, password);
            }
        });
    }

    private void loginUser(String email, String password) {

        // Validate email and password
        if (email.isEmpty() || password.isEmpty()) {
            // Email or password is empty, display an error message
            Toast.makeText(LoginPageActivity.this, "Please enter your email and password.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Authenticate the user using Firebase Authentication
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            // Login successful, store the user's login status
                            SharedPreferences sharedPref = getSharedPreferences("login_status", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putBoolean("isLoggedIn", true);
                            editor.apply();

                            // Login successful, navigate to the next activity
                            Intent intent = new Intent(LoginPageActivity.this, DashPageActivity.class);
                            startActivity(intent);
                            finish(); // Close the login activity
                            // Show a toast message for successful login
                            Toast.makeText(LoginPageActivity.this, "Successfully login!", Toast.LENGTH_SHORT).show();
                        } else {
                            // Login failed, display an error message
                            Toast.makeText(LoginPageActivity.this, "Login failed. Please check your credentials.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}