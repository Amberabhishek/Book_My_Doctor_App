package com.amber.bookmydoctor.AllActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.amber.bookmydoctor.R;

public class DonateChoiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_status);

        ImageView donarsImageView = findViewById(R.id.donars);
        ImageView donatesImageView = findViewById(R.id.donates);

        // Set OnClickListener for the donars ImageView
        donarsImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Perform the action you want when the donars ImageView is clicked
                // For example, start a new activity
                Intent intent = new Intent(DonateChoiceActivity.this, BloodDonorActivity.class);
                startActivity(intent);
            }
        });

        // Set OnClickListener for the donates ImageView
        donatesImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Perform the action you want when the donates ImageView is clicked
                // For example, start a new activity
                Intent intent = new Intent(DonateChoiceActivity.this, BloodBankPageActivity.class);
                startActivity(intent);
            }
        });
    }
}
