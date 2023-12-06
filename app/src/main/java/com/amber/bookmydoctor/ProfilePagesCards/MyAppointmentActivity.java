// YourAppointmentActivity.java

package com.amber.bookmydoctor.ProfilePagesCards;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amber.bookmydoctor.AllActivity.MyAppointment.Appointment;
import com.amber.bookmydoctor.AllActivity.MyAppointment.AppointmentAdapter;
import com.amber.bookmydoctor.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyAppointmentActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AppointmentAdapter appointmentAdapter;
    private List<Appointment> appointmentList;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_recyclerview);

        // Initialize RecyclerView and data
        recyclerView = findViewById(R.id.recycler_View);
        appointmentList = new ArrayList<>();
        appointmentAdapter = new AppointmentAdapter(appointmentList);

        // Set up RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(appointmentAdapter);

        // Initialize Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("patient_booking_details");

        // Fetch data from Firebase
        fetchDataFromFirebase();

        // Set delete click listener in the adapter
        appointmentAdapter.setOnDeleteClickListener(new AppointmentAdapter.OnDeleteClickListener() {
            @Override
            public void onDeleteClick(int position) {
                showConfirmationDialog(position);
            }
        });
    }

    private void fetchDataFromFirebase() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                appointmentList.clear(); // Clear the list before adding new data

                // In YourAppointmentActivity.java when fetching data from Firebase

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Appointment appointment = dataSnapshot.getValue(Appointment.class);
                    appointment.setAppointmentId(dataSnapshot.getKey()); // Assuming the key is the appointment ID
                    appointmentList.add(appointment);
                }

                appointmentAdapter.notifyDataSetChanged(); // Notify the adapter of the data change
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Handle error
            }
        });
    }

    private void deleteAppointment(int position) {
        if (position >= 0 && position < appointmentList.size()) {
            Appointment appointmentToDelete = appointmentList.get(position);
            String appointmentIdToDelete = getAppointmentId(appointmentToDelete);

            if (appointmentIdToDelete != null) {
                // Delete the data from Firebase
                DatabaseReference appointmentRef = databaseReference.child(appointmentIdToDelete);
                appointmentRef.removeValue();
            }
        }
    }

    private String getAppointmentId(Appointment appointment) {
        // Assuming you have a method to get the appointment ID from the Appointment object
        return appointment.getAppointmentId();
    }

    private void showConfirmationDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you want to cancel your appointment?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        deleteAppointment(position);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });

        // Create the AlertDialog object and show it
        builder.create().show();
    }
}
