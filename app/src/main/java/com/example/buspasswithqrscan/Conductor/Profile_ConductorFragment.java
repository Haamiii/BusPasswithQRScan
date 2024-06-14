package com.example.buspasswithqrscan.Conductor;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.buspasswithqrscan.MainActivity;
import com.example.buspasswithqrscan.Parent.ChangePassword_parent;
import com.example.buspasswithqrscan.Parent.History_parent;
import com.example.buspasswithqrscan.R;
import com.example.buspasswithqrscan.network.SharedPreferenceManager;

import org.json.JSONException;
import org.json.JSONObject;

public class Profile_ConductorFragment extends Fragment {

    private TextView nameconductor;
    private TextView tvcontactNoConductor;
    private TextView tvusernameConductor;
    private TextView tvidConductor;
    private TextView tvBusNoConductor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
     View rootView=inflater.inflate(R.layout.fragment_profile__conductor, container, false);

     nameconductor=rootView.findViewById(R.id.nameconductor);
     tvcontactNoConductor=rootView.findViewById(R.id.tvcontactNoConductor);
     tvusernameConductor=rootView.findViewById(R.id.tvusernameConductor);
     tvidConductor=rootView.findViewById(R.id.tvidConductor);
     tvBusNoConductor=rootView.findViewById(R.id.tvBusNoConductor);

        try {
            JSONObject object=new JSONObject(SharedPreferenceManager.getInstance().read("userConductor",null));
            nameconductor.setText(object.getString("Name"));
            tvcontactNoConductor.setText(object.getString("Contact"));
            tvusernameConductor.setText(object.getString("UserName"));
            tvidConductor.setText(object.getInt("Id")+"");
            tvBusNoConductor.setText(object.getString("BusRegNo"));
        } catch (JSONException e) {
            Log.d("ero==>>",e.getMessage());
        }

        rootView.findViewById(R.id.historybtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment history = new History_Conductor();
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.frame_layoutconductor, history)
                        .addToBackStack(null) // This line allows the user to navigate back to the previous fragment
                        .commit();
            }
        });
        rootView.findViewById(R.id.changepssbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment changepassFragment= new ChangePassword_ConductorFragment();
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