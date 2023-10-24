package com.amber.bookmydoctor;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;



public class EmailVerificationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_page);
    }

    @Override
    protected void onResume() {
        super.onResume();
        handleEmailVerification();
    }

    private void handleEmailVerification() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();


        if (user != null && user.isEmailVerified()) {
            // The email is verified, so you can redirect to the login page.
            Intent intent = new Intent(EmailVerificationActivity.this, LoginPageActivity.class);
            startActivity(intent);
            finish();

        } else {
            // The email is not yet verified. You can display a message or handle it as needed.
            Toast.makeText(this, "Email is not yet verified.", Toast.LENGTH_SHORT).show();
        }
    }
}
