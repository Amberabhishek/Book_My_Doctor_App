package com.amber.bookmydoctor.AllActivity.BloodBankAllActivity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.amber.bookmydoctor.Fragments.HospitalFragment;
import com.amber.bookmydoctor.R;

public class BloodHospitalLocationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blood_location_card_hospital);

        HospitalFragment hospitalFragment = new HospitalFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, hospitalFragment).commit();


}
}
