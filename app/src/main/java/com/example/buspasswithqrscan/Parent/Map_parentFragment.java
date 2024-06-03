// Map_parentFragment.java
package com.example.buspasswithqrscan.Parent;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.buspasswithqrscan.Parent.model.ChildrenLocation;
import com.example.buspasswithqrscan.R;
import com.example.buspasswithqrscan.network.ApiService;
import com.example.buspasswithqrscan.network.RetrofitClient;
import com.example.buspasswithqrscan.network.SharedPreferenceManager;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Map_parentFragment extends Fragment implements OnMapReadyCallback {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    private GoogleMap mMap;
    private SupportMapFragment mapFragment;
    private ApiService apiService;
    private Handler handler;
    private Runnable updateRunnable;
    private Map<String, Marker> childMarkers = new HashMap<>();

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
        apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        return v;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
//        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//            mMap.setMyLocationEnabled(true);
//        } else {
//            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
//        }

        // Initialize the Handler and Runnable for periodic updates
        handler = new Handler(Looper.getMainLooper());
        updateRunnable = new Runnable() {
            @Override
            public void run() {
                updateChildLocations();
                handler.postDelayed(this, 10000); // Repeat every 10 seconds
            }
        };

        // Start the periodic updates
        handler.post(updateRunnable);
    }

    private void updateChildLocations() {
        int parentId = SharedPreferenceManager.getInstance().read("parentId", -1);
        Call<List<ChildrenLocation>> call = apiService.getChildLocation(parentId);
        call.enqueue(new Callback<List<ChildrenLocation>>() {
            @Override
            public void onResponse(Call<List<ChildrenLocation>> call, Response<List<ChildrenLocation>> response) {
                if (response.isSuccessful()) {
                    List<ChildrenLocation> childrenLocations = response.body();
                    if (childrenLocations != null) {
                        LatLngBounds.Builder builder = new LatLngBounds.Builder();
                        for (ChildrenLocation location : childrenLocations) {
                            List<LatLng> routePoints = new ArrayList<>();
                            for (ChildrenLocation.Location point : location.getLocations()) {
                                double latitude = point.getLatitude();
                                double longitude = point.getLongitude();
                                LatLng routePoint = new LatLng(latitude, longitude);
                                routePoints.add(routePoint);
                            }
                            // Add polyline for the route with red color
                            mMap.addPolyline(new PolylineOptions().addAll(routePoints).color(0xFFFF0000).clickable(true)); // Red color

                            // Update marker for child's current location
                            double latitude = location.getLocations().get(0).getLatitude();
                            double longitude = location.getLocations().get(0).getLongitude();
                            String name = location.getName();
                            LatLng childLocation = new LatLng(latitude, longitude);

                            Marker marker = childMarkers.get(name);
                            if (marker == null) {
                                // Add new marker with custom icon if it doesn't exist
                                Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.profileonmap); // Make sure custom_marker.png is in your drawable folder
                                marker = mMap.addMarker(new MarkerOptions()
                                        .position(childLocation)
                                        .title(name)
                                        .icon(BitmapDescriptorFactory.fromBitmap(icon)));
                                childMarkers.put(name, marker);
                            } else {
                                // Move existing marker to new location
                                marker.setPosition(childLocation);
                            }
                            builder.include(childLocation);
                        }
                        // Move the camera to fit all markers
                        LatLngBounds bounds = builder.build();
                        int padding = 50; // Offset from edges of the map in pixels
                        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding));
                    }
                } else {
                    Toast.makeText(requireContext(), "Failed to fetch child locations", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ChildrenLocation>> call, Throwable t) {
                Toast.makeText(requireContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Remove the handler callbacks to prevent memory leaks
        if (handler != null && updateRunnable != null) {
            handler.removeCallbacks(updateRunnable);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    mMap.setMyLocationEnabled(true);
                }
            } else {
                Toast.makeText(requireContext(), "Location permission is required to show your location on the map", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
