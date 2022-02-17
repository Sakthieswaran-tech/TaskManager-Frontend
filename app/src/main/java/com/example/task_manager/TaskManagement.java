package com.example.task_manager;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
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

    @GET("tasks/filter_for_admin_by_role")
    Call<TaskDetail> getFilterTaskByRole(
            @Query("role") String role
    );

    @GET("tasks/filter_for_admin")
    Call<TaskDetail> getFilterTasks(
            @Query("role") String role,
            @Query("from_date") String fromdate,
            @Query("to_date") String todate
    );

    @POST("tasks")
    Call<CreateTask> createTask(@Body CreateTask createTask);

    @GET("tasks/{id}")
    Call<TaskDetail> getOneTask(@Path("id") int taskid);

    @DELETE("tasks/{id}")
    Call<TaskDetail> deleteTask(@Path("id") int taskid);

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

    @GET("tasks/filter_by_dates_only")
    Call<TaskDetail> getTasksByDateOnly(@Query("from_date") String start, @Query("to_date") String end);

    @POST("tasks/master_task")
    Call<CreateTask> addmastertask(@Body CreateTask createTask);

    @GET("tasks/master_task")
    Call<TaskDetail> getmasterTasks();
}
