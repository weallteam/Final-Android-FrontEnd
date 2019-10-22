package com.example.galaxyapp.API_Interface;

import com.example.galaxyapp.API_Model.LoginResponse;
import com.example.galaxyapp.API_Model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Login_Call {
    @POST("/user/login")
    Call<LoginResponse> onLogin(@Body User user);
}
