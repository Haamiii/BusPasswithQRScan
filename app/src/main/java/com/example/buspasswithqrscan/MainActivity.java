package com.example.buspasswithqrscan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.buspasswithqrscan.Admin.Admin_dashboard;
import com.example.buspasswithqrscan.Conductor.Conductor_Dashboard;
import com.example.buspasswithqrscan.Student.Student_dashboard;
import com.example.buspasswithqrscan.Parent.Parent_dashboard;

public class MainActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);

        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check the content of the username EditText
                String userType = usernameEditText.getText().toString();

                // Check if the userType is "student" or "parent"
                if (userType.equals("student")) {
                    startActivity(Student_dashboard.class);
                } else if (userType.equals("parent")) {
                    startActivity(Parent_dashboard.class);
                }else if (userType.equals("conductor")) {
                    startActivity(Conductor_Dashboard.class);
                }else if (userType.equals("admin")) {
                    startActivity(Admin_dashboard.class);
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Invalid username", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void startActivity(Class<?> cls) {
        Intent intent = new Intent(MainActivity.this, cls);
        startActivity(intent);
    }
}
