package com.example.buspasswithqrscan.Admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.example.buspasswithqrscan.R;
public class AddNewBus_add_admin extends Fragment {

    Spinner spinner;
    String[] category={"Route ID","null","null"};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_add_new_bus_add_admin, container, false);

        spinner=view.findViewById(R.id.spcategorys_uaddAdmin);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item,category);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        ImageButton backbutton=view.findViewById(R.id.icbackButnNBadmin);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment=new AddAdminFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layoutAdmin, fragment).commit();
            }
        });

        return view;
    }
}