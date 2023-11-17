package com.amber.bookmydoctor.AllActivity.BloodBankAllActivity;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amber.bookmydoctor.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BloodDonorsActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private RecyclerView recyclerView;
    private DonorAdapter donorAdapter;
    private List<UserDetails> userDetailsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blood_donors_activity);


        // Initialize Firebase Database
        // Point directly to the "user_details" node
        databaseReference = FirebaseDatabase.getInstance().getReference("blood_details");

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        userDetailsList = new ArrayList<>();
        donorAdapter = new DonorAdapter(userDetailsList);
        recyclerView.setAdapter(donorAdapter);

        // Retrieve data from Firebase
        retrieveData();
    }

    private void retrieveData() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userDetailsList.clear(); // Clear existing data

                // Iterate through all user details
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    UserDetails userDetails = dataSnapshot.getValue(UserDetails.class);

                    if (userDetails != null) {
                        userDetailsList.add(userDetails);
                        Log.d("FirebaseData", "UserDetails added: " + userDetails.getName());
                    } else {
                        Log.e("FirebaseData", "UserDetails is null");
                    }
                }

                if (userDetailsList.isEmpty()) {
                    // Handle empty state (e.g., display a message or a placeholder view)
                    Log.d("FirebaseData", "No user details found");
                }

                donorAdapter.notifyDataSetChanged(); // Notify adapter of data change
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle errors gracefully (e.g., display an error message to the user)
                Log.e("FirebaseData", "Error: " + error.getMessage());
            }
        });
    }

}

