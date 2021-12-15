package com.example.task_manager;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface LoginRoute {

    @POST("users/login/")
    Call<Token> fetchToken(@Body LoginCredentials loginCredentials);

    @GET("users/{id}/")
    Call<UserDetail> getRole(@Path("id") String id);
}
