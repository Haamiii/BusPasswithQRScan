package com.example.buspasswithqrscan.Admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.buspasswithqrscan.Admin.Model.BusModel;
import com.example.buspasswithqrscan.R;
import com.example.buspasswithqrscan.Student.TabAdapter;
import com.example.buspasswithqrscan.Student.model.StopModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class HomeAdminFragment extends Fragment {

    ViewPager2 viewPager2;
    TabLayout tabLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home_admin, container, false);


        viewPager2 = view.findViewById(R.id.view_pageradmin);
        tabLayout = view.findViewById(R.id.tab_layoutadmin);
        viewPager2.setAdapter(new BusAdapter(setDummydata()));
        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
        }).attach();
        return view;
    }

    private List<BusModel> setDummydata() {
        List<BusModel> busModelList=new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            BusModel model=new BusModel(i+"0",i+5,20-i);
            busModelList.add(model);
        }

        return busModelList;
    }

}