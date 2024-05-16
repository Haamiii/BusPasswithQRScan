package com.example.buspasswithqrscan.Conductor;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.buspasswithqrscan.Conductor.model.NotificationConductorModel;
import com.example.buspasswithqrscan.R;

import java.util.ArrayList;
import java.util.List;

public class Notifications_ConductorFragment extends Fragment {

    RecyclerView recyclerView;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications__conductor, container, false);

        recyclerView = view.findViewById(R.id.rcv_notificationconductor);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new NotificationAdapter_Conductor(setDummyData(), getContext()));
        return view;
    }

    private List<NotificationConductorModel> setDummyData() {
        List<NotificationConductorModel> notificationConductorModelList= new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            NotificationConductorModel model= new NotificationConductorModel("Checked Out!", "Checkout At University", i+"/4/2020",i+":30AM");
            notificationConductorModelList.add(model);
        }


        return notificationConductorModelList;
    }
}
