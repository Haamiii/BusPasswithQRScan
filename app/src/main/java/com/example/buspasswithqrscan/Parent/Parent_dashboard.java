package com.example.buspasswithqrscan.Parent;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.example.buspasswithqrscan.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Parent_dashboard extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Home_parentFragment homeFragment = new Home_parentFragment();
    Map_parentFragment mapFragment = new Map_parentFragment();
    Notification_parentFragment notificationsFragment = new Notification_parentFragment();
    Profile_parentFragment profileFragment = new Profile_parentFragment() ;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_dasboard);

        bottomNavigationView = findViewById(R.id.bottom_navigation_parent);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.home1) {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_parent, homeFragment).commit();
                return true;
            } else if (item.getItemId() == R.id.map1) {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_parent, mapFragment).commit();
                return true;

            } else if (item.getItemId() == R.id.notification1) {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_parent, notificationsFragment).commit();
                return true;
            } else {

                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_parent, profileFragment).commit();
                return true;
            }
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_parent, homeFragment).commit();
    }
    }
