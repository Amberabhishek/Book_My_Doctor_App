package com.amber.bookmydoctor.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amber.bookmydoctor.Doctor;
import com.amber.bookmydoctor.AllActivity.PatientBookingActivity.DoctorListAdapter;
import com.amber.bookmydoctor.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AppointmentFragment extends Fragment {

    private RecyclerView recyclerView;
    private DoctorListAdapter doctorAdapter;
    private List<Doctor> doctors;

    public AppointmentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_doctor_recyclerview, container, false);

        recyclerView = view.findViewById(R.id.recycler_View);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        doctors = new ArrayList<>();
        doctorAdapter = new DoctorListAdapter(getContext(), doctors);
        recyclerView.setAdapter(doctorAdapter);

        // Retrieve data from Firebase Realtime Database and update the adapter
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("doctor");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Clear previous data
                doctors.clear();  // Change to 'doctors' instead of 'patientList'

                // Iterate through the data and add to the list
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Doctor doctor = snapshot.getValue(Doctor.class);  // Change to 'Doctor' instead of 'PatientModel'
                    Log.d("DoctorData", "Id: " + doctor.getDid());  // Add log statements for other fields
                    doctors.add(doctor);  // Change to 'doctors' instead of 'patientList'
                }

                // Notify the adapter that the data set has changed
                doctorAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Handle error
            }
        });

        return view;
    }
}
