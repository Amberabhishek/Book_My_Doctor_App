package com.amber.bookmydoctor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class ProfileFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Find the CardView by its ID
        androidx.cardview.widget.CardView editProfileCardView = view.findViewById(R.id.editProfileButton);

        // Set an OnClickListener for the CardView
        editProfileCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to another page (e.g., replace the current fragment with a new one)
                // You need to define the destination fragment and replace it accordingly.
                // For example, if you have another fragment called EditProfileFragment:
                EditProfileFragment editProfileFragment = new EditProfileFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, editProfileFragment); // Replace with the desired container
                transaction.addToBackStack(null); // Add to the back stack (optional)
                transaction.commit();
            }
        });

        return view;
    }
}