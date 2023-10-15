package com.amber.bookmydoctor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import com.amber.bookmydoctor.R;

public class NextPageActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        Button getStartedButton = findViewById(R.id.register);
        getStartedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Define the intent to start the new activity here
                Intent intent = new Intent(NextPageActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        TextView forgotPasswordTextView = findViewById(R.id.forgot_password);
        forgotPasswordTextView.setText(Html.fromHtml("<u>I forgot my password</u>"));
        forgotPasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Define the intent to start the new activity for password recovery here
                Intent passwordRecoveryIntent = new Intent(NextPageActivity.this, ForgotPageActivity.class);
                startActivity(passwordRecoveryIntent);
            }
        });


        Button loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dashIntent = new Intent(NextPageActivity.this, DashPageActivity.class);
                startActivity(dashIntent);
            }
        });


    }
}
