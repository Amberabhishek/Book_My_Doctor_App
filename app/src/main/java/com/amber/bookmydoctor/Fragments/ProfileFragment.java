package com.amber.bookmydoctor.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.amber.bookmydoctor.AllActivity.LoginPageActivity;
import com.amber.bookmydoctor.ProfilePagesCards.EditPrescriptionActivity;
import com.amber.bookmydoctor.ProfilePagesCards.EditProfileFragment;
import com.amber.bookmydoctor.ProfilePagesCards.HealthTipsActivity;
import com.amber.bookmydoctor.R;

public class ProfileFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Find the CardView for editing the profile by its ID
        CardView editProfileCardView = view.findViewById(R.id.editProfileButton);

        // Set an OnClickListener for the edit profile CardView
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

        // Find the CardView for editing the prescription by its ID
        CardView editPrescriptionCard = view.findViewById(R.id.editPrescription);

        // Set an OnClickListener on the edit prescription CardView
        editPrescriptionCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to the EditPrescriptionActivity
                Intent intent = new Intent(getActivity(), EditPrescriptionActivity.class);
                startActivity(intent);
            }
        });

        // Find the CardView for the logout button by its ID
        CardView logoutCardView = view.findViewById(R.id.logoutCardButton);

        // Set an OnClickListener on the logout CardView
        logoutCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the logout action, for example, clearing user session or data.
                // Then, navigate to the Login page.
                Intent intent = new Intent(getActivity(), LoginPageActivity.class);
                startActivity(intent);
                getActivity().finish(); // Optional: Finish the current activity to prevent going back to the profile.
            }
        });

        // Find the CardView for "Health Tips" by its ID
        CardView healthTipsCard = view.findViewById(R.id.healthTipsCard); // Replace with the correct ID

        // Set an OnClickListener for the "Health Tips" CardView
        healthTipsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to the HealthTipsActivity
                Intent intent = new Intent(getActivity(), HealthTipsActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
