package com.amber.bookmydoctor.AllActivity.BloodBankAllActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.amber.bookmydoctor.AllActivity.DashPageActivity;
import com.amber.bookmydoctor.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BloodDonateChoiceActivity extends AppCompatActivity {

    private static final String BLOOD_TYPE_PREF_KEY = "bloodTypePref";
    private DatabaseReference databaseReference;

    private TextView bloodTypeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_donate_select);

        // Initialize Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference().child("user_details");

        bloodTypeTextView = findViewById(R.id.bloodTypeTextView);

        // Retrieve the selected blood type from the Intent
        String selectedBloodType = getIntent().getStringExtra("bloodType");

        // Set the selected blood type on the TextView
        if (!TextUtils.isEmpty(selectedBloodType)) {
            // If saved blood type is empty, use the selected blood type
            bloodTypeTextView.setText(selectedBloodType);

            // Save the selected blood type to SharedPreferences
            saveBloodType(selectedBloodType);
        } else {
            // Retrieve the saved blood type from SharedPreferences
            String savedBloodType = getSavedBloodType();

            // Set the blood type on the TextView
            if (!TextUtils.isEmpty(savedBloodType)) {
                bloodTypeTextView.setText(savedBloodType);
            }
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

        // Edit Form
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

        // Retrieve data from Firebase
        retrieveDataFromFirebase();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Retrieve the saved blood type from SharedPreferences
        String savedBloodType = getSavedBloodType();

        // Set the blood type on the TextView
        if (!TextUtils.isEmpty(savedBloodType)) {
            bloodTypeTextView.setText(savedBloodType);
        }
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
        String bloodType = sharedPref.getString("bloodType", "");

        // After retrieving blood type
        Log.d("BloodTypeChange", "Retrieved blood type: " + bloodType);

        return bloodType;
    }

    private void saveBloodType(String bloodType) {
        SharedPreferences sharedPref = getSharedPreferences(BLOOD_TYPE_PREF_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("bloodType", bloodType);
        editor.apply();
    }

    private void retrieveDataFromFirebase() {
        // Assuming "user_details" is the node in your database
        databaseReference.child("user_details").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method will be called once with the data from the database.
                if (dataSnapshot.exists()) {
                    // Get the data and update the UI as needed
                    String bloodTypeFromFirebase = dataSnapshot.child("bloodType").getValue(String.class);

                    // Set the retrieved blood type on the TextView
                    if (!TextUtils.isEmpty(bloodTypeFromFirebase)) {
                        bloodTypeTextView.setText(bloodTypeFromFirebase);

                        // After retrieving blood type from Firebase
                        Log.d("BloodTypeChange", "Retrieved blood type from Firebase: " + bloodTypeFromFirebase);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle possible errors.
            }
        });
    }
}
