package com.amber.bookmydoctor.AllActivity.MedicinesList;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.amber.bookmydoctor.MedicinesAllListModel.VitaminModel;
import com.amber.bookmydoctor.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class VitaminMedicineActivity extends AppCompatActivity {

    private CircleImageView imageViewUpload;
    private EditText editTextUserInput, editTextInput, editTextInput1;
    private NumberPicker numberPickerYear;
    private Button uploadButton;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private Uri imageUri;

    // Firebase
    private DatabaseReference databaseReference;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vitamin_medicine); // Change to the appropriate layout if needed

        // Initialize Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("VitaminMedicines");
        storageReference = FirebaseStorage.getInstance().getReference();

        // Initialize UI elements
        editTextUserInput = findViewById(R.id.editTextUserInput);
        editTextInput = findViewById(R.id.editTextInput);
        editTextInput1 = findViewById(R.id.editTextInput1);
        numberPickerYear = findViewById(R.id.numberPickerYear);
        imageViewUpload = findViewById(R.id.imageViewUpload);
        uploadButton = findViewById(R.id.upload);

        // Set the range of years in the NumberPicker
        int currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
        numberPickerYear.setMinValue(currentYear);
        numberPickerYear.setMaxValue(2050);
        numberPickerYear.setValue(currentYear);

        // Set onClickListener for the CircleImageView
        imageViewUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCamera();
            }
        });

        // Set onClickListener for the upload button
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Capture data from the UI
                String medicineName = editTextUserInput.getText().toString();
                String medicinePrice = editTextInput.getText().toString();
                String companyName = editTextInput1.getText().toString();
                int manufacturingYear = numberPickerYear.getValue();

                // Create an instance of VitaminModel
                VitaminModel vitaminModel = new VitaminModel("", medicineName, medicinePrice, companyName, manufacturingYear);

                // Save data to Firebase Realtime Database
                saveToFirebase(vitaminModel);
            }
        });
    }

    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data != null) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                // Get the captured image
                Object image = extras.get("data");
                if (image instanceof Bitmap) {
                    // Set the captured image to the CircleImageView
                    imageViewUpload.setImageBitmap((Bitmap) image);
                    // Save the image to Firebase or perform other operations as needed

                    // Log the imageUri for debugging
                    imageUri = saveImageToFirebase((Bitmap) image);
                    Log.d("VitaminMedicineActivity", "Image URI: " + imageUri);
                }
            }
        } else {
            // Log the requestCode and resultCode for debugging
            Log.d("VitaminMedicineActivity", "Request Code: " + requestCode + ", Result Code: " + resultCode);
            Toast.makeText(this, "Image capture canceled", Toast.LENGTH_SHORT).show();
        }
    }

    // Save the image to Firebase and return the image URI
    private Uri saveImageToFirebase(Bitmap bitmap) {
        // Convert the bitmap to byte array
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        // Create a unique filename for the image
        String filename = "image_" + System.currentTimeMillis() + ".jpg";

        // Create a reference to the Firebase Storage location
        StorageReference imageRef = storageReference.child(filename);

        // Upload the original image to Firebase Storage
        UploadTask uploadTask = imageRef.putBytes(data);
        uploadTask.addOnSuccessListener(taskSnapshot -> {
            // Image uploaded successfully, get the download URL
            imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                // Set the image URL to the global variable
                imageUri = uri;
            });
        });

        // Placeholder URI for now, will be replaced after the image is uploaded
        return Uri.parse("content://media/external/images/media/1");
    }

    private void saveToFirebase(VitaminModel vitaminModel) {
        // Check if all required details are filled
        if (isDetailsFilled(vitaminModel)) {
            if (imageUri != null) {
                // Generate a unique key for the medicine entry
                String key = databaseReference.push().getKey();

                // Save the data to the database, including the image URL
                vitaminModel.setImageUrl(imageUri.toString());
                databaseReference.child(key).setValue(vitaminModel);

                // Display a success message
                Toast.makeText(this, "Medicine Uploaded Successfully", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Please capture an image", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Display a toast to fill in all details
            Toast.makeText(this, "Please fill in all details", Toast.LENGTH_SHORT).show();
        }
    }

    // Check if all required details are filled
    private boolean isDetailsFilled(VitaminModel vitaminModel) {
        return !vitaminModel.getMedicineName().isEmpty() &&
                !vitaminModel.getMedicinePrice().isEmpty() &&
                !vitaminModel.getCompanyName().isEmpty();
    }
}
