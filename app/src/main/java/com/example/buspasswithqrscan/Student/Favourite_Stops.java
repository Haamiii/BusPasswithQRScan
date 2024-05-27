package com.example.buspasswithqrscan.Student;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buspasswithqrscan.R;
import com.example.buspasswithqrscan.network.SharedPreferenceManager;
import com.example.buspasswithqrscan.Student.model.Favourite_stopModel;
import com.example.buspasswithqrscan.network.ApiService;
import com.example.buspasswithqrscan.network.RetrofitClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Favourite_Stops extends AppCompatActivity implements Favourite_StopsAdapter.OnItemCheckedChangeListener {

    RecyclerView recyclerView;
    Favourite_StopsAdapter adapter;
    private List<Favourite_stopModel> favouriteStopModelList;
    JSONObject object;
    TextView tvremoveStudent;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_stops);

        recyclerView = findViewById(R.id.rcv_fouritestops);
        tvremoveStudent=findViewById(R.id.tvremoveStudent);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        favouriteStopModelList = new ArrayList<>(); // Initialize your list here
        try {
            object = new JSONObject(SharedPreferenceManager.getInstance().read("user", null));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Favourite_Stops.this, Student_dashboard.class);
                startActivity(intent);

            }
        });
        tvremoveStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             for(int i=0; i< favouriteStopModelList.size();i++ )
             {
                 Favourite_stopModel frvt=  favouriteStopModelList.get(i);
                 try {
                     RemoveFavStop(object.getInt("Id"),frvt.getId());
                 } catch (JSONException e) {
                     throw new RuntimeException(e);
                 }
             }
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchFavouriteStops();
    }

    private void RemoveFavStop(int studentId, int stopId) {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        apiService.RemoveFavStop(studentId, stopId).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {

                    Toast.makeText(Favourite_Stops.this, response.body(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Favourite_Stops.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(Favourite_Stops.this, "Got fail.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchFavouriteStops() {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        try {


            Call<List<Favourite_stopModel>> call = apiService.getFavStops(object.getInt("Id")); // Replace 1 with the actual student ID

            call.enqueue(new Callback<List<Favourite_stopModel>>() {
                @Override
                public void onResponse(Call<List<Favourite_stopModel>> call, Response<List<Favourite_stopModel>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        List<Favourite_stopModel> favouriteStopList = response.body();
                        adapter = new Favourite_StopsAdapter(favouriteStopList, Favourite_Stops.this);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Toast.makeText(Favourite_Stops.this, "No Favourite Stops Found!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<Favourite_stopModel>> call, Throwable t) {
                    Log.e("Favourite_Stops", "Error fetching favourite stops", t);
                    Toast.makeText(Favourite_Stops.this, "Error fetching data", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onItemCheckedChanged(int position, boolean isChecked, Favourite_stopModel model) {

        if (isChecked)
            favouriteStopModelList.add(model);
        else
            favouriteStopModelList.remove(model);
    }
}
