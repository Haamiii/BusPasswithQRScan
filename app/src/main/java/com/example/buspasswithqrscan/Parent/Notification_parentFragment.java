package com.example.buspasswithqrscan.Parent;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.buspasswithqrscan.Parent.model.NotificationParentModel;
import com.example.buspasswithqrscan.R;
import com.example.buspasswithqrscan.Student.model.NotificationModel;

import java.util.ArrayList;
import java.util.List;

public class Notification_parentFragment extends Fragment {

    RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_notification_parent, container, false);

        recyclerView=view.findViewById(R.id.rcv_notification);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new NotificationAdapter_parent(setDummyData(),getContext()));
        return view;
    }

    private List<NotificationParentModel> setDummyData() {
        List<NotificationParentModel> notificationModelsList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            NotificationParentModel notificationParentModel = new NotificationParentModel("Checked Out!", "Checkout At University", i+"/4/2020",i+":30AM");
            notificationModelsList.add(notificationParentModel);
        }
        return notificationModelsList;
    }
}