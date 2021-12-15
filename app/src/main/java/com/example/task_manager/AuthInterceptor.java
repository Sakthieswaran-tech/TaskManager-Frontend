package com.example.task_manager;

import android.content.Context;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {
    private Context context;
    SessionManager sessionManager;

    public AuthInterceptor(Context context) {
        this.context = context;
        sessionManager=new SessionManager(context);
    }

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request request=chain.request();
        Request request1=request.newBuilder().header("Authorization","Bearer "+sessionManager.fetchToken()).build();
        return chain.proceed(request1);
    }
}
