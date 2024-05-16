package com.example.buspasswithqrscan.Conductor;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.buspasswithqrscan.MainActivity;
import com.example.buspasswithqrscan.Parent.ChangePassword_parent;
import com.example.buspasswithqrscan.Parent.History_parent;
import com.example.buspasswithqrscan.R;
public class Profile_ConductorFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
     View rootView=inflater.inflate(R.layout.fragment_profile__conductor, container, false);

        rootView.findViewById(R.id.historybtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment history = new History_parent();
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.frame_layoutconductor, history)
                        .addToBackStack(null) // This line allows the user to navigate back to the previous fragment
                        .commit();
            }
        });
        rootView.findViewById(R.id.changepssbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment changepassFragment= new ChangePassword_parent();
                getParentFragmentManager().beginTransaction().replace(R.id.frame_layoutconductor,changepassFragment).commit();
            }
        });
        rootView.findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });



        return rootView;
    }
}