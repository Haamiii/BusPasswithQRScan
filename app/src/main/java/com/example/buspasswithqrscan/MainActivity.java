package com.example.buspasswithqrscan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.buspasswithqrscan.Admin.Admin_dashboard;
import com.example.buspasswithqrscan.Conductor.Conductor_Dashboard;
import com.example.buspasswithqrscan.Student.Student_dashboard;
import com.example.buspasswithqrscan.Parent.Parent_dashboard;
import com.example.buspasswithqrscan.network.ApiService;
import com.example.buspasswithqrscan.network.RetrofitClient;
import com.example.buspasswithqrscan.network.SharedPreferenceManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private ImageView passwordToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        passwordToggle = findViewById(R.id.password_toggle);
        passwordToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (passwordEditText.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                    // Show Password
                    passwordEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    passwordToggle.setImageResource(R.drawable.ic_eye_on); // Change to the eye-open icon
                } else {
                    // Hide Password
                    passwordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    passwordToggle.setImageResource(R.drawable.ic_eye_off); // Change to the eye-closed icon
                }
            }
        });

        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userType = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                login(userType,password);

            }
        });
    }

    private void login(String username, String password) {
        RetrofitClient.getRetrofitInstance().create(ApiService.class).doGetRequest("Users/Login?",getParams(username,password)).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        JSONObject object=new JSONObject(response.body().string());
                        String role = object.getString("userRole");
                        SharedPreferenceManager.setSingletonInstance(MainActivity.this);
                        JSONObject studentObject=object.getJSONObject("Students");
                        SharedPreferenceManager.getInstance().save("user",studentObject.toString());
                        if (studentObject.has("UserId")) {
                            int userId = studentObject.getInt("UserId");
                            SharedPreferenceManager.getInstance().save("userId", userId);
                        } else {
                            Log.d("MainActivity", "UserId not found in the response");
                        }
                        if (studentObject.has("PassId")) {
                            int PassId = studentObject.getInt("PassId");
                            SharedPreferenceManager.getInstance().save("PassId",PassId);
                        } else {
                            Log.d("MainActivity", "PassId not found in the response");
                        }
                        if (role == null){
                            Toast.makeText(MainActivity.this, "User Role Not Found", Toast.LENGTH_SHORT).show();
                        } else if (role.equals("Parent")) {
                            startActivity(Parent_dashboard.class);
                        } else if (role.equals("Student")) {
                            startActivity(Student_dashboard.class);
                        } else if (role.equals("Admin")){
                            startActivity(Admin_dashboard.class);
                        } else if (role.equals("Conductor")) {
                            startActivity(Conductor_Dashboard.class);
                        } else {
                            Toast.makeText(MainActivity.this, "User Not Found", Toast.LENGTH_SHORT).show();
                        }
                    }
                 catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (JSONException e) {
                        Toast.makeText(MainActivity.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Log.d("",t.getMessage());
            }
        });

    }
    private Map<String , String> getParams(String username,String pass){
        Map<String,String> params=new HashMap<>();
        params.put("username",username);
        params.put("password",pass);
        return params;
    }

    private void startActivity(Class<?> cls) {
        Intent intent = new Intent(MainActivity.this, cls);
        startActivity(intent);
    }
}
//change when its not run