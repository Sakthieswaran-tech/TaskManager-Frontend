package com.example.task_manager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostRole extends AppCompatActivity {

    TextInputEditText rolename;
    Button createRole;
    LinearLayout linearLayout;
    MainActivity mainActivity=new MainActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_role);

        linearLayout=findViewById(R.id.roleLinear);
        rolename=findViewById(R.id.postrole);
        createRole=findViewById(R.id.createrole);
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        createRole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mainActivity.checkConnection(connectivityManager))
                    mainActivity.createDialog(PostRole.this);
                else {
                    String role = rolename.getText().toString().trim();
                    if (role.isEmpty()) {
                        Snackbar snackbar = Snackbar.make(linearLayout, "Please enter role", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    } else {
                        Roles roles = new Roles(role);
                        Call<Roles> call = APIClient.getRoleManage(PostRole.this).createRole(roles);
                        call.enqueue(new Callback<Roles>() {
                            @Override
                            public void onResponse(Call<Roles> call, Response<Roles> response) {
                                if (!response.isSuccessful()) {
                                    Toast.makeText(PostRole.this, "Something went wrong", Toast.LENGTH_LONG).show();
                                } else {
                                    rolename.setText("");
                                    rolename.clearFocus();
                                    Snackbar snackbar = Snackbar.make(linearLayout, "Role created", Snackbar.LENGTH_LONG);
                                    snackbar.show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Roles> call, Throwable t) {
                                Toast.makeText(PostRole.this, t.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }
            }
        });
    }
}