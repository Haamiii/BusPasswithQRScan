package com.example.buspasswithqrscan.Parent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.buspasswithqrscan.Parent.model.NotificationParentModel;
import com.example.buspasswithqrscan.R;

public class Notificationparent_details extends AppCompatActivity {
    CardView cardView;
    TextView tvtime,tvdate,tvDiscription,tv_stopTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificationparent_details);

        ImageButton backbutton=findViewById(R.id.ivback);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        NotificationParentModel model=(NotificationParentModel) getIntent().getSerializableExtra("model");
        Toast.makeText(this,""+model.getTitle(), Toast.LENGTH_SHORT).show();
        tvtime=findViewById(R.id.tvtime);
        tvdate=findViewById(R.id.tvdate);
        tvDiscription=findViewById(R.id.tvDiscription);
        tv_stopTitle=findViewById(R.id.tv_stopTitle);

        tv_stopTitle.setText(model.getTitle());
        tvDiscription.setText(model.getMsg());
        tvtime.setText(model.getTime());
        tvdate.setText(model.getDate());

    }
}