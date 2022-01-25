package com.example.task_manager;

import android.content.Context;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    public static Retrofit retrofit;
//    public static final String BASE_URL="http://103.249.207.112:5000/";
    public static final String BASE_URL="http://192.168.43.199:5000/";

    public static Retrofit getRetrofit(Context context){
        if (retrofit==null){
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder okhttp=new OkHttpClient.Builder();
            okhttp.addInterceptor(new AuthInterceptor(context)).addInterceptor(interceptor);
            retrofit=new Retrofit.Builder().baseUrl(BASE_URL).
                    addConverterFactory(GsonConverterFactory.create())
                    .client(okhttp.build())
                    .build();
        }
        return retrofit;
    }

    public static LoginRoute getLoginRoute(Context context){
        LoginRoute loginRoute=getRetrofit(context).create(LoginRoute.class);
        return loginRoute;
    }

    public static UserManagement getUserManage(Context context){
        UserManagement userManagement=getRetrofit(context).create(UserManagement.class);
        return userManagement;
    }

    public static TaskManagement getTaskManage(Context context){
        TaskManagement taskManagement=getRetrofit(context).create(TaskManagement.class);
        return taskManagement;
    }

    public static RoleManagement getRoleManage(Context context){
        RoleManagement roleManagement=getRetrofit(context).create(RoleManagement.class);
        return roleManagement;
    }
}
