package com.example.task_manager;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserManagement {

    @GET("users")
    Call<UserDetail> getUsers();

    @POST("users")
    Call<CreateUser> createUser(@Body CreateUser createUser);
}
