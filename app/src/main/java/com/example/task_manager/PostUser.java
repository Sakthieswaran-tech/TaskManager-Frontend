package com.example.task_manager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostUser extends AppCompatActivity {
    TextInputEditText name, id, password, regno, mail;
    Spinner role;
    Button create;
    LinearLayout linearLayout;
    String username, userid;
    String [] rolenames;
    MainActivity mainActivity=new MainActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_user);

        name = findViewById(R.id.postname);
        id = findViewById(R.id.postid);
        password = findViewById(R.id.postpassword);
        regno = findViewById(R.id.postregno);
        mail = findViewById(R.id.postemail);
        role = findViewById(R.id.spinnerrole2);
        create = findViewById(R.id.createuser);
        linearLayout = findViewById(R.id.linear1);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        setTitle("Create User");
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        populateRoles();

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mainActivity.checkConnection(connectivityManager)){
                    if (name.getText().toString().trim().isEmpty() || id.getText().toString().trim().isEmpty() ||
                            password.getText().toString().trim().isEmpty() || regno.getText().toString().trim().isEmpty() ||
                            mail.getText().toString().trim().isEmpty()) {
                        Snackbar snackbar = Snackbar.make(linearLayout, "Please provide all details", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    } else {
                        createUser();
                    }
                }
                else mainActivity.createDialog(PostUser.this);
            }
        });
    }

    private void createUser() {
        username = name.getText().toString().trim();
        userid = id.getText().toString().trim();
        CreateUser createUser = new CreateUser(username, userid,
                regno.getText().toString().trim(), password.getText().toString().trim(),
                mail.getText().toString().trim(),role.getSelectedItem().toString());
        Call<CreateUser> call = APIClient.getUserManage(PostUser.this).createUser(createUser);
        call.enqueue(new Callback<CreateUser>() {
            @Override
            public void onResponse(Call<CreateUser> call, Response<CreateUser> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(PostUser.this, "Something went wrong", Toast.LENGTH_LONG).show();
                } else if (response.code() == 201) {
                    Snackbar snackbar = Snackbar.make(linearLayout, "User created", Snackbar.LENGTH_LONG);
                    snackbar.show();
                    name.setText("");
                    id.setText("");
                    password.setText("");
                    regno.setText("");
                    mail.setText("");
                    mail.clearFocus();
                }
            }

            @Override
            public void onFailure(Call<CreateUser> call, Throwable t) {
                Toast.makeText(PostUser.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void populateRoles() {
        Call<RoleList> call1=APIClient.getRoleManage(PostUser.this).getAllRoles();
        call1.enqueue(new Callback<RoleList>() {
            @Override
            public void onResponse(Call<RoleList> call, Response<RoleList> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(PostUser.this, "Something went wrong", Toast.LENGTH_LONG).show();
                }
                else {
                    RoleList roleList=response.body();
                    rolenames=new String[roleList.getRoles().size()];
                    for (int i=0;i<roleList.getRoles().size();i++){
                        rolenames[i]=roleList.getRoles().get(i).getRole();
                    }
                    ArrayAdapter arrayAdapter1 = new ArrayAdapter(PostUser.this, android.R.layout.simple_spinner_item, rolenames);
                    role.setAdapter(arrayAdapter1);
                }
            }

            @Override
            public void onFailure(Call<RoleList> call, Throwable t) {
                Toast.makeText(PostUser.this,t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
}