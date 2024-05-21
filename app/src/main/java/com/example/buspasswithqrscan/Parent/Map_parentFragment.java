package com.example.buspasswithqrscan.Parent;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.buspasswithqrscan.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.Arrays;
import java.util.List;

public class Map_parentFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    private GoogleMap mMap;
    private SupportMapFragment mapFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_map_parent, container, false);
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapparent);
        if (mapFragment == null) {
            FragmentManager fm = getChildFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            mapFragment = SupportMapFragment.newInstance();
            ft.replace(R.id.mapparent, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);

        return v;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        }

        // Define the list of LatLng points
        List<LatLng> locations = Arrays.asList(
                new LatLng(33.64325112591696, 73.07900336499642), // BIIT
                new LatLng(33.643368972962264, 73.07771405505343), // 6th Road
                new LatLng(33.64179536612695, 73.07721883973113), // 6th Road
                new LatLng(33.64133342515115, 73.07870798076382), // Iran Road
                new LatLng(33.63402779181908, 73.07629543322966) // Sadiqabad
        );

        // Define the names for each location
        List<String> locationNames = Arrays.asList(
                "BIIT",
                "6th Road",
                "6th Road",
                "Iran Road",
                "Sadiqabad"
        );

        // Add markers for each location
        for (int i = 0; i < locations.size(); i++) {
            LatLng location = locations.get(i);
            String locationName = locationNames.get(i);
            mMap.addMarker(new MarkerOptions().position(location).title(locationName));
        }

        // Draw the polyline connecting all points
        PolylineOptions polylineOptions = new PolylineOptions()
                .addAll(locations)
                .color(Color.RED)
                .width(10);

        mMap.addPolyline(polylineOptions);

        // Add bus icon at one of the locations along the polyline
        LatLng busLocation = new LatLng(33.64053299503377, 73.0785520191526); // Example: bus on Iran Road
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.profileonmap); // Assuming you have a bus icon resource
        mMap.addMarker(new MarkerOptions().position(busLocation).icon(icon));

        // Move the camera to show all markers
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (LatLng location : locations) {
            builder.include(location);
        }
        LatLngBounds bounds = builder.build();
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    mMap.setMyLocationEnabled(true);
                }
            } else {
                Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {

    }
}