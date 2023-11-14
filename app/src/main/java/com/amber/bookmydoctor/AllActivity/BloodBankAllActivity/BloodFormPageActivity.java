package com.amber.bookmydoctor.AllActivity.BloodBankAllActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.amber.bookmydoctor.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class BloodFormPageActivity extends AppCompatActivity {

    private EditText editTextName, editTextAge, editTextBloodType, editTextAddress, editTextContact;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_detail_form);

        // Initialize Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference().child("user_details");

        editTextName = findViewById(R.id.editTextName);
        editTextAge = findViewById(R.id.editTextAge);
        editTextBloodType = findViewById(R.id.editTextBloodType);
        editTextAddress = findViewById(R.id.editTextAddress);
        editTextContact = findViewById(R.id.editTextContact);

        ImageView btnSubmit = findViewById(R.id.btnSubmit);
        // Inside the btnSubmit OnClickListener in BloodBankPageActivity
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateForm()) {
                    // All fields are filled, perform submission logic here
                    saveUserData();
                    showToast("Submit successful!");

                    SharedPreferences sharedPref = getSharedPreferences("blood_register_status", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putBoolean("isLoggedIn", true);
                    editor.apply();


                    // Pass blood type information to DonateChoiceActivity
                    Intent intent = new Intent(BloodFormPageActivity.this, BloodDonateChoiceActivity.class);
                    intent.putExtra("bloodType", editTextBloodType.getText().toString());
                    startActivity(intent);
                }
            }
        });

        editTextAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker(view);
            }
        });

        editTextBloodType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBloodTypeDialog();
            }
        });
    }

    private boolean validateForm() {
        if (TextUtils.isEmpty(editTextName.getText())
                || TextUtils.isEmpty(editTextAge.getText())
                || TextUtils.isEmpty(editTextBloodType.getText())
                || TextUtils.isEmpty(editTextAddress.getText())
                || TextUtils.isEmpty(editTextContact.getText())) {

            showToast("Please fill in all fields");
            return false;
        }

        String phoneNumber = editTextContact.getText().toString().trim();
        if (phoneNumber.length() != 10) {
            showToast("Please enter a valid 10-digit phone number");
            return false;
        }

        return true;
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void saveUserData() {
        String name = editTextName.getText().toString().trim();
        String dob = editTextAge.getText().toString().trim(); // Assuming dob is in the format "dd/MM/yyyy"
        String bloodType = editTextBloodType.getText().toString().trim();
        String address = editTextAddress.getText().toString().trim();
        String contact = editTextContact.getText().toString().trim();

        // Calculate age based on the current date
        int age = calculateAge(dob);

        // Create a unique ID for the user
        String userId = databaseReference.push().getKey();

        // Create a map to store user details
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("name", name);
        userMap.put("age", age); // Store the calculated age
        userMap.put("bloodType", bloodType);
        userMap.put("address", address);
        userMap.put("contact", contact);

        // Save user details to Firebase
        databaseReference.child(userId).setValue(userMap);
    }

    private int calculateAge(String dob) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        try {
            Date birthDate = dateFormat.parse(dob);
            Calendar today = Calendar.getInstance();
            Calendar birthCalendar = Calendar.getInstance();
            birthCalendar.setTime(birthDate);

            int age = today.get(Calendar.YEAR) - birthCalendar.get(Calendar.YEAR);

            // Adjust age if the birth date hasn't occurred yet this year
            if (today.get(Calendar.DAY_OF_YEAR) < birthCalendar.get(Calendar.DAY_OF_YEAR)) {
                age--;
            }

            return age;
        } catch (ParseException e) {
            e.printStackTrace();
            return 0; // Return 0 in case of an error
        }
    }


    private void showDatePicker(View view) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        editTextAge.setText(day + "/" + (month + 1) + "/" + year);
                    }
                },
                year,
                month,
                day
        );

        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

        datePickerDialog.show();
    }

    private void showBloodTypeDialog() {
        final String[] bloodTypes = {"A+", "B+", "O+", "AB+", "A-", "B-", "AB-", "O-"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select your blood type")
                .setItems(bloodTypes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        String selectedBloodType = bloodTypes[which];
                        editTextBloodType.setText(selectedBloodType);

                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
