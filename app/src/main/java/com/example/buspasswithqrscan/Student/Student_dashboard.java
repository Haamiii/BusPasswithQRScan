package com.example.buspasswithqrscan.Student;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.buspasswithqrscan.R;
import com.example.buspasswithqrscan.network.SharedPreferenceManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Student_dashboard extends AppCompatActivity {


    BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment = new HomeFragment();
    MapFragment mapFragment = new MapFragment();
    NotificationsFragment notificationsFragment = new NotificationsFragment();
    ProfileFragment profileFragment = new ProfileFragment();
    String studentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);
        // Retrieve PassId as String
        Object passIdObject = SharedPreferenceManager.getInstance().get("PassId");
        if (passIdObject instanceof Integer) {
            studentId = String.valueOf(passIdObject);
        } else if (passIdObject instanceof String) {
            studentId = (String) passIdObject;
        } else {
            studentId = "";  // default value if PassId is not set
        }

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.home1) {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, homeFragment).commit();
                return true;
            } else if (item.getItemId() == R.id.map1) {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, mapFragment).commit();
                return true;

            } else if (item.getItemId() == R.id.qr1) {
                QrCodeFragment qrCodeFragment = QrCodeFragment.newInstance(studentId);
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, qrCodeFragment).commit();
                return true;

            } else if (item.getItemId() == R.id.notification1) {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, notificationsFragment).commit();
                return true;
            } else {

                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, profileFragment).commit();
                return true;
            }
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, homeFragment).commit();
    }

}
