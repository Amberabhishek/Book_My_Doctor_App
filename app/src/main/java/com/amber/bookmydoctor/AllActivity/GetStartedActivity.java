package com.amber.bookmydoctor.AllActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

import com.amber.bookmydoctor.AllActivity.DoctorAllActivity.DoctorLoginPageActivity;
import com.amber.bookmydoctor.R;

public class GetStartedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_page);

        // No need to findViewById for the ImageViews

        // You can handle clicks in XML using android:onClick attribute
    }

    // Define the click methods in the activity
    public void onPeopleClick(View view) {
        // Create an intent to open the LoginPageActivity
        Intent intent = new Intent(this, LoginPageActivity.class);
        startActivity(intent);
        finish();
    }

    public void onDoctorClick(View view) {
        // Create an intent to open the DoctorDashActivity
        Intent intent = new Intent(this, DoctorLoginPageActivity.class);
        startActivity(intent);
        finish();
    }
}
