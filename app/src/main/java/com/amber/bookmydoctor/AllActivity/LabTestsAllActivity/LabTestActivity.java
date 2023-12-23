package com.amber.bookmydoctor.AllActivity.LabTestsAllActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.amber.bookmydoctor.R;

public class LabTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_tests);

        // Find the CardView by its ID
        CardView cancerCard = findViewById(R.id.cancer);

        // Set an OnClickListener for the CardView
        cancerCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start a new activity when the CardView is clicked
                Intent intent = new Intent(LabTestActivity.this, CancerLabTestActivity.class);
                startActivity(intent);
            }
        });

        CardView diabetesCard = findViewById(R.id.diabetes);

        diabetesCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LabTestActivity.this, DiabetesLabActivity.class);
                startActivity(intent);
            }
        });


        CardView feverCard = findViewById(R.id.fever);
        feverCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LabTestActivity.this, FeverLabActivity.class);
                startActivity(intent);
            }
        });

    }
}
