package com.example.a91restuarantapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.ArrayList;
import java.util.Arrays;

import com.google.android.gms.maps.CameraUpdateFactory;


public class AddAPlace extends AppCompatActivity {

    private static final String TAG = "Running";

    // Location services //
    LocationManager locationManager;
    LocationListener locationListener;

    // Text view on top of screen //
    TextView placeNameText;
    TextView locationTextView;

    // Button with functionality //
    Button getCurrentLocation;
    Button showMap;
    Button save;

    private ArrayList<LatLng> locationArrayList;
    private GoogleMap mMap;

    // Permission to acccess location //
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
                        0, locationListener); } } }

                        // MAIN //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_a_place);
        // Buttons //
        getCurrentLocation = findViewById(R.id.getCurrentLocation);
        showMap = findViewById(R.id.showMap);
        save = findViewById(R.id.save);
        // Text View //
        placeNameText = findViewById(R.id.placeNameText);
        locationTextView = findViewById(R.id.locationTextView);


        //************** PLACES **********************//
        // Initialize the SDK for places
        Places.initialize(getApplicationContext(), getString(R.string.PlacesApi));
        // Create a new PlacesClient instance
        PlacesClient placesClient = Places.createClient(this);
        // Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.searchBar);
        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {

                placeNameText.setText("You Selected: \n " + place.getName());

                String address = placeNameText.getText().toString();
                GeoLocation geoLocation = new GeoLocation ();

                Toast.makeText(AddAPlace.this, "LatLong = " + place.getLatLng() , Toast.LENGTH_LONG).show();
            }
            @Override
            public void onError(@NonNull Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status); }});

            // when clicking save it means that the position that has been found is saved to the app //
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(AddAPlace.this, "This has been saved", Toast.LENGTH_SHORT).show();
                }
            });

            // ** Clickable button that will show the location in the location text name ** //
            getCurrentLocation.setOnClickListener(v -> {
                // Location services  //
                locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
                locationListener = new LocationListener() {
                    @Override
                    public void onLocationChanged(@NonNull Location location) {
                        locationTextView.setText(location.toString());
                    }};

                /* This will allow the use to use the permission services correctly*/
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                        (this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                } else {locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
                        0, locationListener);}
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
                        0, locationListener);

            });
            // ** Links to the same show map that is in main screen //
            showMap.setOnClickListener(v -> {
                Intent intent2 = new Intent (this, MapsActivity.class);
                startActivity(intent2);
            });
    }}

