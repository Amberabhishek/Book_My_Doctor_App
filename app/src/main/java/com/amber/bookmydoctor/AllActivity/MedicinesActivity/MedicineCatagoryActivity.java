package com.amber.bookmydoctor.AllActivity.MedicinesActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.amber.bookmydoctor.AllActivity.MedicinesList.CovidMedicineActivity;
import com.amber.bookmydoctor.AllActivity.MedicinesList.SkinMedicineActivity;
import com.amber.bookmydoctor.AllActivity.MedicinesList.VitaminMedicineActivity;
import com.amber.bookmydoctor.R;

public class MedicineCatagoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_catagory);

        // Find the CardView by its ID
        androidx.cardview.widget.CardView cardView = findViewById(R.id.cardView1);

        // Set an OnClickListener to the CardView
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the click event, for example, navigate to another activity
                Intent intent = new Intent(MedicineCatagoryActivity.this, CovidMedicineActivity.class);
                startActivity(intent);
            }
        });

        // Find the CardView by its ID
        CardView cardView2 = findViewById(R.id.cardView2);

        // Set an OnClickListener for the CardView
        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start the new activity when the CardView is clicked
                Intent intent = new Intent(MedicineCatagoryActivity.this, SkinMedicineActivity.class);
                startActivity(intent);
            }
        });


        // Find the CardView by its ID
        CardView cardView3 = findViewById(R.id.cardView3);

        // Set an OnClickListener for the CardView
        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start the new activity when the CardView is clicked
                Intent intent = new Intent(MedicineCatagoryActivity.this, VitaminMedicineActivity.class);
                startActivity(intent);
            }
        });



    }
}