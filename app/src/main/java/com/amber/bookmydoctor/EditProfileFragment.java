package com.amber.bookmydoctor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.AppCompatSpinner;
import android.widget.ArrayAdapter;

public class EditProfileFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the fragment's layout
        View view = inflater.inflate(R.layout.edit_profile_patient, container, false);

        // Find the Spinner by its ID
        AppCompatSpinner spinner = view.findViewById(R.id.spinner);

        // Create an ArrayAdapter to provide data to the Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                requireContext(), // Use requireContext() to get the fragment's context
                R.array.spinner_data, // Reference to an array resource with your data
                android.R.layout.simple_spinner_item
        );

        // Specify the layout for the dropdown items
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set the adapter for the Spinner
        spinner.setAdapter(adapter);

        return view;
    }
}
