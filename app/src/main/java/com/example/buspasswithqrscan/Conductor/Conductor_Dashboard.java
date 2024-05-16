package com.example.buspasswithqrscan.Conductor;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.example.buspasswithqrscan.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Conductor_Dashboard extends AppCompatActivity {


    BottomNavigationView bottomNavigationView;
    Home_ConductorFragment homeFragment = new Home_ConductorFragment();
    Map_ConductorFragment mapFragment = new Map_ConductorFragment();
    Qr_ConductoFragment qrCodeFragment = new Qr_ConductoFragment();
    Notifications_ConductorFragment notificationsFragment = new Notifications_ConductorFragment();
    Profile_ConductorFragment profileFragment = new Profile_ConductorFragment();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conductor_dashboard);
        bottomNavigationView = findViewById(R.id.bottom_navigationconductor);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.home1) {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layoutconductor, homeFragment).commit();
                return true;
            } else if (item.getItemId() == R.id.map1) {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layoutconductor, mapFragment).commit();
                return true;

            } else if (item.getItemId() == R.id.qr1) {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layoutconductor, qrCodeFragment).commit();
                return true;

            } else if (item.getItemId() == R.id.notification1) {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layoutconductor, notificationsFragment).commit();
                return true;
            } else {

                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layoutconductor, profileFragment).commit();
                return true;
            }
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layoutconductor, homeFragment).commit();
    }
}