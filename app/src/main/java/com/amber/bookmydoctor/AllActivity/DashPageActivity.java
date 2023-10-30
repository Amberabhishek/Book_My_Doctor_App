package com.amber.bookmydoctor.AllActivity;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.amber.bookmydoctor.Fragments.AddFragment;
import com.amber.bookmydoctor.Fragments.AppointmentFragment;
import com.amber.bookmydoctor.Fragments.HomeFragment;
import com.amber.bookmydoctor.Fragments.ProfileFragment;
import com.amber.bookmydoctor.Fragments.ShopFragment;
import com.amber.bookmydoctor.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DashPageActivity extends AppCompatActivity {


    // Define the fragments
    private HomeFragment homeFragment;
    private AppointmentFragment appointmentFragment;
    private AddFragment addFragment;
    private ShopFragment shopFragment;
    private ProfileFragment profileFragment;


    BottomNavigationView bottomNavigationView;


    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    private NavigationView topNavigationView;

    private String userName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash);

        // Initialize the fragments
        homeFragment = new HomeFragment();
        appointmentFragment = new AppointmentFragment();
        addFragment = new AddFragment();
        shopFragment = new ShopFragment();
        profileFragment = new ProfileFragment();

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,homeFragment).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(MenuItem item) {


                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                if (item.getItemId() == R.id.nav_home) {
                    transaction.replace(R.id.fragment_container, homeFragment);
                } else if (item.getItemId() == R.id.nav_appoint) {
                    transaction.replace(R.id.fragment_container, appointmentFragment);
                } else if (item.getItemId() == R.id.nav_add) {
                    transaction.replace(R.id.fragment_container, addFragment);
                } else if (item.getItemId() == R.id.nav_shop) {
                    transaction.replace(R.id.fragment_container, shopFragment);
                } else if (item.getItemId() == R.id.nav_profile) {
                    transaction.replace(R.id.fragment_container, profileFragment);
                }
                transaction.addToBackStack(null); // Add the transaction to the back stack
                transaction.commit();
                return true;

            }

        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false); // Hide the default title


        // Get the topNavigationView for top navigation
        topNavigationView = findViewById(R.id.topNavigationview);
        topNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.nav_share) {
                    shareApp();
                } else if (itemId == R.id.nav_about) {
                    openAboutUsPopup();;
                } else if (itemId == R.id.nav_help) {
                    openGmailForFeedback();
                } else if (itemId == R.id.nav_logout) {
                    // Handle the "Logout" item click
                    Intent intent = new Intent(DashPageActivity.this, LoginPageActivity.class);
                    startActivity(intent);
                    finish();
                    logoutUser();
                }

                // Close the drawer if it's open
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                return true;
            }





            //Share App//
        private void shareApp() {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Check out this app");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "I found this amazing app that you might like: https://play.google.com/store/apps/details?id=" + getPackageName());
            startActivity(Intent.createChooser(shareIntent, "Share via"));
        }
            //About Us popup//
            private void openAboutUsPopup() {
                // Create and show an AlertDialog or Dialog for displaying the "About Us" content
                AlertDialog.Builder builder = new AlertDialog.Builder(DashPageActivity.this);
                builder.setTitle("About Us");
                builder.setMessage("Book My Doctor is an Android application that not only solves the issue of the patients as user, but also solves the problems of the doctors as well.\n" +
                        "The App offers users to book an appointment with the doctor that are register in the app already.\n" +
                        "Thankyou"); // Replace with your content
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Handle the "OK" button click if needed
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }


        //Help and Feedback//
            private void openGmailForFeedback() {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:")); // Use "mailto:" to specify email as the data
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"amberaashiqui226@gmail.com"}); // Set the recipient's email address
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback"); // Set the email subject
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Write Your feedback here..."); // Set the email body

                try {
                    startActivity(emailIntent);
                } catch (android.content.ActivityNotFoundException e) {
                }
            }

        });

        // Get the DrawerLayout and ActionBarDrawerToggle
        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);


        // Enable the hamburger icon
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_bar); // Use your drawable resource here
        }

        // Set the ActionBarDrawerToggle as the drawer listener
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
    }

    // Method to load a fragment into the FrameLayout
    private void setFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null); // Add the transaction to the back stack
        transaction.commit();
    }

    private int backPressCount = 0;

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            // Pop the back stack and navigate back to the previous fragment
            getSupportFragmentManager().popBackStack();
        } else if (backPressCount < 1) {
            backPressCount++;
            Toast.makeText(this, "Press back again", Toast.LENGTH_SHORT).show();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true; // This handles the opening/closing of the navigation drawer
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_item, menu);
        return true;
    }

    private void logoutUser() {
        // Log out the user (implement Firebase Authentication log-out here)
        // Example: FirebaseAuth.getInstance().signOut();
        // Clear the user's login status
        SharedPreferences sharedPref = getSharedPreferences("login_status", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("isLoggedIn", false);
        editor.apply();

        FirebaseAuth.getInstance().signOut();
    }
}
