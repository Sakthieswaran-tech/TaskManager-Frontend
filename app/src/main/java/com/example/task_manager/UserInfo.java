package com.example.task_manager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserInfo extends AppCompatActivity {
    TextView name,id,regno,mail,role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        name=findViewById(R.id.flexibleempname);
        id=findViewById(R.id.flexibleempid);
        regno=findViewById(R.id.flexibleregno);
        mail=findViewById(R.id.flexibleemail);
        role=findViewById(R.id.flexiblerole);

        Bundle bundle=getIntent().getExtras();
        String userid=bundle.getString("USERID");

        Call<UserDetail> call=APIClient.getLoginRoute(UserInfo.this).getRole(userid);
        call.enqueue(new Callback<UserDetail>() {
            @Override
            public void onResponse(Call<UserDetail> call, Response<UserDetail> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(UserInfo.this,"Something went wrong",Toast.LENGTH_LONG).show();
                }else {
                    UserDetail userDetail=response.body();
                    name.setText(userDetail.getUser().get(0).getName());
                    id.setText(userDetail.getUser().get(0).getEmpID());
                    regno.setText(userDetail.getUser().get(0).getEmpNO());
                    mail.setText(userDetail.getUser().get(0).getEmail());
                    role.setText(userDetail.getUser().get(0).getRole());
                }
            }

            @Override
            public void onFailure(Call<UserDetail> call, Throwable t) {
                Toast.makeText(UserInfo.this,t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }
}