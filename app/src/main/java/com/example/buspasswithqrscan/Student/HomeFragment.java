package com.example.buspasswithqrscan.Student;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.buspasswithqrscan.R;
import com.example.buspasswithqrscan.network.SharedPreferenceManager;
import com.example.buspasswithqrscan.Student.model.Favourite_stopModel;
import com.example.buspasswithqrscan.network.ApiService;
import com.example.buspasswithqrscan.network.RetrofitClient;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    TextView ivjourneyStudent,ivtotaljourneyStudent;
    ProgressBar prograssbarstudent;

    Button editFrvtStop;
    ViewPager2 viewPager2;
    TabLayout tabLayout;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        editFrvtStop = view.findViewById(R.id.editFrvtStop);
        prograssbarstudent=view.findViewById(R.id.prograssbarstudent);
        editFrvtStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Favourite_Stops.class));
            }
        });

        viewPager2 = view.findViewById(R.id.view_pager);
        tabLayout = view.findViewById(R.id.tab_layout);
        fetchFavouriteStops();

        ivjourneyStudent=view.findViewById(R.id.ivjourneyStudent);
        ivtotaljourneyStudent=view.findViewById(R.id.ivtotaljourneyStudent);

        try {
            JSONObject object=new JSONObject(SharedPreferenceManager.getInstance().read("user",null));
            ivjourneyStudent.setText(object.getInt("RemainingJourneys")+"");
            ivtotaljourneyStudent.setText("/"+object.getInt("TotalJourneys")+"");
            prograssbarstudent.setMax(object.getInt("TotalJourneys"));
            prograssbarstudent.setProgress(object.getInt("RemainingJourneys"));
        } catch (JSONException e) {
            Log.d("ero==>>",e.getMessage());
        }

        return view;
    }


    private void fetchFavouriteStops() {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        try {
            JSONObject object = new JSONObject(SharedPreferenceManager.getInstance().read("user", null));

            Call<List<Favourite_stopModel>> call = apiService.getFavStops(object.getInt("Id"));

            call.enqueue(new Callback<List<Favourite_stopModel>>() {
                @Override
                public void onResponse(Call<List<Favourite_stopModel>> call, Response<List<Favourite_stopModel>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        List<Favourite_stopModel> favouriteStopList = response.body();
                        viewPager2.setAdapter(new TabAdapter(favouriteStopList));
                        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
                        }).attach();
                    } else {
                        Toast.makeText(getContext(), "No Favourite Stops Found!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<Favourite_stopModel>> call, Throwable t) {
                    Log.e("Favourite_Stops", "Error fetching favourite stops", t);
                    Toast.makeText(getContext(), "Error fetching data", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }


}