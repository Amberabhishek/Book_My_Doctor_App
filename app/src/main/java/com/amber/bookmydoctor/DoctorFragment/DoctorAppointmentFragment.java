package com.amber.bookmydoctor.DoctorFragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amber.bookmydoctor.AllActivity.PatientBookingActivity.BookingDetails;
import com.amber.bookmydoctor.PatientDetails;
import com.amber.bookmydoctor.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DoctorAppointmentFragment extends Fragment {

    private RecyclerView recyclerView;
    private PatientAdapter patientAdapter;
    private List<PatientDetails> patientList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doctor_appointment, container, false);

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.patient_recycler_View);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        // Initialize the list and adapter
        patientList = new ArrayList<>();
        patientAdapter = new PatientAdapter(patientList);
        recyclerView.setAdapter(patientAdapter);

        // Retrieve data from Firebase and populate the list
        retrieveDataFromFirebase();

        return view;
    }

    private void retrieveDataFromFirebase() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("patient_booking_details");

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                patientList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    BookingDetails bd = snapshot.getValue(BookingDetails.class);
                    Log.d("FirebaseData", "Doctor Name: " + bd.getDid());
                    Log.d("FirebaseData", "Patient: " + (bd.getDid() == currentUser.getUid().toString()));
                    if (bd.getDid().equals(currentUser.getUid())) {
                        PatientDetails patient = snapshot.getValue(PatientDetails.class);
                        if (patient != null) {
                            patientList.add(patient);
                        }

                    }

                }

                patientAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
            }
        });
    }
}
