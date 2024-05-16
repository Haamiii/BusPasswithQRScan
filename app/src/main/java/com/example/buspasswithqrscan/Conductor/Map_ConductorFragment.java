package com.example.buspasswithqrscan.Conductor;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Map_ConductorFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapClickListener  {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    private GoogleMap mMap;
    private SupportMapFragment mapFragment;
    private Spinner spinner;
    private SpinnerAdapter adapter;

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
        // Enable the My Location layer if the permission is granted
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            // Request location permission
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        }

        // Add a marker in Biit and move the camera
        LatLng uni = new LatLng(33.64098, 73.07782);
        mMap.addMarker(new MarkerOptions().position(uni).title("Barani Institute of Information Technology"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(uni, 15f));
    }
}
