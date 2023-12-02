package com.amber.bookmydoctor.AllActivity.DoctorAllActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.amber.bookmydoctor.AllActivity.LoginPageActivity;
import com.amber.bookmydoctor.R;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.AuthResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DoctorRegisterPageActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_register_page);

        mAuth = FirebaseAuth.getInstance();

        // Check if the user is already logged in
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // User is already logged in, open Doctor Dashboard Activity
            openDoctorDashboard();
        }

        MaterialButton createAccountBtn = findViewById(R.id.create_account_btn);
        EditText emailEditText = findViewById(R.id.email_edit_text);
        EditText passwordEditText = findViewById(R.id.password_edit_text);
        EditText confirmPasswordEditText = findViewById(R.id.confirm_password_edit_text);

        createAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                String confirmPassword = confirmPasswordEditText.getText().toString().trim();

                if (!isValidEmail(email)) {
                    emailEditText.setError("Invalid email");
                } else if (password.length() < 8) {
                    passwordEditText.setError("Password must be at least 8 characters");
                } else if (!password.equals(confirmPassword)) {
                    confirmPasswordEditText.setError("Passwords do not match");
                } else {
                    registerUser(email, password);
                }
            }
        });
    }

    private void registerUser(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(DoctorRegisterPageActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            setUserRole("doctor");
                            openDoctorDashboard();
                            finish();
                        } else {
                            String errorMessage = task.getException() != null ? task.getException().getMessage() : "Registration failed. Please try again.";
                            Toast.makeText(DoctorRegisterPageActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void openDoctorDashboard() {
        startActivity(new Intent(DoctorRegisterPageActivity.this, DoctorLoginPageActivity.class));
        finish();
    }

    private void setUserRole(String role) {
        String userId = mAuth.getCurrentUser().getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("doctor").child(userId);
        databaseReference.child("role").setValue(role);
    }

    private boolean isValidEmail(String email) {
        return email != null && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
