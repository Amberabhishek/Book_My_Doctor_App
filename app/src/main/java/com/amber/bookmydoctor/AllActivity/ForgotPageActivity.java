package com.amber.bookmydoctor.AllActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.amber.bookmydoctor.R;
import com.google.android.material.button.MaterialButton;

public class ForgotPageActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        // Find the "Get OTP" button by its ID
        MaterialButton getOtpButton = findViewById(R.id.get_otp);

        // Set an OnClickListener for the button
        getOtpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Define the intent to start the new activity for password recovery here
                Intent passwordRecoveryIntent = new Intent(ForgotPageActivity.this, ForgotOTPActivity.class);
                startActivity(passwordRecoveryIntent);
            }
        });

    }
}