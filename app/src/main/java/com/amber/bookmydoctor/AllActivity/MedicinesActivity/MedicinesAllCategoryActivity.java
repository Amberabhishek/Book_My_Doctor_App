package com.amber.bookmydoctor.AllActivity.MedicinesActivity;

// Import statements

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amber.bookmydoctor.Medicine;
import com.amber.bookmydoctor.MedicinesAdaptor.CovidAdapter;
import com.amber.bookmydoctor.MedicinesAllListModel.CovidModel;
import com.amber.bookmydoctor.R;  // Replace with your actual package name

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MedicinesAllCategoryActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private List<CovidModel> covidList;
    private CovidAdapter covidAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_recyclerview);


        // Initialize Firebase
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("CovidMedicines");


        // Initialize RecyclerView and Adapter
        RecyclerView recyclerView = findViewById(R.id.recycler_View);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        covidList = new ArrayList<>();
        covidAdapter = new CovidAdapter(this, covidList);
        recyclerView.setAdapter(covidAdapter);

        // Call method to fetch data
        fetchDataFromFirebase();
    }

    private void fetchDataFromFirebase() {
        // Use ValueEventListener to listen for changes in the data
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                covidList.clear(); // Clear the existing list
                // Iterate through the dataSnapshot to get the data
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Get the CovidModel object
                    CovidModel covidModel = snapshot.getValue(CovidModel.class);

                    // Add the CovidModel to the list
                    covidList.add(covidModel);
                }
                // Notify the adapter that the data has changed
                covidAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle errors here
                Log.e("Firebase", "Error fetching data", databaseError.toException());
            }
        });
    }
}
