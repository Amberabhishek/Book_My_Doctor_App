package com.amber.bookmydoctor.AllActivity.BloodBankAllActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.amber.bookmydoctor.AllActivity.DashPageActivity;
import com.amber.bookmydoctor.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BloodDonateChoiceActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;

    private TextView bloodTypeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_donate_select);

        // Initialize Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference().child("blood_details");

        bloodTypeTextView = findViewById(R.id.bloodTypeTextView);

        // Retrieve the selected blood type from the Intent
        String selectedBloodType = getIntent().getStringExtra("bloodType");

        // Set the selected blood type on the TextView
        if (!TextUtils.isEmpty(selectedBloodType)) {
            // If saved blood type is empty, use the selected blood type
            bloodTypeTextView.setText(selectedBloodType);

            // Save the selected blood type to Firebase
            saveBloodTypeToFirebase(selectedBloodType);
        } else {
            // Retrieve the saved blood type from Firebase
            retrieveBloodTypeFromFirebase();
        }

        ImageView donarsImageView = findViewById(R.id.donars);
        ImageView donatesImageView = findViewById(R.id.donates);

        // Set OnClickListener for the donars ImageView
        donarsImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Perform the action you want when the donars ImageView is clicked
                // For example, start a new activity
                Intent intent = new Intent(BloodDonateChoiceActivity.this, BloodDonorsActivity.class);
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
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Retrieve the saved blood type from Firebase
        retrieveBloodTypeFromFirebase();
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

    private void retrieveBloodTypeFromFirebase() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userUid = currentUser.getUid();

            databaseReference.child(userUid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // Retrieve blood type from Firebase
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
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle possible errors.
                }
            });
        }
    }

    private void saveBloodTypeToFirebase(String bloodType) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userUid = currentUser.getUid();

            // Save the selected blood type to Firebase
            databaseReference.child(userUid).child("bloodType").setValue(bloodType);
        }
    }
}