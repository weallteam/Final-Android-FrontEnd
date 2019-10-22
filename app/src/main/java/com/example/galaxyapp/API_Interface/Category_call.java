package com.example.galaxyapp.API_Interface;

import com.example.galaxyapp.API_Model.Categories;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Category_call {
    @GET ("/service/viewCategory")
    Call<List<Categories>> getAllCategories();
}
