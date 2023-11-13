package com.amber.bookmydoctor.AllActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.amber.bookmydoctor.R;

import java.util.Calendar;

public class BloodBankPageActivity extends AppCompatActivity {

    private EditText editTextName, editTextAge, editTextBloodType, editTextAddress, editTextContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_details);

        editTextName = findViewById(R.id.editTextName);
        editTextAge = findViewById(R.id.editTextAge);
        editTextBloodType = findViewById(R.id.editTextBloodType);
        editTextAddress = findViewById(R.id.editTextAddress);
        editTextContact = findViewById(R.id.editTextContact);

        ImageView btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateForm()) {
                    // All fields are filled, perform submission logic here
                    showToast("Submit successful!");
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

            // At least one field is empty
            showToast("Please fill in all fields");
            return false;
        }

        // Check if phone number is exactly 10 digits
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

    public void showDatePicker(View view) {
        if (view.getId() == R.id.editTextAge) {
            if (view instanceof EditText) {
                EditText editText = (EditText) view;
                if (editText.getCompoundDrawablesRelative()[2] != null) {

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
            }
        }
    }

    private void showBloodTypeDialog() {
        final String[] bloodTypes = {"A+", "B+", "O+", "AB+", "A-", "B-", "AB-", "O-"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select your blood type")
                .setItems(bloodTypes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        editTextBloodType.setText(bloodTypes[which]);
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
