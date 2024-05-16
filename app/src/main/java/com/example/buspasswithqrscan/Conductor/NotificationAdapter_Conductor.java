package com.example.buspasswithqrscan.Conductor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buspasswithqrscan.Conductor.model.NotificationConductorModel;
import com.example.buspasswithqrscan.R;

import java.util.List;

public class NotificationAdapter_Conductor extends RecyclerView.Adapter<NotificationAdapter_Conductor.ViewHolder> {
    List<NotificationConductorModel> notificationConductorModelList;
    Context context;

    public NotificationAdapter_Conductor(List<NotificationConductorModel> notificationConductorModelList, Context context) {
        this.notificationConductorModelList = notificationConductorModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public NotificationAdapter_Conductor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
     View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_conductor_notification_item,parent,false);
        return new NotificationAdapter_Conductor.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter_Conductor.ViewHolder holder, int position) {
        NotificationConductorModel model=notificationConductorModelList.get(position);
        holder.tvNotificationTitle.setText(model.getTitle());
        holder.tvNotificationMsg.setText(model.getMsg());
        holder.tvtime.setText(model.getTime());
        holder.tvdate.setText(model.getDate());


    }

    @Override
    public int getItemCount() {
        return notificationConductorModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView tvNotificationTitle,tvNotificationMsg,tvtime,tvdate;
        ImageView ivNotificationIcon;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView=itemView.findViewById(R.id.cvNotificationWrapper);
            tvNotificationTitle=itemView.findViewById(R.id.tvNotificationTitle);
            tvNotificationMsg=itemView.findViewById(R.id.tvNotificationMsg);
            tvtime=itemView.findViewById(R.id.tvtime);
            tvdate=itemView.findViewById(R.id.tvdate);
            ivNotificationIcon=itemView.findViewById(R.id.ivNotificationIcon);
        }
    }
}
