package com.example.buspasswithqrscan.network;

public class ApiResponse<T> {
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
