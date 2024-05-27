package com.example.buspasswithqrscan.Student;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.buspasswithqrscan.R;
import com.example.buspasswithqrscan.Student.model.NotificationModel;

public class Notification_details extends AppCompatActivity {

    CardView cardView;
    TextView tvtime,tvdate,tvDiscription,tv_stopTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_details);

        ImageButton backbutton=findViewById(R.id.ivback);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        NotificationModel model=(NotificationModel) getIntent().getSerializableExtra("model");
        Toast.makeText(this,""+model.getType(), Toast.LENGTH_SHORT).show();
        tvtime=findViewById(R.id.tvtime);
        tvdate=findViewById(R.id.tvdate);
        tvDiscription=findViewById(R.id.tvDiscription);
        tv_stopTitle=findViewById(R.id.tv_stopTitle);

        tv_stopTitle.setText(model.getType());
        tvDiscription.setText(model.getDescription());
        tvtime.setText(model.getTime());
        tvdate.setText(model.getDate());


    }
}