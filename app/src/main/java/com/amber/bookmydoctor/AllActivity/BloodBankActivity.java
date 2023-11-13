package com.amber.bookmydoctor.AllActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.amber.bookmydoctor.R;

public class BloodBankActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_bank);


        ImageView redButton = findViewById(R.id.red_butn);

        // Set an OnClickListener for the ImageView
        redButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to open the target activity
                Intent intent = new Intent(BloodBankActivity.this, DonateChoiceActivity.class);
                // Replace "TargetActivity.class" with the actual name of the target activity.

                // Start the target activity
                startActivity(intent);
            }
        });

    }
}
