package com.example.galaxyapp.API_Interface;

import com.example.galaxyapp.API_Model.Book;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Book_call {
    @POST("/service/addBook")
    Call<Void> addBook(@Body Book book);

    @POST("/service/viewBook")
    Call<List<Book>> viewBook(@Body Book book);
}
