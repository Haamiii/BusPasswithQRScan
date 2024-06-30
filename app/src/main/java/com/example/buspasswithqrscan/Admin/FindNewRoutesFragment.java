package com.example.buspasswithqrscan.Admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buspasswithqrscan.Admin.Model.ApiStops;
import com.example.buspasswithqrscan.Admin.Model.Routes;
import com.example.buspasswithqrscan.R;
import com.example.buspasswithqrscan.network.ApiService;
import com.example.buspasswithqrscan.network.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FindNewRoutesFragment extends Fragment {

    private static final String ARG_ORGANIZATION_ID = "organization_id";

    private RecyclerView routesRecyclerView;
    private RoutesAdapter routesAdapter;
    private ApiService apiService;
    private Spinner stopSpinner;

    public static FindNewRoutesFragment newInstance(int organizationId) {
        FindNewRoutesFragment fragment = new FindNewRoutesFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_ORGANIZATION_ID, organizationId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find_new_routes, container, false);

        apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        routesRecyclerView = view.findViewById(R.id.routes_recycler_view);
        routesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        routesAdapter = new RoutesAdapter(getContext(), null);
        routesRecyclerView.setAdapter(routesAdapter);

        stopSpinner = view.findViewById(R.id.organization_spinner);

        // Fetch routes and populate spinner
        fetchStopsForSpinner();

        return view;
    }

    private void fetchStopsForSpinner() {
        apiService.getAllStops().enqueue(new Callback<List<ApiStops>>() {
            @Override
            public void onResponse(@NonNull Call<List<ApiStops>> call, @NonNull Response<List<ApiStops>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<ApiStops> stops = response.body();
                    populateStopSpinner(stops);
                } else {
                    Toast.makeText(getContext(), "Failed to load stops", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<ApiStops>> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Failed to load stops", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void populateStopSpinner(List<ApiStops> stops) {
        ArrayAdapter<ApiStops> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, stops);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stopSpinner.setAdapter(adapter);

        stopSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ApiStops selectedStop = (ApiStops) parent.getSelectedItem();
                int selectedStopId = selectedStop.getId();
                fetchRoutes(selectedStopId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }

    private void fetchRoutes(int stopId) {
        apiService.getRoutes(stopId).enqueue(new Callback<List<Routes>>() {
            @Override
            public void onResponse(@NonNull Call<List<Routes>> call, @NonNull Response<List<Routes>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Routes> routes = response.body();
                    routesAdapter.updateRoutes(routes);
                } else {
                    Toast.makeText(getContext(), "Failed to load routes", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Routes>> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Failed to load routes", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
