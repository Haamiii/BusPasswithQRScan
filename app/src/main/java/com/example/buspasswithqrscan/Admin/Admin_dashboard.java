package com.example.buspasswithqrscan.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.buspasswithqrscan.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Admin_dashboard extends AppCompatActivity {


    BottomNavigationView bottomNavigationView;
    HomeAdminFragment homeFragment = new HomeAdminFragment();
    MapAdminFragment mapFragment = new MapAdminFragment();
    AddAdminFragment addAdminFragmentFragment = new AddAdminFragment();
    NotificationAdminFragment notificationsFragment = new NotificationAdminFragment();
    ProfileAdminFragment profileFragment = new ProfileAdminFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        bottomNavigationView = findViewById(R.id.bottom_navigationAdmin);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.homeAdmin) {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layoutAdmin, homeFragment).commit();
                return true;
            } else if (item.getItemId() == R.id.mapAdmin) {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layoutAdmin, mapFragment).commit();
                return true;

            } else if (item.getItemId() == R.id.AddAdmin) {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layoutAdmin, addAdminFragmentFragment).commit();
                return true;

            } else if (item.getItemId() == R.id.notificationAdmin) {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layoutAdmin, notificationsFragment).commit();
                return true;
            } else {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layoutAdmin, profileFragment).commit();
                return true;
            }
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layoutAdmin, homeFragment).commit();
    }
}