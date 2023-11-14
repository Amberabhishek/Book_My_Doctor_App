package com.amber.bookmydoctor.AllActivity.BloodBankAllActivity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.amber.bookmydoctor.LocationClient;
import com.amber.bookmydoctor.R;

public class BloodHospitalLocationActivity extends AppCompatActivity {

    LocationClient locationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_hospital);



        LocationClient lc = locationClient;
        lc.locationsender();
        lc.nearbySearcher();

    }

}
