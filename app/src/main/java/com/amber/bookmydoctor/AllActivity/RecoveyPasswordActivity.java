package com.amber.bookmydoctor.AllActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.amber.bookmydoctor.R;
import com.google.android.material.button.MaterialButton;

public class RecoveyPasswordActivity extends AppCompatActivity {

    private EditText passwordEditText;
    private EditText rePasswordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recovery_password);


        /*MaterialButton getOtpButton = findViewById(R.id.get_otp);


        passwordEditText = findViewById(R.id.password);
        rePasswordEditText = findViewById(R.id.re_password);
        // Set an OnClickListener for the button
        getOtpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String password = passwordEditText.getText().toString();
                String rePassword = rePasswordEditText.getText().toString();

                // Check if the passwords match
                if (password.equals(rePassword)) {
                    // Passwords match, navigate to the next activity
                    Intent intent = new Intent(RecoveyPasswordActivity.this, NextPageActivity.class); // Replace NextActivity with the actual class name of your next activity
                    startActivity(intent);
                } else {
                    // Passwords do not match, show a toast message
                    Toast.makeText(RecoveyPasswordActivity.this, "Passwords does not match", Toast.LENGTH_SHORT).show();
                }
            }
        });*/

    }
}


