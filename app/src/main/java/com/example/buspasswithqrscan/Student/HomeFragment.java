package com.example.buspasswithqrscan.Student;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.buspasswithqrscan.R;
import com.example.buspasswithqrscan.Student.model.StopModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    Button editFrvtStop;
    ViewPager2 viewPager2;
    TabLayout tabLayout;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        editFrvtStop= view.findViewById(R.id.editFrvtStop);
        editFrvtStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Favourite_Stops.class));
            }
        });
        viewPager2 = view.findViewById(R.id.view_pager);
        tabLayout = view.findViewById(R.id.tab_layout);
        viewPager2.setAdapter(new TabAdapter(setDummyData()));
        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
        }).attach();
        return view;
    }

    private List<StopModel> setDummyData() {
        List<StopModel> stopModelList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            StopModel stopModel = new StopModel("Chandni Chowk", "08:20 AM", i);
            stopModelList.add(stopModel);
        }
        return stopModelList;
    }


}