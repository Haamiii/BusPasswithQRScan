package com.example.buspasswithqrscan.Parent;


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

import com.example.buspasswithqrscan.Parent.model.NotificationParentModel;
import com.example.buspasswithqrscan.R;

import java.util.List;

public class NotificationAdapter_parent extends RecyclerView.Adapter<NotificationAdapter_parent.ViewHolder> {
    List<NotificationParentModel> notificationModelsList;
    Context context;
    public NotificationAdapter_parent(List<NotificationParentModel> notificationModelsList, Context context) {
        this.context= context;
        this.notificationModelsList = notificationModelsList;
    }

    @NonNull
    @Override
    public NotificationAdapter_parent.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_notification_item, parent, false);
        return new NotificationAdapter_parent.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter_parent.ViewHolder holder, int position) {

        NotificationParentModel model= notificationModelsList.get(position);
        holder.tvNotificationTitle.setText(model.getTitle());
        holder.tvNotificationMsg.setText(model.getMsg());
        holder.tvtime.setText(model.getTime());
        holder.tvdate.setText(model.getDate());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,Notificationparent_details.class);
                intent.putExtra("model",model);
                context.startActivity(intent);
            }
        });
        

        //        holder.cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(context, Notificationparent_details.class);
//                intent.putExtra("model",model);
//                context.startActivity(intent);
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return notificationModelsList.size();
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
