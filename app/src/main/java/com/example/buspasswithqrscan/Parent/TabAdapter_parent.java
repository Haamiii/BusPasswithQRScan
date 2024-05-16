package com.example.buspasswithqrscan.Parent;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buspasswithqrscan.Parent.model.Childsparent_Model;
import com.example.buspasswithqrscan.R;
import com.example.buspasswithqrscan.Student.TabAdapter;

import java.util.List;

public class TabAdapter_parent extends RecyclerView.Adapter<TabAdapter_parent.ViewHolder> {
    List<Childsparent_Model> childsparentModelList;
    public TabAdapter_parent(List<Childsparent_Model> list) {
        this.childsparentModelList=list;
    }

    @NonNull
    @Override
    public TabAdapter_parent.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.imageparemt_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TabAdapter_parent.ViewHolder holder, int position) {
        Childsparent_Model model=childsparentModelList.get(position);
        holder.tvname.setText(model.getName());
        holder.tvpickcheckintime.setText(model.getPickcheckinTime());
        holder.tvpickcheckouttime.setText(model.getPickcheckoutTime());
        holder.tvdropcheckintime.setText(model.getDropcheckinTime());
        holder.tvdropcheckouttime.setText(model.getDropcheckouttime());
    }

    @Override
    public int getItemCount() {
        return childsparentModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvname,tvpickcheckintime,tvpickcheckouttime,tvdropcheckintime,tvdropcheckouttime;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvname=itemView.findViewById(R.id.tv_name);
            tvpickcheckintime=itemView.findViewById(R.id.tv_pickcheckin_time);
            tvpickcheckouttime=itemView.findViewById(R.id.tv_pickcheckout_time);
            tvdropcheckintime=itemView.findViewById(R.id.tv_dropcheckin_time);
            tvdropcheckouttime=itemView.findViewById(R.id.tv_dropcheckout_time);

        }
    }
}
