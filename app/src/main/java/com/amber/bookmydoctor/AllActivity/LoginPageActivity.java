package com.amber.bookmydoctor.AllActivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.amber.bookmydoctor.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.SignInMethodQueryResult;

public class LoginPageActivity extends Activity {

    EditText emailEditText, passwordEditText;
    ImageView loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        // Check if the user is already logged in
        SharedPreferences sharedPref = getSharedPreferences("login_status", Context.MODE_PRIVATE);
        boolean isLoggedIn = sharedPref.getBoolean("isLoggedIn", false);

        if (isLoggedIn) {
            // User is already logged in, so navigate to the dashboard
            Intent intent = new Intent(LoginPageActivity.this, DashPageActivity.class);
            startActivity(intent);
            finish(); // Close the current activity
        }

        ImageView registerButton = findViewById(R.id.register);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start the registration activity
                Intent intent = new Intent(LoginPageActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        TextView forgotPasswordTextView = findViewById(R.id.forgot_password);
        forgotPasswordTextView.setText(Html.fromHtml("<u>I forgot my password</u>"));
        forgotPasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showForgotPasswordDialog();
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

    private void showForgotPasswordDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Forgot Password");
        builder.setMessage("Enter your email:");

        // Set up the input
        final EditText input = new EditText(this);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String email = input.getText().toString();
                sendOtpByEmail(email);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void sendOtpByEmail(String email) {
        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(Task<Void> task) {
                        if (task.isSuccessful()) {
                            // Password reset email sent successfully
                            Toast.makeText(LoginPageActivity.this, "OTP sent to your email.", Toast.LENGTH_SHORT).show();
                        } else {
                            // If the email address is not registered or other issues
                            Toast.makeText(LoginPageActivity.this, "Failed to send OTP. Check your email address.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void checkEmailRegistration(final String email) {
        FirebaseAuth.getInstance().fetchSignInMethodsForEmail(email)
                .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                    @Override
                    public void onComplete(Task<SignInMethodQueryResult> task) {
                        if (task.isSuccessful()) {
                            SignInMethodQueryResult result = task.getResult();

                            if (result != null && result.getSignInMethods() != null
                                    && result.getSignInMethods().isEmpty()) {
                                // Email is not registered
                                Toast.makeText(LoginPageActivity.this, "Email is not registered.", Toast.LENGTH_SHORT).show();
                            } else {
                                // Email is registered, send OTP
                                sendOtpByEmail(email);
                            }
                        } else {
                            // Handle the task failure
                            Toast.makeText(LoginPageActivity.this, "Failed to check email registration.", Toast.LENGTH_SHORT).show();
                        }
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
                            Toast.makeText(LoginPageActivity.this, "Login failed. Please check your details.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
