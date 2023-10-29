package com.amber.bookmydoctor.ProfilePagesCards;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
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
import java.util.Calendar;

public class EditProfileFragment extends Fragment {
    private TextView dateOfBirthButton;
    private EditText nameEditText;
    private EditText phoneNumberEditText;
    private Spinner genderSpinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_profile_patient, container, false);

        // Find and initialize UI elements
        dateOfBirthButton = view.findViewById(R.id.button_dob);
        nameEditText = view.findViewById(R.id.etNames);
        phoneNumberEditText = view.findViewById(R.id.etPhoneNumbers);
        genderSpinner = view.findViewById(R.id.spinner);
        CardView saveButton = view.findViewById(R.id.save_button);

        // Set up the Date of Birth picker
        dateOfBirthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        // Set up the Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.spinner_data,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(adapter);

        // Set up the Save button click listener
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProfileDataToFirebase();
            }
        });

        return view;
    }

    private void showDatePickerDialog() {
        // Get the current date
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Create a DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                        // Handle the selected date
                        String selectedDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                        dateOfBirthButton.setText(selectedDate);
                    }
                },
                year, month, day
        );

        // Show the DatePickerDialog
        datePickerDialog.show();
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
            return; // Exit the method if any of the fields are empty
        }

        User user = new User(updatedName, updatedPhoneNumber, selectedDateOfBirth, selectedGender);

        userRef.setValue(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(requireContext(), "Profile Saved Successfully", Toast.LENGTH_SHORT).show();
                        // After saving the profile data, navigate to the ProfileFragment
                        Fragment profileFragment = new ProfileFragment();
                        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_container, profileFragment);
                        transaction.addToBackStack(null); // Optional: Add the transaction to the back stack
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
