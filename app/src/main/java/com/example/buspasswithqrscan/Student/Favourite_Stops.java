package com.example.buspasswithqrscan.Student;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.buspasswithqrscan.R;
import com.example.buspasswithqrscan.Student.model.Favourite_stopModel;
import com.example.buspasswithqrscan.Student.model.NotificationModel;

import java.util.ArrayList;
import java.util.List;

public class Favourite_Stops extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_stops);

        recyclerView=findViewById(R.id.rcv_fouritestops);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Favourite_stopModel> dummyData = setDummyData();
        Favourite_StopsAdapter adapter = new Favourite_StopsAdapter(dummyData, this);
        recyclerView.setAdapter(adapter);
        ImageButton backButton= findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Favourite_Stops.this, Student_dashboard.class );
                startActivity(intent);
            }
        });

    }

    private List<Favourite_stopModel> setDummyData() {
        List<Favourite_stopModel> favourite_stopModelList= new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            Favourite_stopModel favouriteStopModel = new Favourite_stopModel(1,"Saddar",false);
            favourite_stopModelList.add(favouriteStopModel);
        }
        return favourite_stopModelList;
    }
}