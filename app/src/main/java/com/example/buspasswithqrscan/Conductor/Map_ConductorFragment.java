package com.example.buspasswithqrscan.Conductor;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

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

public class Map_ConductorFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapClickListener  {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    private GoogleMap mMap;
    private SupportMapFragment mapFragment;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_map__conductor, container, false);
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapconductor);
        if (mapFragment == null) {
            FragmentManager fm = getChildFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            mapFragment = SupportMapFragment.newInstance();
            ft.replace(R.id.mapconductor, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        Button chooseJourneyButton = v.findViewById(R.id.choose_journey_button);
        chooseJourneyButton.setOnClickListener(view -> showSpinnerDialog());
        return v;
    }

    private void showSpinnerDialog() {
        // Inflate the dialog layout
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.diologl_spinner, null);

        // Set up the spinner
        Spinner spinner = dialogView.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.spinner_items, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // Create and show the dialog
        AlertDialog dialog = new AlertDialog.Builder(requireContext())
                .setView(dialogView)
                .setTitle("Select Journey")
                .setCancelable(false)
                .create();

        Button startButton = dialogView.findViewById(R.id.start_button);
        Button closeButton = dialogView.findViewById(R.id.close_button);

        startButton.setOnClickListener(v -> {
            String selectedItem = spinner.getSelectedItem().toString();
            shownewDialog();
            dialog.dismiss();
        });

        closeButton.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    private void shownewDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View newDialogView = inflater.inflate(R.layout.dialog_spinner_start, null);

        AlertDialog newDialog = new AlertDialog.Builder(requireContext())
                .setView(newDialogView)
                .setCancelable(false)
                .create();

        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        Button close = newDialogView.findViewById(R.id.close_button);
        close.setOnClickListener(v -> newDialog.dismiss());

        newDialog.show();
    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(this);

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
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.busonmap); // Assuming you have a bus icon resource
        mMap.addMarker(new MarkerOptions().position(busLocation).icon(icon));

        // Move the camera to show all markers
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (LatLng location : locations) {
            builder.include(location);
        }
        LatLngBounds bounds = builder.build();
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
    }
}
