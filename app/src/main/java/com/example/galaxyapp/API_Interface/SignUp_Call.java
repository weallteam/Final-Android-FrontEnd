package com.example.galaxyapp.API_Interface;

import com.example.galaxyapp.API_Model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SignUp_Call {
    @POST("/user/signup")
    Call<Void> onSignUp(@Body User user);
}
