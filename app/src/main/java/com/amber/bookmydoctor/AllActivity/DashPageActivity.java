package com.amber.bookmydoctor.AllActivity;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
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

import com.amber.bookmydoctor.Fragments.HospitalFragment;
import com.amber.bookmydoctor.Fragments.AppointmentFragment;
import com.amber.bookmydoctor.Fragments.HomeFragment;
import com.amber.bookmydoctor.Fragments.ProfileFragment;
import com.amber.bookmydoctor.Fragments.ShopFragment;
import com.amber.bookmydoctor.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class DashPageActivity extends AppCompatActivity {

    private HomeFragment homeFragment;
    private AppointmentFragment appointmentFragment;
    private HospitalFragment addFragment;
    private ShopFragment shopFragment;
    private ProfileFragment profileFragment;

    BottomNavigationView bottomNavigationView;

    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    private NavigationView topNavigationView;

    private String userName;
    private Fragment currentFragment;

    private void updateToolbarTitle(String title) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setTitle(title);
        }
    }

    private void fetchUserDataFromFirebase() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId);

            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String userName = dataSnapshot.child("name").getValue(String.class);
                        String imageUrl = dataSnapshot.child("imageUrl").getValue(String.class);

                        // Fetch email from Firebase Authentication
                        String userEmail = currentUser.getEmail();

                        // Update UI with the retrieved data
                        updateUserInfo(userName, imageUrl, userEmail);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle errors
                    Log.e("DashPageActivity", "Failed to fetch user data", databaseError.toException());
                }
            });
        }
    }

    private void updateUserInfo(String userName, String imageUrl, String userEmail) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                // Update the UI with the retrieved user data
                TextView navUserNameTextView = findViewById(R.id.nav_user_name);
                if (navUserNameTextView != null) {
                    navUserNameTextView.setText(userName);
                }
                ImageView navProfileImageView = findViewById(R.id.nav_profile_image);
                // Load the user image into the ImageView using a library like Picasso or Glide
                // Example using Picasso:
                Picasso.get().load(imageUrl).placeholder(R.drawable.ic_person_icon).into(navProfileImageView);

                // Update user email
                TextView navUserEmailTextView = findViewById(R.id.nav_user_email);
                navUserEmailTextView.setText(userEmail);
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash);

        // Fetch user data from Firebase and update UI
        fetchUserDataFromFirebase();

        homeFragment = new HomeFragment();
        appointmentFragment = new AppointmentFragment();
        addFragment = new HospitalFragment();
        shopFragment = new ShopFragment();
        profileFragment = new ProfileFragment();

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, homeFragment).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                String fragmentTitle = "";

                if (item.getItemId() == R.id.nav_home) {
                    transaction.setCustomAnimations(R.anim.press_anim, R.anim.back_anim);
                    transaction.replace(R.id.fragment_container, homeFragment);
                    fragmentTitle = "Home";
                    currentFragment = homeFragment;
                } else if (item.getItemId() == R.id.nav_appoint) {
                    transaction.setCustomAnimations(R.anim.press_anim, R.anim.back_anim);
                    transaction.replace(R.id.fragment_container, appointmentFragment);
                    fragmentTitle = "Appointments";
                    currentFragment = appointmentFragment;
                } else if (item.getItemId() == R.id.nav_hospital) {
                    transaction.setCustomAnimations(R.anim.press_anim, R.anim.back_anim);
                    transaction.replace(R.id.fragment_container, addFragment);
                    fragmentTitle = "Hospitals";
                    currentFragment = addFragment;
                } else if (item.getItemId() == R.id.nav_shop) {
                    transaction.setCustomAnimations(R.anim.press_anim, R.anim.back_anim);
                    transaction.replace(R.id.fragment_container, shopFragment);
                    fragmentTitle = "Shop";
                    currentFragment = shopFragment;
                } else if (item.getItemId() == R.id.nav_profile) {
                    transaction.setCustomAnimations(R.anim.press_anim, R.anim.back_anim);
                    transaction.replace(R.id.fragment_container, profileFragment);
                    fragmentTitle = "Profile";
                    currentFragment = profileFragment;
                }

                transaction.addToBackStack(null);
                transaction.commit();
                updateToolbarTitle(fragmentTitle);
                updateFabVisibility();

                return true;
            }
        });

        ImageButton fabCallAmbulance = findViewById(R.id.fab_call_ambulance);
        fabCallAmbulance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ambulanceNumber = "123"; // Replace with the actual ambulance number
                Uri phoneUri = Uri.parse("tel:" + ambulanceNumber);
                Intent dialIntent = new Intent(Intent.ACTION_DIAL, phoneUri);
                startActivity(dialIntent);
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        topNavigationView = findViewById(R.id.topNavigationview);
        topNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.nav_share) {
                    shareApp();
                } else if (itemId == R.id.nav_about) {
                    openAboutUsPopup();
                } else if (itemId == R.id.nav_help) {
                    openGmailForFeedback();
                } else if (itemId == R.id.nav_logout) {
                    Intent intent = new Intent(DashPageActivity.this, GetStartedActivity.class);
                    startActivity(intent);
                    finish();
                    logoutUser();
                }

                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                return true;
            }

            private void shareApp() {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Check out this app");
                shareIntent.putExtra(Intent.EXTRA_TEXT, "I found this amazing app that you might like: https://play.google.com/store/apps/details?id=" + getPackageName());
                startActivity(Intent.createChooser(shareIntent, "Share via"));
            }

            private void openAboutUsPopup() {
                AlertDialog.Builder builder = new AlertDialog.Builder(DashPageActivity.this);
                builder.setTitle("About Us");
                builder.setMessage("Book My Doctor is an Android application that not only solves the issue of the patients as user but also solves the problems of the doctors as well.\n" +
                        "The App offers users to book an appointment with the doctor that are register in the app already.\n" +
                        "Thank you"); // Replace with your content
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }

            private void openGmailForFeedback() {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"amberaashiqui226@gmail.com"});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Write Your feedback here...");
                try {
                    startActivity(emailIntent);
                } catch (android.content.ActivityNotFoundException e) {
                }
            }
        });

        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_bar);
        }

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
    }

    private void setFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
        currentFragment = fragment;
        updateFabVisibility();
    }

    private void updateFabVisibility() {
        ImageButton fabCallAmbulance = findViewById(R.id.fab_call_ambulance);
        if (currentFragment instanceof HomeFragment) {
            fabCallAmbulance.setVisibility(View.VISIBLE);
        } else {
            fabCallAmbulance.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if (currentFragment instanceof HomeFragment) {
            showExitConfirmationDialog();
        } else if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            super.onBackPressed();
        } else {
            showExitConfirmationDialog();
        }
    }

    private void showExitConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Exit");
        builder.setMessage("Are you sure you want to exit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_item, menu);
        return true;
    }

    private void logoutUser() {
        SharedPreferences sharedPref = getSharedPreferences("login_status", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("isLoggedIn", false);
        editor.apply();

        FirebaseAuth.getInstance().signOut();
    }
}