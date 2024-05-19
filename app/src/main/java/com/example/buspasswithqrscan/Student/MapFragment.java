package com.example.buspasswithqrscan.Student;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private static final String TAG = "MapFragment";
    private GoogleMap mMap;
    private SupportMapFragment mapFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map, container, false);
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment == null) {
            FragmentManager fm = getChildFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            mapFragment = SupportMapFragment.newInstance();
            ft.replace(R.id.map, mapFragment).commit();
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

        LatLng startLocation = new LatLng(33.6508, 73.0849); // 6th Road
        LatLng endLocation = new LatLng(33.5896, 73.0551); // Saddar

        mMap.addMarker(new MarkerOptions().position(startLocation).title("6th Road"));
        mMap.addMarker(new MarkerOptions().position(endLocation).title("Saddar"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(startLocation, 12f));

        drawRoute(startLocation, endLocation);
    }

    private void drawRoute(LatLng start, LatLng end) {
        String url = getDirectionsUrl(start, end);
        new FetchURL().execute(url);
    }

    private String getDirectionsUrl(LatLng origin, LatLng dest) {
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        String sensor = "sensor=false";
        String key = "key=AAIzaSyA8obqhPxd0kOvMzxlc1v3uQH_mAVuW8RQ"; // Replace with your actual API key
        String parameters = str_origin + "&" + str_dest + "&" + sensor + "&" + key;
        return "https://maps.googleapis.com/maps/api/directions/json?" + parameters;
    }

    private class FetchURL extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {
            String data = "";
            try {
                data = downloadUrl(url[0]);
            } catch (Exception e) {
                Log.e(TAG, "Error fetching directions", e);
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray routes = jsonObject.getJSONArray("routes");
                if (routes.length() > 0) {
                    JSONObject route = routes.getJSONObject(0);
                    JSONObject overviewPolyline = route.getJSONObject("overview_polyline");
                    String points = overviewPolyline.getString("points");
                    List<LatLng> decodedPath = decodePoly(points);
                    PolylineOptions polylineOptions = new PolylineOptions()
                            .addAll(decodedPath)
                            .width(10)
                            .color(R.color.Teal) // Set the color to your preference
                            .geodesic(true);
                    mMap.addPolyline(polylineOptions);
                } else {
                    Toast.makeText(requireContext(), "No routes found", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                Log.e(TAG, "Error parsing directions", e);
            }
        }

        private String downloadUrl(String strUrl) throws Exception {
            String data = "";
            HttpURLConnection urlConnection = null;
            try {
                URL url = new URL(strUrl);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();
                BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                data = sb.toString();
                br.close();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            return data;
        }

        private List<LatLng> decodePoly(String encoded) {
            List<LatLng> poly = new ArrayList<>();
            int index = 0, len = encoded.length();
            int lat = 0, lng = 0;
            while (index < len) {
                int b, shift = 0, result = 0;
                do {
                    b = encoded.charAt(index++) - 63;
                    result |= (b & 0x1f) << shift;
                    shift += 5;
                } while (b >= 0x20);
                int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
                lat += dlat;

                shift = 0;
                result = 0;
                do {
                    b = encoded.charAt(index++) - 63;
                    result |= (b & 0x1f) << shift;
                    shift += 5;
                } while (b >= 0x20);
                int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
                lng += dlng;

                LatLng p = new LatLng(((lat / 1E5)), ((lng / 1E5)));
                poly.add(p);
            }
            return poly;
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
                Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
