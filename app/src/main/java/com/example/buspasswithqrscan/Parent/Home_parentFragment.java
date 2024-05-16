package com.example.buspasswithqrscan.Parent;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.buspasswithqrscan.Parent.model.Childsparent_Model;
import com.example.buspasswithqrscan.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class Home_parentFragment extends Fragment {

    ViewPager2 viewPager2;
    TabLayout tabLayout;

    @SuppressLint("MissingInflatedId")
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home_parent, container, false);



        viewPager2 = view.findViewById(R.id.view_pager_parent);
        tabLayout = view.findViewById(R.id.tab_layout_parent);
        viewPager2.setAdapter(new TabAdapter_parent(setDummyData()));
        new TabLayoutMediator(tabLayout,viewPager2,((tab, position) -> {})).attach();

        return view;
    }

    private List<Childsparent_Model> setDummyData() {
        List<Childsparent_Model> Childsparent_Modellist= new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Childsparent_Model childsparentModel= new Childsparent_Model("Hamid Basar","8:00","8:30","4:00","4:30");
            Childsparent_Modellist.add(childsparentModel);
        }

        return Childsparent_Modellist;
    }
}