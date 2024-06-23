package com.example.buspasswithqrscan.network;

import com.example.buspasswithqrscan.Admin.Model.ApiStops;
import com.example.buspasswithqrscan.Admin.Model.BusLocations;
import com.example.buspasswithqrscan.Admin.Model.Route;
import com.example.buspasswithqrscan.Conductor.model.JourneyStopsChecker;
import com.example.buspasswithqrscan.Conductor.model.RouteModel;
import com.example.buspasswithqrscan.Parent.model.ChildrenLocation;
import com.example.buspasswithqrscan.Parent.model.Childsparent_Model;
import com.example.buspasswithqrscan.Parent.model.HistoryModelParent;
import com.example.buspasswithqrscan.Parent.model.NotificationParentModel;
import com.example.buspasswithqrscan.Student.model.BusLocation;
import com.example.buspasswithqrscan.Student.model.Favourite_stopModel;
import com.example.buspasswithqrscan.Student.model.HistoryModel;
import com.example.buspasswithqrscan.Student.model.StopModel;
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
    @GET("Users/GetUserNotification")
    Call<List<NotificationParentModel>> getUserNotifications(@Query("id") int userId);


    @POST("Student/AddFavStop")
    Call<String> AddFavStop(@Query("studentId") int studentId, @Query("stopId") int stopId);
    @DELETE("Student/RemoveFavStop")
    Call<String>RemoveFavStop(@Query("studentId")int studentId, @Query("stopId")int stopId);
    @GET("Users/GetUserHistory")
    Call<List<HistoryModel>> getUserHistory(@Query("id") int userId, @Query("fDate") String fromDate, @Query("tDate") String toDate);

    @PUT("Users/ChangePassword")
    Call<ResponseBody> changePassword(@Body RequestBody requestBody);
    @PUT("Users/ChangePassword")
    Call<ResponseBody> changePasswordParent(@Body RequestBody requestBody);

    @Headers("Content-Type: application/json")
    @GET("Stops/GetAllRoutes")
    Call<List<List<StopModel>>> getAllRoutes(@Query("OrganizationId") int organizationId);
    @Headers("Content-Type: application/json")
    @GET("Stops/GetAllRoutes")
    Call<List<List<ApiStops>>> getAllRoute(@Query("OrganizationId") int organizationId);

    @GET("Bus/GetBusesLocations")
    Call<List<BusLocation>> getBusesLocations(@Query("OrganizationId") int organizationId);

//    @Headers("Content-Type: application/json")
//    @GET("Users/GetChildrenByParentId")
//    Call<List<Student>> getChildrenByParentId(@Query("parentId") int parentId);

    @GET("Users/GetUserHistory")
    Call<List<List<HistoryModelParent>>> getUserHistoryParent(@Query("id") int userId, @Query("fDate") String fromDate, @Query("tDate") String toDate);

    @GET("Parent/GetChildren")
    Call<List<Childsparent_Model>> getChildrenByParentId(@Query("Id") int parentId);
    @GET("Parent/GetChildLocation")
    Call<List<ChildrenLocation>> getChildLocation(@Query("id") int parentId);

    @GET("Conductor/GetAssignedRoutes")
    Call<List<RouteModel>> getAssignedRoutes(@Query("conductorId") int conductorId);

    @POST("Conductor/StartJourney")
    Call<ResponseBody> startJourney(@Query("busId") int busId, @Query("routeId") int routeId);
    @POST("Conductor/UpdateBusLocation")
    Call<ResponseBody> updateBusLocation(@Body com.example.buspasswithqrscan.Conductor.model.BusLocation busLocation);
    @GET("Conductor/ScanQrCode")
    Call<ResponseBody> scanQrCode(@Query("passId") int passId, @Query("busId") int busId);
    @POST("Conductor/ReachedAtStop")
    Call<ResponseBody> ReachedAtStop(@Query("busId") int busId, @Query("routeId") int routeId, @Query("stopId") int stopId);
    @GET("Conductor/GetBookedSeats")
    Call<Integer> getBookedSeats(@Query("conductorId") int conductorId);
    @GET("Conductor/GetNextStop")
    Call<com.example.buspasswithqrscan.Conductor.model.StopModel> getNextStop(@Query("conductorId") int conductorId);
    @GET("Conductor/GetRemainingStops")
    Call<JourneyStopsChecker> getRemainingStops(@Query("conductorId") int conductorId);

    @POST("Admin/InsertStop")
    Call<String> insertStop(@Body ApiStops stop);

    @GET("Stops/GetAllStops")
    Call<List<ApiStops>> getAllStops();

    @POST("Admin/InsertRoute")
    Call<String> insertRoute(@Body Route route);

    @GET("Bus/GetBusesLocations")
    Call<List<BusLocations>> getBusesLocation(@Query("OrganizationId") int organizationId);
}
