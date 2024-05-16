package com.example.buspasswithqrscan.Parent;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;

import com.example.buspasswithqrscan.MainActivity;
import com.example.buspasswithqrscan.R;
import com.example.buspasswithqrscan.Parent.ChangePassword_parent;
import com.example.buspasswithqrscan.Parent.History_parent;

public class Profile_parentFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile_parent, container, false);

        rootView.findViewById(R.id.historybtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment history = new History_parent();
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout_parent, history)
                        .addToBackStack(null) // This line allows the user to navigate back to the previous fragment
                        .commit();
            }
        });
        rootView.findViewById(R.id.changepssbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment changepassFragment= new ChangePassword_parent();
                getParentFragmentManager().beginTransaction().replace(R.id.frame_layout_parent,changepassFragment).commit();
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