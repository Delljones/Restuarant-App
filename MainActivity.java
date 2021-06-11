package com.example.a91restuarantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button addNewPlace = findViewById(R.id.addNewPlace);
        Button showOnMap = findViewById(R.id.showOnMap);

        addNewPlace.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddAPlace.class);
            startActivity(intent);
        });

        showOnMap.setOnClickListener(v -> {
            Intent intent2 = new Intent(MainActivity.this, MapsActivity.class);
            startActivity(intent2);
        });
    }
}