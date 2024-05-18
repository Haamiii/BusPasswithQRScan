package com.example.buspasswithqrscan.Admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buspasswithqrscan.Admin.Model.NotificationAdminModel;
import com.example.buspasswithqrscan.Conductor.NotificationAdapter_Conductor;
import com.example.buspasswithqrscan.R;

import java.util.List;

public class NotificationAdapter_Admin extends RecyclerView.Adapter<NotificationAdapter_Admin.ViewHolder> {
    List<NotificationAdminModel> notificationAdminModelsList;
    Context context;

    public NotificationAdapter_Admin(List<NotificationAdminModel> notificationAdminModelsList, Context context) {
        this.notificationAdminModelsList = notificationAdminModelsList;
        this.context = context;
    }

    @NonNull
    @Override
    public NotificationAdapter_Admin.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_admin_notification_items,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter_Admin.ViewHolder holder, int position) {
        NotificationAdminModel model=notificationAdminModelsList.get(position);
        holder.tvNotificationMsg.setText(model.getMsg());
        holder.tvtime.setText(model.getTime());
        holder.tvdate.setText(model.getDate());
    }

    @Override
    public int getItemCount() {
        return notificationAdminModelsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView tvNotificationMsg,tvtime,tvdate;
        ImageView ivNotificationIcon;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView=itemView.findViewById(R.id.cvNotificationWrapperadmin);
            tvNotificationMsg=itemView.findViewById(R.id.tvNotificationMsgadmin);
            tvtime=itemView.findViewById(R.id.tvtimeadmin);
            tvdate=itemView.findViewById(R.id.tvdateadmin);
            ivNotificationIcon=itemView.findViewById(R.id.ivNotificationIconadmin);
        }
    }
}
