package com.example.buspasswithqrscan.Admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.buspasswithqrscan.Admin.Model.NotificationAdminModel;
import com.example.buspasswithqrscan.Conductor.NotificationAdapter_Conductor;
import com.example.buspasswithqrscan.Conductor.model.NotificationConductorModel;
import com.example.buspasswithqrscan.R;

import java.util.ArrayList;
import java.util.List;

public class NotificationAdminFragment extends Fragment {
    RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_notification_admin, container, false);

        recyclerView = view.findViewById(R.id.rcv_notificationAdmin);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new NotificationAdapter_Admin(setDummyData(), getContext()));

        return view;
    }

    private List<NotificationAdminModel> setDummyData() {
        List<NotificationAdminModel>notificationAdminModelList=new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            NotificationAdminModel model= new NotificationAdminModel("Journey Added for Hamid Basar.","Today","7:00AM");
            notificationAdminModelList.add(model);
        }
        return notificationAdminModelList;
    }

}