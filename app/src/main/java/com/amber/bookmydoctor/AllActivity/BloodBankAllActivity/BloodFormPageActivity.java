package com.amber.bookmydoctor.AllActivity.BloodBankAllActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.amber.bookmydoctor.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

    private Spinner genderSpinner;

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

        genderSpinner = findViewById(R.id.spinner);

        ImageView btnSubmit = findViewById(R.id.btnSubmit);

        // Check if the user is authenticated
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userUid = currentUser.getUid();

            // Retrieve user data from Firebase
            databaseReference.child("user_details").child(userUid).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    if (task.getResult() != null && task.getResult().getValue() != null) {
                        // User data exists, update UI
                        Map<String, Object> userData = (Map<String, Object>) task.getResult().getValue();

                        // Check for null before setting values
                        editTextName.setText(String.valueOf(userData.get("name")));
                        editTextAge.setText(String.valueOf(userData.get("age")));
                        editTextBloodType.setText(String.valueOf(userData.get("bloodType")));
                        editTextAddress.setText(String.valueOf(userData.get("address")));
                        editTextContact.setText(String.valueOf(userData.get("contact")));

                        // Set genderSpinner selection based on userData or a default value
                        String userGender = String.valueOf(userData.get("gender"));
                        setGenderSpinnerSelection(userGender);

                        // You may need to set the genderSpinner selection based on userData
                    } else {
                        // Clear fields if data is null
                        editTextName.setText("");
                        editTextAge.setText("");
                        editTextBloodType.setText("");
                        editTextAddress.setText("");
                        editTextContact.setText("");

                        // Set default selection for genderSpinner or clear it based on your requirement
                        setGenderSpinnerDefaultSelection();
                    }
                }
            });

        }

            btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateForm()) {
                    saveUserData();
                    showToast("Submit successful!");

                    SharedPreferences sharedPref = getSharedPreferences("blood_register_status", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putBoolean("isLoggedIn", true);
                    editor.apply();

                    Intent intent = new Intent(BloodFormPageActivity.this, BloodDonateChoiceActivity.class);
                    intent.putExtra("bloodType", editTextBloodType.getText().toString());
                    startActivity(intent);
                }
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.spinner_data,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(adapter);

        editTextAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker();
            }
        });

        editTextBloodType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBloodTypeDialog();
            }
        });
    }

    private Void setGenderSpinnerDefaultSelection() {
        return null;
    }

    private void setGenderSpinnerSelection(String userGender) {
        if (!TextUtils.isEmpty(userGender)) {
            // Find the index of userGender in the array of genders and set the selection
            ArrayAdapter<CharSequence> adapter = (ArrayAdapter<CharSequence>) genderSpinner.getAdapter();
            int position = adapter.getPosition(userGender);
            genderSpinner.setSelection(position);
        }
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
        String dob = editTextAge.getText().toString().trim();
        String bloodType = editTextBloodType.getText().toString().trim();
        String address = editTextAddress.getText().toString().trim();
        String contact = editTextContact.getText().toString().trim();
        String gender = genderSpinner.getSelectedItem().toString();

        int age = calculateAge(dob);

        // Get the current user's UID from Firebase Authentication
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            // Handle the case where the user is not authenticated
            showToast("User not authenticated");
            return;
        }

        String userUid = currentUser.getUid();

        if (TextUtils.isEmpty(userUid)) {
            // Handle the case where the user's UID is empty
            showToast("User UID is empty");
            return;
        }

        // Use the user's UID as a unique identifier
        DatabaseReference userRef = databaseReference.child("user_details").child(userUid);

        Map<String, Object> userMap = new HashMap<>();
        userMap.put("name", name);
        userMap.put("age", age);
        userMap.put("bloodType", bloodType);
        userMap.put("address", address);
        userMap.put("contact", contact);
        userMap.put("gender", gender);

        // Update the existing user details for the specific UID
        userRef.setValue(userMap);
    }

    private int calculateAge(String dob) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        try {
            Date birthDate = dateFormat.parse(dob);
            Calendar today = Calendar.getInstance();
            Calendar birthCalendar = Calendar.getInstance();
            birthCalendar.setTime(birthDate);

            int age = today.get(Calendar.YEAR) - birthCalendar.get(Calendar.YEAR);

            if (today.get(Calendar.DAY_OF_YEAR) < birthCalendar.get(Calendar.DAY_OF_YEAR)) {
                age--;
            }

            return age;
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private void showDatePicker() {
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
