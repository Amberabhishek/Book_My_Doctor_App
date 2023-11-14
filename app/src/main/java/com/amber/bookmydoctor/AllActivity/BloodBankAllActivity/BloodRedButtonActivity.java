package com.amber.bookmydoctor.AllActivity.BloodBankAllActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.amber.bookmydoctor.AllActivity.DashPageActivity;
import com.amber.bookmydoctor.AllActivity.LoginPageActivity;
import com.amber.bookmydoctor.R;

public class BloodRedButtonActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "MyPrefsFile";
    private static final String BLOOD_BANK_PAGE_COMPLETED = "BloodBankPageCompleted";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_red_button);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        boolean bloodBankPageCompleted = settings.getBoolean(BLOOD_BANK_PAGE_COMPLETED, false);

        if (!bloodBankPageCompleted) {
            ImageView redButton = findViewById(R.id.red_butn);

            // Set an OnClickListener for the ImageView
            redButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // Check if the user is already logged in
                    SharedPreferences sharedPref = getSharedPreferences("blood_register_status", Context.MODE_PRIVATE);
                    boolean isLoggedIn = sharedPref.getBoolean("isLoggedIn", false);

                    if (!isLoggedIn) {
                        // User is already logged in, so navigate to the dashboard
                        Intent intent = new Intent(BloodRedButtonActivity.this, BloodFormPageActivity.class);
                        startActivity(intent);
                        finish(); // Close the current activity
                    }
                    else{
                        Intent intent = new Intent(BloodRedButtonActivity.this, BloodDonateChoiceActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            });
        } else {
            // The BloodBankPageActivity has already been completed, you can handle this scenario
            // Maybe redirect the user to a different activity
            // For example, you can replace the following with your intended behavior
            Intent intent = new Intent(BloodRedButtonActivity.this, BloodDonateChoiceActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
