package com.amber.bookmydoctor.AllActivity.DoctorAllActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.amber.bookmydoctor.DoctorFragment.DoctorProfileFragment;
import com.amber.bookmydoctor.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

public class DoctorDetailsForm extends AppCompatActivity {

    private EditText editTextName, editTextAge, editTextDoctorType, editTextContact, editTextAbout;
    private Spinner spinner;
    private ImageView profileImage;
    private Uri selectedImageUri;
    private DatabaseReference databaseReference;
    private TextView editTextAddress; // Updated to TextView for city selection

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_editprofile_details);

        editTextName = findViewById(R.id.editTextName);
        editTextAge = findViewById(R.id.editTextAge);
        editTextDoctorType = findViewById(R.id.editTextDoctorType);
        editTextContact = findViewById(R.id.editTextContact);
        editTextAbout = findViewById(R.id.dr_abt);
        spinner = findViewById(R.id.spinner);
        profileImage = findViewById(R.id.profileImage);
        editTextAddress = findViewById(R.id.editTextAddress); // Updated to TextView for city selection

        databaseReference = FirebaseDatabase.getInstance().getReference().child("doctor");

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.spinner_data,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        editTextDoctorType.setOnClickListener(view -> showBloodTypeDialog());
        profileImage.setOnClickListener(v -> openGallery());

        editTextAddress.setOnClickListener(view -> showCitySelectionDialog()); // Updated to use TextView for city selection

        // Load user data from Firebase when the activity is created
        loadProfileDataFromFirebase();
    }

    public void showCitySelectionDialog() {
        final String[] cities = {"Andaman and Nicobar Islands", "Andhra Pradesh", "Arunachal Pradesh", "Assam", "Bihar", "Chandigarh",
                "Chhattisgarh", "Dadra and Nagar Haveli", "Daman and Diu", "Delhi", "Goa", "Gujarat",
                "Haryana", "Himachal Pradesh", "Jharkhand", "Karnataka", "Kerala", "Lakshadweep",
                "Madhya Pradesh", "Maharashtra", "Manipur", "Meghalaya", "Mizoram", "Nagaland",
                "Odisha", "Puducherry", "Punjab", "Rajasthan", "Sikkim", "Tamil Nadu", "Telangana",
                "Tripura", "Uttar Pradesh", "Uttarakhand", "West Bengal"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select City")
                .setItems(cities, (dialogInterface, which) ->
                        editTextAddress.setText(cities[which]));

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void showDatePicker(View view) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (datePicker, selectedYear, selectedMonth, selectedDay) -> {
                    String selectedDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                    editTextAge.setText(selectedDate);
                }, year, month, day);
        datePickerDialog.show();
    }

    private void showBloodTypeDialog() {
        final String[] bloodTypes = {"Surgeon", "General practitioner", "Neurologist", "Dermatologist", "Pediatrician", "Radiologist", "Psychiatrist", "Anesthesiologist", "Cardiologist", "Oncologist", "Orthopedic surgeon", "Urologist", "Ophthalmology", "Ophthalmologist", "Pathologist", "Endocrinologist", "Gastroenterologist", "Rheumatologist", "Internal medicine", "Pulmonologist", "Psychiatry", "Urology", "Family medicine", "Neurology"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Doctor Specialist")
                .setItems(bloodTypes, (dialogInterface, which) ->
                        editTextDoctorType.setText(bloodTypes[which]));

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            profileImage.setImageURI(selectedImageUri);
        }
    }

    public void onSaveButtonClick(View view) {
        String age = editTextAge.getText().toString();
        String doctorType = editTextDoctorType.getText().toString();
        String contact = editTextContact.getText().toString();
        String about = editTextAbout.getText().toString();
        String gender = spinner.getSelectedItem().toString();
        String name = editTextName.getText().toString();
        String city = editTextAddress.getText().toString();
        String role = "doctor";

        if (selectedImageUri != null) {
            String imageFileName = name + ".jpg";
            StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("profile_images/" + imageFileName);
            UploadTask uploadTask = storageRef.putFile(selectedImageUri);

            uploadTask.addOnSuccessListener(taskSnapshot -> {
                storageRef.getDownloadUrl().addOnSuccessListener(downloadUri -> {
                    String imageUrl = downloadUri.toString();
                    saveDoctorToDatabase(name, city, age, doctorType, contact, about, gender, imageUrl, role);
                });
            }).addOnFailureListener(e -> {
                Toast.makeText(DoctorDetailsForm.this, "Failed to upload image", Toast.LENGTH_SHORT).show();
            });
        } else {
            // If no image is selected, use a default image URL


        }
    }

    private void saveDoctorToDatabase(String name, String city, String age, String doctorType, String contact, String about, String gender, String imageUrl, String role) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();



        // Check if the doctor data already exists for the current user
        databaseReference.child(currentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {

            // Check if the doctor data already exists for the current user


            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Update existing data

                    databaseReference.child(currentUser.getUid()).child("name").setValue(name);
                    databaseReference.child(currentUser.getUid()).child("city").setValue(city);
                    databaseReference.child(currentUser.getUid()).child("age").setValue(age);
                    databaseReference.child(currentUser.getUid()).child("doctorType").setValue(doctorType);
                    databaseReference.child(currentUser.getUid()).child("contact").setValue(contact);
                    databaseReference.child(currentUser.getUid()).child("about").setValue(about);
                    databaseReference.child(currentUser.getUid()).child("gender").setValue(gender);
                    databaseReference.child(currentUser.getUid()).child("imageUrl").setValue(imageUrl);
                    databaseReference.child(currentUser.getUid()).child("role").setValue(role);
                    databaseReference.child(currentUser.getUid()).child("Did").setValue(currentUser.getUid());
                } else {
                    // Create a new entry
                    Doctor doctor = new Doctor(name, city, age, doctorType, contact, about, gender, imageUrl, role);
                    databaseReference.child(currentUser.getUid()).setValue(doctor);

                }

                Toast.makeText(DoctorDetailsForm.this, "Profile details saved", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("FirebaseError", "Failed to check existing data", databaseError.toException());
            }
        });
        finish();
    }

    private void loadProfileDataFromFirebase() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("doctor").child(currentUser.getUid());

        userRef.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Doctor doctor = dataSnapshot.getValue(Doctor.class);

                    // Add logging for debugging
                    Log.d("FirebaseData", "Doctor Name: " + doctor.getName());
                    // Populate the form with doctor data
                    editTextName.setText(doctor.getName());
                    editTextContact.setText(doctor.getContact());
                    editTextAge.setText(doctor.getAge());
                    editTextDoctorType.setText(doctor.getDoctorType());
                    editTextAbout.setText(doctor.getAbout());
                    editTextAddress.setText(doctor.getCity());

                    // Set the selected item in the gender spinner
                    ArrayAdapter<CharSequence> adapter = (ArrayAdapter<CharSequence>) spinner.getAdapter();
                    if (adapter != null) {
                        int position = adapter.getPosition(doctor.getGender());
                        spinner.setSelection(position);
                    }

                    // Load and display the doctor's profile image using Picasso
                    Picasso.get().load(doctor.getImageUrl()).into(profileImage);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Add logging for debugging
                Log.e("FirebaseError", "Failed to load profile data", e);
                Toast.makeText(DoctorDetailsForm.this, "Failed to load profile data", Toast.LENGTH_SHORT).show();

            }
        });

    }
}
