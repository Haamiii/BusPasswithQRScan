package com.example.buspasswithqrscan.Parent;
import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.buspasswithqrscan.R;
import com.example.buspasswithqrscan.Student.ProfileFragment;

public class ChangePassword_parent extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_change_password_parent, container, false);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        ImageButton backbutton=view.findViewById(R.id.ivbackBtnn);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Fragment fragment= new Profile_parentFragment();
                FragmentTransaction transaction=getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout_parent,fragment).commit();
            }
        });
        return view;
    }
}