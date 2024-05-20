package com.example.buspasswithqrscan.Admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.buspasswithqrscan.R;

public class AddAdminFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.fragment_add_admin, container, false);


       Button searchupdate=view.findViewById(R.id.btnsearchupdate);
       searchupdate.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Fragment searchupdate= new Searchupdate_add_adminFragment();
               getParentFragmentManager().beginTransaction().replace(R.id.frame_layoutAdmin,searchupdate).commit();
           }
       });

       Button addnewstudent=view.findViewById(R.id.btnnewstudet);
       addnewstudent.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Fragment addnewstud=new AddNewStudent_add_admin();
               getParentFragmentManager().beginTransaction().replace(R.id.frame_layoutAdmin,addnewstud).commit();

           }
       });

       Button addnewconductor=view.findViewById(R.id.btnnewconductor);
       addnewconductor.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Fragment addnewconductor=new AddNewConductor_add_admin();
               getParentFragmentManager().beginTransaction().replace(R.id.frame_layoutAdmin,addnewconductor).commit();
           }
       });
       Button addnewadmin=view.findViewById(R.id.btnnewadmin);
       addnewadmin.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Fragment addnewadmin=new AddNewAdmin_add_admin();
               getParentFragmentManager().beginTransaction().replace(R.id.frame_layoutAdmin,addnewadmin).commit();

           }
       });

       Button addnewbus=view.findViewById(R.id.btnnewbusses);
       addnewbus.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Fragment addnewbus=new AddNewBus_add_admin();
               getParentFragmentManager().beginTransaction().replace(R.id.frame_layoutAdmin,addnewbus).commit();

           }
       });
       Button addnewjourney=view.findViewById(R.id.btnrechargejourney);
       addnewjourney.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Fragment addnewjourney=new AddMoreJourneys_add_admin();
               getParentFragmentManager().beginTransaction().replace(R.id.frame_layoutAdmin,addnewjourney).commit();

           }
       });

       return view;

    }
}