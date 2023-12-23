package com.amber.bookmydoctor.AllActivity.LabTestsAllActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.amber.bookmydoctor.R;

public class FeverLabActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fever_lab);


        CardView buttonCardView = findViewById(R.id.button);
        buttonCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // When the CardView is clicked, open the dialer with the specified number
                String phoneNumber = "tel:9555996552";
                Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse(phoneNumber));
                startActivity(dialIntent);
            }
        });

    }
}