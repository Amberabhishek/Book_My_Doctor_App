package com.amber.bookmydoctor.AllActivity.MedicinesActivity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.view.View;
import android.widget.RelativeLayout;

import com.amber.bookmydoctor.R;

public class MedicineActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medicines_activity);

        // Find the RelativeLayout by its ID
        RelativeLayout relativeLayout = findViewById(R.id.relativeLayout);

        // Set an OnClickListener to the RelativeLayout
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an Intent to start the new activity
                Intent intent = new Intent(MedicineActivity.this, MedicinesHelpCenter.class);
                startActivity(intent);
            }
        });

        CardView cardOne = findViewById(R.id.card_one);

        // Set a click listener on the CardView
        cardOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open the new activity when the CardView is clicked
                Intent intent = new Intent(MedicineActivity.this, MedicinesAllCategoryActivity.class);
                startActivity(intent);
            }
        });


        CardView cardView = findViewById(R.id.cardView1); // Corrected ID

        // Set a click listener on the CardView
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open the new activity when the CardView is clicked
                Intent intent = new Intent(MedicineActivity.this, MedicineSkinActivity.class);
                startActivity(intent);
            }
        });


        cardView = findViewById(R.id.cardView2);

        // Set a click listener on the CardView
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open the new activity when the CardView is clicked
                Intent intent = new Intent(MedicineActivity.this, MedicineVitaminActivity.class);
                startActivity(intent);
            }
        });



        // Find the RelativeLayout by its ID
        relativeLayout = findViewById(R.id.relativeLayouts);

        // Set an OnClickListener to the RelativeLayout
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an Intent to start the new activity
                Intent intent = new Intent(MedicineActivity.this, MedicineCatagoryActivity.class);
                startActivity(intent);
            }
        });

    }}
