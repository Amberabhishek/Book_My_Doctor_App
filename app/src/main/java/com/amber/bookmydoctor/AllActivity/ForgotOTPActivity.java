package com.amber.bookmydoctor.AllActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.amber.bookmydoctor.R;
import com.google.android.material.button.MaterialButton;

public class ForgotOTPActivity extends AppCompatActivity {

    private EditText[] otpEditTexts;
    private MaterialButton submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_page);

        otpEditTexts = new EditText[]{
                findViewById(R.id.rectangle_7),
                findViewById(R.id.rectangle_5),
                findViewById(R.id.rectangle_4),
                findViewById(R.id.rectangle_8)
        };

        // Set up TextWatchers for each OTP EditText
        for (int i = 0; i < otpEditTexts.length - 1; i++) {
            final int currentIndex = i;
            final int nextIndex = i + 1;

            otpEditTexts[currentIndex].addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    if (editable.length() == 1) {
                        // Move the focus to the next EditText if a number is entered
                        if (nextIndex < otpEditTexts.length) {
                            otpEditTexts[nextIndex].requestFocus();
                        }
                    }
                }
            });
        }

        submitButton = findViewById(R.id.get_otp);

        MaterialButton submitButton = findViewById(R.id.get_otp);

        submitButton.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            // Define the intent to start the new activity here
            Intent intent = new Intent(ForgotOTPActivity.this, RecoveyPasswordActivity.class);
            startActivity(intent);
        }
    });

    }
}



