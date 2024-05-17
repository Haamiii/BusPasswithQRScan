package com.example.buspasswithqrscan.Admin;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.buspasswithqrscan.MainActivity;
import com.example.buspasswithqrscan.R;

public class ProfileAdminFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview=inflater.inflate(R.layout.fragment_profile_admin, container, false);

       rootview.findViewById(R.id.changepssbtnadmin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment changePasswordAdmin= new ChangePasswordAdmin();
                getParentFragmentManager().beginTransaction().replace(R.id.frame_layoutAdmin,changePasswordAdmin).commit();
            }
        });
        rootview.findViewById(R.id.logoutadmin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        return rootview;
    }
}