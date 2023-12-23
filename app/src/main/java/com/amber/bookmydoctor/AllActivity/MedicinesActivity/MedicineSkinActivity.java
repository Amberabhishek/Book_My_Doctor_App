package com.amber.bookmydoctor.AllActivity.MedicinesActivity;

// Import statements

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amber.bookmydoctor.MedicinesAdaptor.SkinAdapter;
import com.amber.bookmydoctor.MedicinesAllListModel.SkinModel;
import com.amber.bookmydoctor.R;  // Replace with your actual package name

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MedicineSkinActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private List<SkinModel> skinList;
    private SkinAdapter skinAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_recyclerview);

        // Initialize Firebase
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("SkinMedicines");

        // Initialize RecyclerView and Adapter
        RecyclerView recyclerView = findViewById(R.id.recycler_View);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        skinList = new ArrayList<>();
        skinAdapter = new SkinAdapter(this, skinList);
        recyclerView.setAdapter(skinAdapter);

        // Call method to fetch data
        fetchDataFromFirebase();
    }

    private void fetchDataFromFirebase() {
        // Use ValueEventListener to listen for changes in the data
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                skinList.clear(); // Clear the existing list
                // Iterate through the dataSnapshot to get the data
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Get the SkinModel object
                    SkinModel skinModel = snapshot.getValue(SkinModel.class);

                    // Add the SkinModel to the list
                    skinList.add(skinModel);
                }
                // Notify the adapter that the data has changed
                skinAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle errors here
                Log.e("Firebase", "Error fetching data", databaseError.toException());
            }
        });
    }
}
