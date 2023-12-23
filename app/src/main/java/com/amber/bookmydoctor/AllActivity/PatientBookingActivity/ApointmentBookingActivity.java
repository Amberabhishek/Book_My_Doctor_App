package com.amber.bookmydoctor.AllActivity.PatientBookingActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.DatePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.amber.bookmydoctor.AllActivity.MyAppointment.MyAppointmentActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.amber.bookmydoctor.R;
import com.squareup.picasso.Picasso;

public class ApointmentBookingActivity extends AppCompatActivity {

    private Button selectedTimeSlotButton;
    private String selectedDate;
    private DatabaseReference databaseReference;

    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctors_appointment_book);

        // Initialize Firebase
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("patient_booking_details");


        // Retrieve the current user from Firebase Authentication
        user = FirebaseAuth.getInstance().getCurrentUser();

        // Check if the user is authenticated
        if (user == null) {
            // Handle the case when the user is not authenticated
            // You might want to redirect to the login page or take appropriate action
            return;
        }
        

        // Retrieve specific data from the Intent
        String doctorName = getIntent().getStringExtra("doctorName");
        String doctorImage = getIntent().getStringExtra("doctorImage");
        String doctorType = getIntent().getStringExtra("doctorType");
        String doctorId = getIntent().getStringExtra("doctorID");
        String patientId = user.getUid();

        // Display the doctor's data in your UI elements
        TextView doctorNameTextView = findViewById(R.id.dr_name);
        TextView specialityTextView = findViewById(R.id.dr_speciality);
        ImageView doctorImageView = findViewById(R.id.dr_image);

        doctorNameTextView.setText(doctorName);
        specialityTextView.setText(doctorType);
        Picasso.get().load(doctorImage).into(doctorImageView);

        // Assuming you have an array of time slots, you can replace this with your data
        String[] timeSlots = {"8:00 AM", "9:00 AM", "10:00 AM", "11:00 AM", "12:00 PM", "1:00 PM", "2:00 PM", "3:00 PM", "4:00 PM", "5:00 PM", "6:00 PM"
                , "7:00 PM", "8:00 PM", "9:00 PM", "10:00 PM"};

        // Get the timeSlotContainer LinearLayout
        LinearLayout timeSlotContainer = findViewById(R.id.timeSlotContainer);

        // Loop through the array of time slots and create a Button for each one
        for (String timeSlot : timeSlots) {
            Button timeSlotButton = new Button(this);
            timeSlotButton.setText(timeSlot);
            timeSlotContainer.addView(timeSlotButton);

            timeSlotButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (selectedTimeSlotButton != null) {
                        selectedTimeSlotButton.setBackgroundColor(getResources().getColor(android.R.color.white));
                    }

                    timeSlotButton.setBackgroundColor(getResources().getColor(android.R.color.system_surface_dim_light));
                    selectedTimeSlotButton = timeSlotButton;
                }
            });
        }

        // Get the DatePicker
        View datePickerView = findViewById(R.id.calendar);

        // Set a listener for date selection
        if (datePickerView instanceof DatePicker) {
            DatePicker datePicker = (DatePicker) datePickerView;
            datePicker.init(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), (view, year, monthOfYear, dayOfMonth) -> {
                selectedDate = String.format("%02d-%02d-%04d", dayOfMonth, monthOfYear + 1, year);
            });
        } else if (datePickerView instanceof CalendarView) {
            ((CalendarView) datePickerView).setOnDateChangeListener((view, year, month, dayOfMonth) -> {
                selectedDate = String.format("%02d-%02d-%04d", dayOfMonth, month + 1, year);
            });
        }

        // Handle the booking button click
        ImageView bookButton = findViewById(R.id.btn_book);
        bookButton.setOnClickListener(view -> {
            EditText editTextName = findViewById(R.id.editTextName);
            EditText editTextEmail = findViewById(R.id.editTextEmail);
            EditText editTextPhone = findViewById(R.id.editTextPhone);

            String userName = editTextName.getText().toString();
            String userEmail = editTextEmail.getText().toString();
            String userPhone = editTextPhone.getText().toString();

            // Validate name, email, and phone number
            if (userName.isEmpty() || userEmail.isEmpty() || userPhone.isEmpty()) {
                Toast.makeText(this, "Please fill in all details", Toast.LENGTH_SHORT).show();
                return;
            }

            // Validate phone number length
            if (userPhone.length() < 10) {
                Toast.makeText(this, "Please enter a valid phone number", Toast.LENGTH_SHORT).show();
                return;
            }

            // Validate email format
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
                Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
                return;
            }

            if (selectedDate != null && selectedTimeSlotButton != null) {
                BookingDetails bookingDetails = new BookingDetails(userName, userEmail, userPhone, selectedDate, selectedTimeSlotButton.getText().toString(), doctorId, patientId);
                String bookingKey = databaseReference.push().getKey();
                databaseReference.child(bookingKey).setValue(bookingDetails);

                showCongratulationsPopup();
            } else {
                Toast.makeText(this, "Please select date and time slot", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showCongratulationsPopup() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.congratulations_popup);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.congratulation_anim);
        dialog.getWindow().getDecorView().startAnimation(animation);
        dialog.show();

        new Handler().postDelayed(() -> {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }

            Intent intent = new Intent(ApointmentBookingActivity.this, MyAppointmentActivity.class);
            startActivity(intent);
            finish();
        }, 3000); // 3000 milliseconds (3 seconds)
    }
}
