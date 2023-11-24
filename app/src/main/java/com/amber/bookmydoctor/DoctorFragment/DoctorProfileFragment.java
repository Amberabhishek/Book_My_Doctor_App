package com.amber.bookmydoctor.DoctorFragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.amber.bookmydoctor.AllActivity.DoctorAllActivity.DoctorDetailsForm;
import com.amber.bookmydoctor.AllActivity.GetStartedActivity;
import com.amber.bookmydoctor.AllActivity.LoginPageActivity;
import com.amber.bookmydoctor.ProfilePagesCards.EditPrescriptionActivity;
import com.amber.bookmydoctor.ProfilePagesCards.EditProfileFragment;
import com.amber.bookmydoctor.ProfilePagesCards.HealthTipsActivity;
import com.amber.bookmydoctor.R;
import com.google.firebase.auth.FirebaseAuth;
import android.net.Uri;

public class DoctorProfileFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Find the CardView for editing the profile by its ID
        CardView editProfileCard = view.findViewById(R.id.editProfileButton);

        // Set an OnClickListener on the edit profile CardView
        editProfileCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to the EditPrescriptionActivity
                Intent intent = new Intent(getActivity(), DoctorDetailsForm.class);
                startActivity(intent);

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
                // Clear Firebase Authentication state
                FirebaseAuth.getInstance().signOut();

                // Clear the login status
                SharedPreferences sharedPref = getActivity().getSharedPreferences("login_status", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putBoolean("isLoggedIn", false);
                editor.apply();

                Intent intent = new Intent(getActivity(), GetStartedActivity.class);
                startActivity(intent);
                getActivity().finish(); // Finish the current activity
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

        // Find the CardView in your code
        CardView rateUsCardView = view.findViewById(R.id.rateUsCardView); // Replace with the actual ID

        // Set an OnClickListener for the CardView
        rateUsCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the Play Store to your app's page
                String appPackageName = getActivity().getPackageName(); // Use the context's getPackageName
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException e) {
                    // If the Play Store app is not installed, open the Play Store website
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
            }
        });

        return view;
    }
}
