package com.amber.bookmydoctor.AllActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.amber.bookmydoctor.AllActivity.DoctorAllActivity.DoctorDashActivity;

import com.amber.bookmydoctor.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            // User is already logged in

            getUserRoleFromDatabase(user.getUid());
        } else {
            // User is not logged in, go to the GetStartedActivity
            navigateToGetStartedActivity();
        }

        Button getStartedButton = findViewById(R.id.button_getstarted);
        getStartedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Define the intent to start the new activity here
                navigateToGetStartedActivity();
            }
        });
    }


    private void getUserRoleFromDatabase(String uid) {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(uid).child("role");
        DatabaseReference doctorRef = FirebaseDatabase.getInstance().getReference("doctor").child(uid).child("role");

        // Read from the user database
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    String userRole = dataSnapshot.getValue(String.class);
                    handleUserRole(uid, userRole);
                } else {
                    // If not found in the user database, check the doctor database
                    checkDoctorDatabase(uid);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
                navigateToGetStartedActivity();
            }
        });
    }

    private void checkDoctorDatabase(String uid) {
        DatabaseReference doctorRef = FirebaseDatabase.getInstance().getReference("doctor").child(uid).child("role");

        // Read from the doctor database
        doctorRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    String doctorRole = dataSnapshot.getValue(String.class);
                    handleDoctorRole(uid, doctorRole);
                } else {
                    // Handle the case when the user is not found in either database
                    handleUnknownRole();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
                navigateToGetStartedActivity();
            }
        });
    }

    private void handleUserRole(String userId, String userRole) {
        if (userRole != null) {
            // Update the user role in Firebase
            DatabaseReference rolesRef = FirebaseDatabase.getInstance().getReference("role").child(userId);
            rolesRef.setValue(userRole)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                // Navigate to the corresponding activity
                                navigateToActivity(userRole);
                            } else {
                                // Handle the failure to update the user role
                                handleUnknownRole();
                            }
                        }
                    });
        } else {
            // Handle the case when userRole is null
            handleUnknownRole();
        }
    }

    private void handleDoctorRole(String userId, String doctorRole) {
        if (doctorRole != null) {
            // Update the doctor role in Firebase
            DatabaseReference rolesRef = FirebaseDatabase.getInstance().getReference("role").child(userId);
            rolesRef.setValue(doctorRole)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                // Navigate to the corresponding activity
                                navigateToActivity(doctorRole);
                            } else {
                                // Handle the failure to update the doctor role
                                handleUnknownRole();
                            }
                        }
                    });
        } else {
            // Handle the case when doctorRole is null
            handleUnknownRole();
        }
    }

    private void handleUnknownRole() {
        // Handle other roles or unknown roles if needed
        // For example, redirect to a default activity or show an error message
        navigateToGetStartedActivity();
    }

    private void navigateToActivity(String role) {
        // Navigate to the corresponding activity based on the role
        if ("patient".equals(role)) {
            Intent intent = new Intent(this, DashPageActivity.class);
            startActivity(intent);
            finish();
        } else if ("doctor".equals(role)) {
            Intent intent = new Intent(this, DoctorDashActivity.class);
            startActivity(intent);
            finish();
        } else {
            // Handle other roles or unknown roles if needed
            handleUnknownRole();
        }
    }

    private void navigateToGetStartedActivity() {
        Intent intent = new Intent(this, GetStartedActivity.class);
        startActivity(intent);
        finish();
    }
}
