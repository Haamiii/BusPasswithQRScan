package com.example.buspasswithqrscan.Conductor;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.buspasswithqrscan.Parent.Profile_parentFragment;
import com.example.buspasswithqrscan.R;

public class ChangePassword_ConductorFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_change_password__conductor, container, false);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        ImageButton imageButton=view.findViewById(R.id.ivbackBtnnc);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment= new Profile_ConductorFragment();
                FragmentTransaction transaction=getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layoutconductor,fragment).commit();
            }
        });


        return view;
    }
}