package com.amber.bookmydoctor.AllActivity.BloodBankAllActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.amber.bookmydoctor.AllActivity.DashPageActivity;
import com.amber.bookmydoctor.R;

public class BloodDonateChoiceActivity extends AppCompatActivity {

    private static final String BLOOD_TYPE_PREF_KEY = "bloodTypePref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_donate_select);

        // Retrieve the selected blood type from the Intent
        String selectedBloodType = getIntent().getStringExtra("bloodType");

        // Set the selected blood type on the TextView
        TextView bloodTypeTextView = findViewById(R.id.bloodTypeTextView);

        // Retrieve the saved blood type from SharedPreferences
        String savedBloodType = getSavedBloodType();

        // Set the blood type on the TextView
        if (!TextUtils.isEmpty(savedBloodType)) {
            bloodTypeTextView.setText(savedBloodType);
        } else if (!TextUtils.isEmpty(selectedBloodType)) {
            // If saved blood type is empty, use the selected blood type
            bloodTypeTextView.setText(selectedBloodType);

            // Save the selected blood type to SharedPreferences
            saveBloodType(selectedBloodType);
        }

        ImageView donarsImageView = findViewById(R.id.donars);
        ImageView donatesImageView = findViewById(R.id.donates);

        // Set OnClickListener for the donars ImageView
        donarsImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Perform the action you want when the donars ImageView is clicked
                // For example, start a new activity
                Intent intent = new Intent(BloodDonateChoiceActivity.this, BloodGroupSelectActivity.class);
                startActivity(intent);
            }
        });

        //EditFom
        TextView editFormTextView = findViewById(R.id.edit_form_blood);
        editFormTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the click event here
                // For example, navigate to BloodBankPageActivity
                Intent intent = new Intent(BloodDonateChoiceActivity.this, BloodFormPageActivity.class);
                startActivity(intent);
            }
        });

        // Set OnClickListener for the donates ImageView
        donatesImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Perform the action you want when the donates ImageView is clicked
                // For example, start a new activity
                Intent intent = new Intent(BloodDonateChoiceActivity.this, BloodHospitalLocationActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        // Override the back button behavior to go back to DashboardPageActivity
        super.onBackPressed();
        Intent intent = new Intent(BloodDonateChoiceActivity.this, DashPageActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Clear the back stack
        startActivity(intent);
        finish();
    }

    private String getSavedBloodType() {
        SharedPreferences sharedPref = getSharedPreferences(BLOOD_TYPE_PREF_KEY, Context.MODE_PRIVATE);
        return sharedPref.getString("bloodType", "");
    }

    private void saveBloodType(String bloodType) {
        SharedPreferences sharedPref = getSharedPreferences(BLOOD_TYPE_PREF_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("bloodType", bloodType);
        editor.apply();
    }
}
