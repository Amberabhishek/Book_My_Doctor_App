package com.amber.bookmydoctor.AllActivity.MedicinesActivity;

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

import java.util.ArrayList;
import java.util.List;

public class MedicineVitaminActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private List<SkinModel> vitaminList;
    private SkinAdapter vitaminAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_recyclerview);

        // Initialize Firebase
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("VitaminMedicines");

        // Initialize RecyclerView and Adapter
        RecyclerView recyclerView = findViewById(R.id.recycler_View);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        vitaminList = new ArrayList<>();
        vitaminAdapter = new SkinAdapter(this, vitaminList);
        recyclerView.setAdapter(vitaminAdapter);

        // Call method to fetch data
        fetchDataFromFirebase();
    }

    private void fetchDataFromFirebase() {
        // Use ValueEventListener to listen for changes in the data
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                vitaminList.clear(); // Clear the existing list
                // Iterate through the dataSnapshot to get the data
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Get the SkinModel object
                    SkinModel vitaminModel = snapshot.getValue(SkinModel.class);

                    // Add the SkinModel to the list
                    vitaminList.add(vitaminModel);
                }
                // Notify the adapter that the data has changed
                vitaminAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle errors here
                Log.e("Firebase", "Error fetching data", databaseError.toException());
            }
        });
    }
}
