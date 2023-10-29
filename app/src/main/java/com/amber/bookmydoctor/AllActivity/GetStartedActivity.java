package com.amber.bookmydoctor.AllActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.amber.bookmydoctor.R;

public class GetStartedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        CardView patientCard = findViewById(R.id.patient_butn);

        patientCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to open the SelectActivity
                Intent intent = new Intent(GetStartedActivity.this, LoginPageActivity.class);
                startActivity(intent);
                finish();
            }
        });

        CardView doctorButton = findViewById(R.id.doctor_btn); // Updated ID to match the XML

        doctorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to open the DoctorDetailActivity (or the desired activity)
                Intent intent = new Intent(GetStartedActivity.this, DoctorDashActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
