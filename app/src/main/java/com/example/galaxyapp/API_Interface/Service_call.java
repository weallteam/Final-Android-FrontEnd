package com.example.galaxyapp.API_Interface;

import com.example.galaxyapp.API_Model.ImageResponse;
import com.example.galaxyapp.API_Model.Service;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface Service_call {

    @POST("/service/addService")
    Call<Void> addService(@Body Service service);


    @GET("/service/getService")
    Call<List<Service>> getService();

    @Multipart
    @POST("/service/sendPic")
    Call<ImageResponse> uploadProfileImage(@Part MultipartBody.Part img);


    @POST("/service/searchService")
    Call<List<Service>> searchSearchBytitle(@Body Service service);

    @POST("/service/searchServiceonCategory")
    Call<List<Service>> searchServiceonCategory(@Body Service service);

    @POST("/service/findOneService")
    Call<Service> findOneService(@Body Service service);
}
