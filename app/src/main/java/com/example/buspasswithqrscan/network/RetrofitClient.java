package com.example.buspasswithqrscan.network;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit;

    public static Retrofit getRetrofitInstance() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(logging).build();
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl("http://192.168.149.148/WebApi/api/").client(client).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
}