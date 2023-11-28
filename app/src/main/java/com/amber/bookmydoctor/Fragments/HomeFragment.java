package com.amber.bookmydoctor.Fragments;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.amber.bookmydoctor.AllActivity.BloodBankAllActivity.BloodRedButtonActivity;
import com.amber.bookmydoctor.AllActivity.CardActivity;
import com.amber.bookmydoctor.AllActivity.LabTestsAllActivity.LabTestActivity;
import com.amber.bookmydoctor.AllActivity.MedicinesActivity.MedicineActivity;
import com.amber.bookmydoctor.R;


public class HomeFragment extends Fragment {

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        // Call the setupCardClickListener method for each CardView you want to click on
        setupCardClickListener(R.id.first_card, CardActivity.class);
        setupCardClickListener(R.id.imageCard, BloodRedButtonActivity.class);
        setupCardClickListener(R.id.videoCard, LabTestActivity.class);
        setupCardClickListener(R.id.audioCard, MedicineActivity.class);

        return view;
    }

    // This method sets up a click listener for the CardView with the given ID
    private void setupCardClickListener(int cardViewId, Class<?> targetActivity) {
        CardView cardView = view.findViewById(cardViewId);
        if (cardView != null) {
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Create an intent to start the target activity
                    Intent intent = new Intent(getActivity(), targetActivity);
                    if (getActivity() != null) {
                        getActivity().startActivity(intent);
                    }
                }
            });
        }
    }


}
