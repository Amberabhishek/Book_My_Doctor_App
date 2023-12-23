package com.amber.bookmydoctor.AllActivity.MedicinesActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.amber.bookmydoctor.R;

public class MedicinesHelpCenter extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medicines_help_activity);

        CardView callUsCard = findViewById(R.id.call_us);
        CardView chatUsCard = findViewById(R.id.chat_us);

        callUsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open the phone dialer with the specified number
                Intent dialIntent = new Intent(Intent.ACTION_DIAL);
                dialIntent.setData(Uri.parse("tel:+91 9555996552"));
                startActivity(dialIntent);
            }
        });


        chatUsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open Gmail to compose an email
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:amberaashiqui226@gmail.com"));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject for your email"); // Add a subject if needed
                startActivity(emailIntent);
            }
        });
    }
}
