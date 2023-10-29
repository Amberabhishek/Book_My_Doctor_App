package com.amber.bookmydoctor.AllActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.amber.bookmydoctor.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE);
        boolean isFirstRun = sharedPreferences.getBoolean("isFirstRun", true);

        if (isFirstRun) {
            // This is the first run of the app, show the main activity
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isFirstRun", false);
            editor.apply();
        } else {
            FirebaseAuth auth = FirebaseAuth.getInstance();
            FirebaseUser user = auth.getCurrentUser();


            if (user != null) {
                // User is already logged in, navigate to the dashboard
                Intent intent = new Intent(this, DashPageActivity.class);
                startActivity(intent);
                finish(); // This prevents the user from going back to the login and get started screens
            } else {
                Intent intent = new Intent(this, LoginPageActivity.class);
                startActivity(intent);
                finish();
            }
        }


        Button getStartedButton = findViewById(R.id.button_getstarted);
        getStartedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Define the intent to start the new activity here
                Intent intent = new Intent(MainActivity.this, GetStartedActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}