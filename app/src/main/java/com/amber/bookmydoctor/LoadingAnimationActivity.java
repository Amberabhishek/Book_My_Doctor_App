package com.amber.bookmydoctor;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoadingAnimationActivity extends AppCompatActivity {

    private BroadcastReceiver MyReceiver = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_animation_activity);

        MyReceiver = new MyReceiver();
        broadcastIntent();


        new Handler().postDelayed(() -> {
            // Close the loading page
            finish();

        }, 8000); // 8000 milliseconds (8 seconds) - adjust as needed

    }

    private void broadcastIntent() {
        registerReceiver(MyReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(MyReceiver);
}
}