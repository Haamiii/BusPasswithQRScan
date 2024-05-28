package com.example.buspasswithqrscan.network;


import com.example.buspasswithqrscan.Student.model.Favourite_stopModel;
import com.example.buspasswithqrscan.Student.model.HistoryModel;
import com.example.buspasswithqrscan.Student.model.Student;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface ApiService {
    @Headers("Content-Type: application/json")
    @GET
    Call<ResponseBody> doGetRequest(@Url String url, @QueryMap Map<String, String> queryParams);

    @Headers("Content-Type: application/json")
    @GET
    Call<ResponseBody> doGetRequest(@Url String url);

    @Headers("Content-Type: application/json")
    @POST
    Call<ResponseBody> doPostRequest(@Url String url, @Body RequestBody object);

    @Headers("Content-Type: application/json")
    @PUT
    Call<ResponseBody> doPutRequest(@Url String url, @Body RequestBody object);

    @Headers("Content-Type: application/json")
    @POST
    Call<ResponseBody> doPostRequest(@Url String url, @QueryMap Map<String, Object> queryParams);

    //@Headers("Content-Type: application/json")
    @Multipart
    @POST
    Call<ResponseBody> doPostRequestWithMapBody(@Url String url
            , @PartMap Map<String, RequestBody> partBody
            , @Part List<MultipartBody.Part> imagePart);

    @GET("Student")
    Call<ApiResponse<Student>> getStudentDetails(@Query("id") int studentId);
    @GET("Student/GetFavStops")
    Call<List<Favourite_stopModel>> getFavStops(@Query("id") int studentId);

    @GET("Users/GetUserNotification")
    Call<ResponseBody> GetUserNotification(@Query("id")int userId);


    @POST("Student/AddFavStop")
    Call<String>AddFavStop(@Query("studentId")int studentId, @Query("stopId")int stopId);
    @DELETE("Student/RemoveFavStop")
    Call<String>RemoveFavStop(@Query("studentId")int studentId, @Query("stopId")int stopId);
    @GET("Users/GetUserHistory")
    Call<List<HistoryModel>> getUserHistory(@Query("id") int userId, @Query("fDate") String fromDate, @Query("tDate") String toDate);

    @PUT("Users/ChangePassword")
    Call<ResponseBody> changePassword(@Body RequestBody requestBody);


}
