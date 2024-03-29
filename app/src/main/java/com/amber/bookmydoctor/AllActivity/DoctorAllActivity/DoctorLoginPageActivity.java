package com.amber.bookmydoctor.AllActivity.DoctorAllActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amber.bookmydoctor.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DoctorLoginPageActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_login_page);

        mAuth = FirebaseAuth.getInstance();


        TextView forgotPasswordTextView = findViewById(R.id.forgot_password);
        forgotPasswordTextView.setText(Html.fromHtml("<u>I forgot my password</u>"));
        forgotPasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showForgotPasswordDialog();
            }
        });


        ImageView loginButton = findViewById(R.id.login_button);
        EditText emailEditText = findViewById(R.id.email_edit_text);
        EditText passwordEditText = findViewById(R.id.password_edit_text);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (!isValidEmail(email)) {
                    emailEditText.setError("Invalid email");
                } else if (password.isEmpty()) {
                    passwordEditText.setError("Password is required");
                } else {
                    loginUser(email, password);
                }
            }
        });

        ImageView registerButton = findViewById(R.id.register);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open DoctorRegisterPageActivity when the register button is clicked
                startActivity(new Intent(DoctorLoginPageActivity.this, DoctorRegisterPageActivity.class));
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
                            Toast.makeText(DoctorLoginPageActivity.this, "OTP sent to your email.", Toast.LENGTH_SHORT).show();
                        } else {
                            // If the email address is not registered or other issues
                            Toast.makeText(DoctorLoginPageActivity.this, "Failed to send OTP. Check your email address.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }



    private void loginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Login success, check if the user is a doctor
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                String userId = user.getUid();
                                // Check the user's role from Firebase Realtime Database
                                // Assuming you have a "role" field in your user data
                                // Adjust the database reference according to your data structure
                                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("doctor").child(userId);
                                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.exists() && dataSnapshot.child("role").getValue(String.class).equals("doctor")) {
                                            // User is a doctor, navigate to the doctor's dashboard or desired activity
                                            startActivity(new Intent(DoctorLoginPageActivity.this, DoctorDashActivity.class));
                                            finish();
                                            // Show a Toast for successful login
                                            Toast.makeText(DoctorLoginPageActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                                        } else {
                                            // User is not a doctor, show an error message or handle accordingly
                                            Toast.makeText(DoctorLoginPageActivity.this, "Invalid credentials for a doctor.", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        // Handle errors or show an error message
                                        Toast.makeText(DoctorLoginPageActivity.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(DoctorLoginPageActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private boolean isValidEmail(String email) {
        return email != null && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
