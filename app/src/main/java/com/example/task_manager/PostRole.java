package com.example.task_manager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.timessquare.CalendarPickerView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostRole extends AppCompatActivity {

    TextInputEditText rolename;
    Button createRole;
    ScrollView scrollView;
    MainActivity mainActivity=new MainActivity();
    RecyclerView recyclerView;
    RoleTableAdapter roleTableAdapter;
    HashSet<String> set=new HashSet<>();
    RoleList roleList;
    List<String> list=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_role);

        scrollView=findViewById(R.id.roleLinear);
        recyclerView=findViewById(R.id.roletable);
        rolename=findViewById(R.id.postrole);
        createRole=findViewById(R.id.createrole);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        setTitle("Create Role");
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        fetchRoles();
        createRole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputMethodManager=(InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(),0);
                if(!mainActivity.checkConnection(connectivityManager))
                    mainActivity.createDialog(PostRole.this);
                else {
                    String role = rolename.getText().toString().trim();
                    if (role.isEmpty()) {
                        Snackbar snackbar = Snackbar.make(scrollView, "Please enter role", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                    else if (set.contains(role)){
                        Snackbar snackbar = Snackbar.make(scrollView, "Role exists already", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                    else {
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
                                    Snackbar snackbar = Snackbar.make(scrollView, "Role created", Snackbar.LENGTH_LONG);
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

    private void fetchRoles() {
        Call<RoleList> call=APIClient.getRoleManage(PostRole.this).getAllRoles();
        call.enqueue(new Callback<RoleList>() {
            @Override
            public void onResponse(Call<RoleList> call, Response<RoleList> response) {
                if (response.code()==200){
                    roleList=response.body();
                    putdata(roleList.getRoles());
                }
            }

            @Override
            public void onFailure(Call<RoleList> call, Throwable t) {

            }
        });
    }

    void putdata(List<Roles> roles) {
        for(int i=0;i<roles.size();i++){
            list.add(roles.get(i).getRole());
            set.add(roles.get(i).getRole());
        }
        roleTableAdapter=new RoleTableAdapter(list,PostRole.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(PostRole.this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(PostRole.this, DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.recyclerview_divider));
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(roleTableAdapter);
    }
}