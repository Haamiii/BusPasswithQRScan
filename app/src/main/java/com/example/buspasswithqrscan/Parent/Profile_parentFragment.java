package com.example.buspasswithqrscan.Parent;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;
import android.widget.TextView;

import com.example.buspasswithqrscan.MainActivity;
import com.example.buspasswithqrscan.R;
import com.example.buspasswithqrscan.Parent.ChangePassword_parent;
import com.example.buspasswithqrscan.Parent.History_parent;
import com.example.buspasswithqrscan.network.SharedPreferenceManager;

import org.json.JSONException;
import org.json.JSONObject;

public class Profile_parentFragment extends Fragment {

    private TextView nameparent;
    private TextView contactparent;
    private TextView childenrollparent;
    private TextView parentidparent;
    private TextView usernameparent;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile_parent, container, false);

        nameparent=rootView.findViewById(R.id.nameparent);
        contactparent=rootView.findViewById(R.id.contactparent);
        childenrollparent=rootView.findViewById(R.id.childenrollparent);
        parentidparent=rootView.findViewById(R.id.parentidparent);
        usernameparent=rootView.findViewById(R.id.usernameparent);
        try {
            JSONObject object=new JSONObject(SharedPreferenceManager.getInstance().read("userParent",null));
            nameparent.setText(object.getString("Name"));
            contactparent.setText(object.getString("Contact"));
            childenrollparent.setText(object.getInt("ChildrenEnroll")+"");
            parentidparent.setText(object.getInt("Id")+"");
            usernameparent.setText(object.getString("UserName"));
        } catch (JSONException e) {
            Log.d("ero==>>",e.getMessage());
        }

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