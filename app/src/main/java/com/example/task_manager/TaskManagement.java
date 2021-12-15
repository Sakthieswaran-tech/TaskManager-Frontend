package com.example.task_manager;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TaskManagement {

    @GET("tasks")
    Call<TaskDetail> getTasks();

    @GET("tasks/filter")
    Call<TaskDetail> getTaskByRole(
            @Query("role") String role,
            @Query("completed") int completed);

    @POST("tasks")
    Call<CreateTask> createTask(@Body CreateTask createTask);

    @GET("tasks/{id}")
    Call<TaskDetail> getOneTask(@Path("id") int taskid);

    @PATCH("tasks/{id}/start")
    Call<StartTask> startTask(@Path("id") int id ,@Body StartTask startTask);

    @PATCH("tasks/{id}/complete")
    Call<StartTask> completeTask(@Path("id") int id, @Body StartTask startTask);

    @GET("tasks/current")
    Call<TaskDetail> getTodaysTask(
            @Query("role") String role,
            @Query("estimated_start") String estimated_start);

    @GET("tasks/group")
    Call<List<GraphData>> getTaskByGroup();
}
