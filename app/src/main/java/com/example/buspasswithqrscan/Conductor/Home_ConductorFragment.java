package com.example.buspasswithqrscan.Conductor;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.buspasswithqrscan.Conductor.model.JourneyStopsChecker;
import com.example.buspasswithqrscan.Conductor.model.StopModel;
import com.example.buspasswithqrscan.R;
import com.example.buspasswithqrscan.network.ApiService;
import com.example.buspasswithqrscan.network.RetrofitClient;
import com.example.buspasswithqrscan.network.SharedPreferenceManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home_ConductorFragment extends Fragment {
    private TextView bookedSeatsTextView;
    private TextView stopNameTextView, routeNoTextView, stopTimingTextView;
    private TextView remainingStopsTextView, totalStopsTextView;
    int conductorID= SharedPreferenceManager.getInstance().readInt("Id",0);
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home__conductor, container, false);
        stopNameTextView = rootView.findViewById(R.id.tvstopnamecon);
        routeNoTextView = rootView.findViewById(R.id.tv_route_nocon);
        stopTimingTextView = rootView.findViewById(R.id.tv_stop_timingcon);
        remainingStopsTextView = rootView.findViewById(R.id.tvremainingstopNO);
        totalStopsTextView = rootView.findViewById(R.id.tvtotalstopsNO);

        ImageButton button = rootView.findViewById(R.id.announcement);
        bookedSeatsTextView = rootView.findViewById(R.id.booked_seats_text_view);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new AnnouncementFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layoutconductor, fragment).commit();
            }
        });

        fetchBookedSeats(conductorID);
        fetchNextStopDetails();
        fetchJourneyStops(conductorID);
        return rootView;
    }

    private void fetchBookedSeats(int conductorId) {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<Integer> call = apiService.getBookedSeats(conductorId);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful() && response.body() != null) {
                    int bookedSeats = response.body();
                    bookedSeatsTextView.setText(String.valueOf(bookedSeats));
                } else {
                    Toast.makeText(getActivity(), "Failed to get booked seats", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toast.makeText(getActivity(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void fetchNextStopDetails() {
        // Create an instance of ApiService using RetrofitClient
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        // Call the appropriate method to fetch next stop details
        Call<StopModel> call = apiService.getNextStop(conductorID); // Pass the actual conductorId here

        // Make an asynchronous call
        call.enqueue(new Callback<StopModel>() {
            @Override
            public void onResponse(Call<StopModel> call, Response<StopModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    StopModel nextStop = response.body();
                    // Populate the TextViews with the fetched data
                    stopNameTextView.setText(nextStop.getName());
                    routeNoTextView.setText(nextStop.getRoute());
                    stopTimingTextView.setText(nextStop.getTiming());
                } else {
                    Toast.makeText(getActivity(), "Failed to get next stop details", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<StopModel> call, Throwable t) {
                Toast.makeText(getActivity(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchJourneyStops(int conductorId) {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<JourneyStopsChecker> call = apiService.getRemainingStops(conductorId);
        call.enqueue(new Callback<JourneyStopsChecker>() {
            @Override
            public void onResponse(Call<JourneyStopsChecker> call, Response<JourneyStopsChecker> response) {
                if (response.isSuccessful() && response.body() != null) {
                    JourneyStopsChecker journeyStopsChecker = response.body();
                    remainingStopsTextView.setText(String.valueOf(journeyStopsChecker.getRemainingStops()));
                    totalStopsTextView.setText(String.valueOf(journeyStopsChecker.getTotalStops()));
                } else {
                    Toast.makeText(getActivity(), "Failed to get journey stops", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<JourneyStopsChecker> call, Throwable t) {
                Toast.makeText(getActivity(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

