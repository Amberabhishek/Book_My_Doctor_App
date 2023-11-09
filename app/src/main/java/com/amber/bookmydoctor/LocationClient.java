package com.amber.bookmydoctor;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.FetchPhotoRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.PlaceLikelihood;
import com.google.android.libraries.places.api.model.PhotoMetadata;
import com.google.android.libraries.places.api.model.Place.Field;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LocationClient {
    private Activity hospitalFragment;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationRequest locationRequest;
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private PlacesClient placesClient;

    private ArrayList<HospitalModel> hospitalModelArrayList; // Initialize the ArrayList
    private HospitalAdapter adapter; // Make sure this adapter is set from the calling class

    public LocationClient(Activity hospitalFragment, HospitalAdapter adapter) {
        this.hospitalFragment = hospitalFragment;
        this.adapter = adapter; // Set the adapter
        hospitalModelArrayList = new ArrayList<>(); // Initialize the ArrayList
        Places.initialize(hospitalFragment, "AIzaSyD4rrS_p4XTi8u5MKro7o9TByVcMJBsP6Y"); // Replace with your API key
        placesClient = Places.createClient(hospitalFragment);
    }

    public void locationsender() {
        // Initialize the FusedLocationProviderClient
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(hospitalFragment);

        if (ActivityCompat.checkSelfPermission(hospitalFragment, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Request permission
            ActivityCompat.requestPermissions(hospitalFragment, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
        }

        // Create a location request
        locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)  // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000);  // 1 second, in milliseconds

        fusedLocationClient.requestLocationUpdates(locationRequest, new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult != null) {
                    // Get the last known location from the result
                    Location location = locationResult.getLastLocation();

                    // Get latitude and longitude
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();

                    // Inside your LocationClient class

                    Log.d("LocationClient", "Latitude: " + latitude + ", Longitude: " + longitude);

                    // Now you have the current latitude and longitude
                    // You can use them as needed in your app
                }
            }
        }, null);
    }

    public void nearbySearcher() {
        // Define the place fields you want to retrieve
        List<Field> placeFields = Arrays.asList(
                Field.NAME, Field.ADDRESS, Field.PHOTO_METADATAS, Field.TYPES);

        // Define the request for nearby places
        FindCurrentPlaceRequest request = FindCurrentPlaceRequest.newInstance(placeFields);

        if (ActivityCompat.checkSelfPermission(hospitalFragment, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Request permission
            ActivityCompat.requestPermissions(hospitalFragment, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
        }

        // Perform the request
        Task<FindCurrentPlaceResponse> placeResponse = placesClient.findCurrentPlace(request);

        placeResponse.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FindCurrentPlaceResponse response = task.getResult();
                List<PlaceLikelihood> placeLikelihoods = response.getPlaceLikelihoods();

                for (PlaceLikelihood placeLikelihood : placeLikelihoods) {
                    Place place = placeLikelihood.getPlace();

                    // Extract hospital details
                    String name = place.getName();
                    String address = place.getAddress();
                    List<PhotoMetadata> photoMetadata = place.getPhotoMetadatas();
                    String imageUrl = "";

                    // Inside the loop where you're processing place metadata
                    if (photoMetadata != null && !photoMetadata.isEmpty()) {
                        PhotoMetadata photo = photoMetadata.get(0);

                        // Assuming you have a valid photo reference, you can construct a photo URL
                        String photoReference = photo.getAttributions(); // Get the photo reference

                        imageUrl = getPhotoUrl(photoReference);
                    }

                    // Create a HospitalModel object with the extracted details
                    HospitalModel hospital = new HospitalModel(name, address, imageUrl);

                    // Add the hospital object to the list
                    hospitalModelArrayList.add(hospital);

                    Log.d("LocationClient", "Recorded Place - Name: " + name + ", Address: " + address);
                }

                // Update the HospitalAdapter with the updated hospital data
                adapter.setHospitalData(hospitalModelArrayList);
            } else {
                Exception exception = task.getException();
                if (exception != null) {
                    Log.e("LocationClient", "Error: " + exception.getMessage());
                }
            }
        });
    }

    private String getPhotoUrl(String photoReference) {
        String apiKey = "AIzaSyD4rrS_p4XTi8u5MKro7o9TByVcMJBsP6Y"; // Replace with your actual API key
        int maxWidth = 90; // Specify your desired photo width
        int maxHeight = 90; // Specify your desired photo height

        String url = "https://maps.googleapis.com/maps/api/place/photo" +
                "?maxwidth=" + maxWidth +
                "&maxheight=" + maxHeight +
                "&photoreference=" + photoReference +
                "&key=" + apiKey;

        return url;
    }
}