package com.amber.bookmydoctor;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash);
        SearchView searchView = findViewById(R.id.search_view);

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
                    setFragment(new ShareFragment());
                } else if (itemId == R.id.nav_about) {
                    setFragment(new AboutFragment());
                } else if (itemId == R.id.nav_help) {
                    setFragment(new HelpFragment());
                }

                // Close the drawer if it's open
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                return true;
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

        // Add the "LogOut" button
        Button logoutButton = findViewById(R.id.logout_btn);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutUser();
            }
        });
    }


    // Method to load a fragment into the FrameLayout
    private void setFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null); // Add the transaction to the back stack
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            // If there are fragments in the back stack, pop the back stack
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed(); // If there are no fragments in the back stack, handle as usual
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
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();

        // Define click listeners for various CardViews
        setupCardClickListeners();
    }
    private void setupCardClickListeners() {
        // Example CardView click listeners, replace with your CardView IDs and desired actions
        setupCardClickListener(R.id.first_card, CardActivity.class);
        setupCardClickListener(R.id.imageCard, BloodBankActivity.class);
        setupCardClickListener(R.id.videoCard, LabTestActivity.class);
        setupCardClickListener(R.id.audioCard, MedicineActivity.class);
    }

    private void setupCardClickListener(int cardViewId, Class<?> targetActivity) {
        CardView cardView = findViewById(cardViewId);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashPageActivity.this, targetActivity);
                startActivity(intent);
            }
        });
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


        // Navigate back to the login page
        Intent intent = new Intent(DashPageActivity.this, LoginPageActivity.class);
        startActivity(intent);
        finish(); // Close the current activity
    }


}