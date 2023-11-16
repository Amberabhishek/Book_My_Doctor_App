package com.amber.bookmydoctor.AllActivity.BloodBankAllActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.amber.bookmydoctor.R;

public class BloodGroupSelectActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_group_page);

        CardView aPositiveCard = findViewById(R.id.a_positive);

        aPositiveCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the click event, for example, start a new activity
                Intent intent = new Intent(BloodGroupSelectActivity.this, BloodDonorsActivity.class);
                startActivity(intent);

                // Optionally, you can show a toast message
                Toast.makeText(BloodGroupSelectActivity.this, "A+", Toast.LENGTH_SHORT).show();
            }
        });



    }
}
