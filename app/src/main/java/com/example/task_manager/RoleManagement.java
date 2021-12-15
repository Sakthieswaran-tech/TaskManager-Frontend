package com.example.task_manager;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RoleManagement {

    @POST("roles")
    Call<Roles> createRole(@Body Roles roles);

    @GET("roles")
    Call<RoleList> getAllRoles();
}
