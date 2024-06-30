package com.example.buspasswithqrscan.SuperAdmin;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.buspasswithqrscan.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class activity_superadmin_dashboard extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Fragment_superadmin_dashboard homeFragment = new Fragment_superadmin_dashboard();
    Fragment_superadmin_map mapFragment = new Fragment_superadmin_map();
    Fragment_superadmin_notifications notificationsFragment = new Fragment_superadmin_notifications();
    Fragment_superadmin_profile profileFragment = new Fragment_superadmin_profile();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_superadmin_dashboard);

        bottomNavigationView = findViewById(R.id.bottom_navigation_superadmin);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.homesa) {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_superadmin, homeFragment).commit();
                return true;
            } else if (item.getItemId() == R.id.mapsa) {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_superadmin, mapFragment).commit();
                return true;

            } else if (item.getItemId() == R.id.notificationsa) {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_superadmin, notificationsFragment).commit();
                return true;
            } else {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_superadmin, profileFragment).commit();
                return true;
            }
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_superadmin, homeFragment).commit();
    }

}