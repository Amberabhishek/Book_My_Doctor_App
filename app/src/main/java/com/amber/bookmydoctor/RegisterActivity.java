package com.amber.bookmydoctor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        // Find the "Phone" button ImageView by its ID
        ImageView phoneButton = findViewById(R.id.button_phone);

        // Set an OnClickListener on the "Phone" button
        phoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to the next activity
                Intent intent = new Intent(RegisterActivity.this, PhonePageActivity.class);

                // Start the next activity
                startActivity(intent);
            }
        });


    }
}