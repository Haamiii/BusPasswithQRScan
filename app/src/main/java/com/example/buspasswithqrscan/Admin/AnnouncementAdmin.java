package com.example.buspasswithqrscan.Admin;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.buspasswithqrscan.R;

public class AnnouncementAdmin extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_announcement_admin, container, false);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        ImageButton backbtn=view.findViewById(R.id.ivbackBtnAnnadmin);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment=new HomeAdminFragment();
                FragmentTransaction transaction=getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layoutAdmin,fragment).commit();
            }
        });


        return view;
    }
}