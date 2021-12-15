package com.example.task_manager;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    SharedPreferences sharedPreferences;
    private Context context;

    public SessionManager() {
    }

    public SessionManager(Context context) {
        this.context = context;
    }

    public void saveToken(String token){
        sharedPreferences=context.getSharedPreferences("My preference",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("Token",token);
        editor.apply();
    }

    public String fetchToken(){
        sharedPreferences=context.getSharedPreferences("My preference",Context.MODE_PRIVATE);
        return sharedPreferences.getString("Token","");
    }
    
}

