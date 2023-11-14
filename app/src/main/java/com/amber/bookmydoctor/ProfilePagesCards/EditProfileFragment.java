package com.amber.bookmydoctor.ProfilePagesCards;

import android.app.DatePickerDialog;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.amber.bookmydoctor.Fragments.ProfileFragment;
import com.amber.bookmydoctor.R;
import com.amber.bookmydoctor.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.util.Calendar;

public class EditProfileFragment extends Fragment {
    private ImageView saveButton;
    private ImageView profileImage;
    private TextView dateOfBirthButton;
    private EditText nameEditText;
    private EditText phoneNumberEditText;
    private Spinner genderSpinner;
    private Uri selectedImageUri;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_edit_patient, container, false);

        profileImage = view.findViewById(R.id.profileImage);
        dateOfBirthButton = view.findViewById(R.id.button_dob);
        nameEditText = view.findViewById(R.id.etNames);
        phoneNumberEditText = view.findViewById(R.id.etPhoneNumbers);
        genderSpinner = view.findViewById(R.id.spinner);
        saveButton = view.findViewById(R.id.save_button);

        dateOfBirthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.spinner_data,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(adapter);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProfileDataToFirebase();
            }
        });

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        return view;
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                        String selectedDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                        dateOfBirthButton.setText(selectedDate);
                    }
                },
                year, month, day
        );
        datePickerDialog.show();
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

    private void saveProfileDataToFirebase() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(currentUser.getUid());

        String updatedName = nameEditText.getText().toString();
        String updatedPhoneNumber = phoneNumberEditText.getText().toString();
        String selectedDateOfBirth = dateOfBirthButton.getText().toString();
        String selectedGender = genderSpinner.getSelectedItem().toString();

        if (updatedName.isEmpty() || updatedPhoneNumber.isEmpty() || selectedDateOfBirth.isEmpty() || selectedGender.isEmpty()) {
            Toast.makeText(requireContext(), "Please fill in all the details", Toast.LENGTH_SHORT).show();
            return;
        }

        if (selectedImageUri != null) {
            String imageFileName = currentUser.getUid() + ".jpg";
            StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("profile_images/" + imageFileName);
            UploadTask uploadTask = storageRef.putFile(selectedImageUri);

            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri downloadUri) {
                            String imageUrl = downloadUri.toString();
                            User user = new User(updatedName, updatedPhoneNumber, selectedDateOfBirth, selectedGender, imageUrl);

                            userRef.setValue(user)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(requireContext(), "Profile Saved Successfully", Toast.LENGTH_SHORT).show();
                                            Fragment profileFragment = new ProfileFragment();
                                            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                                            transaction.replace(R.id.fragment_container, profileFragment);
                                            transaction.addToBackStack(null);
                                            transaction.commit();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(requireContext(), "Failed to save profile", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    });
                }
            });
        } else {
            User user = new User(updatedName, updatedPhoneNumber, selectedDateOfBirth, selectedGender, "");
            userRef.setValue(user)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(requireContext(), "Profile Saved Successfully", Toast.LENGTH_SHORT).show();
                            Fragment profileFragment = new ProfileFragment();
                            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.fragment_container, profileFragment);
                            transaction.addToBackStack(null);
                            transaction.commit();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(requireContext(), "Failed to save profile", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}
