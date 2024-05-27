package com.example.buspasswithqrscan.Student;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buspasswithqrscan.R;
import com.example.buspasswithqrscan.Student.model.NotificationModel;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    List<NotificationModel> notificationModelsList;
    Context context;
    public NotificationAdapter(List<NotificationModel> notificationModelsList, Context context) {
        this.context= context;
        this.notificationModelsList = notificationModelsList;
    }


    @NonNull
    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_notification_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.ViewHolder holder, int position) {

        NotificationModel model= notificationModelsList.get(position);
        holder.tvNotificationTitle.setText(model.getType());
        holder.tvNotificationMsg.setText(model.getDescription());
       // holder.tvtime.setText(model.getTime());
        holder.tvdate.setText(model.getDate());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, Notification_details.class);
                intent.putExtra("model",model);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {

        return notificationModelsList.size();
    }

    public void updateData(List<NotificationModel> studentNotification) {
        this.notificationModelsList.clear();
        this.notificationModelsList.addAll(studentNotification);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

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
