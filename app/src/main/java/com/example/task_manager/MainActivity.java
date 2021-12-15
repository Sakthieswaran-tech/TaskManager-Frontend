package com.example.task_manager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    TextInputEditText name, password;
    Button login;
    ConstraintLayout constraintLayout;
    SessionManager sessionManager;
    public static String ROLE;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        name = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.button);
        constraintLayout = findViewById(R.id.constraint_main);
        sessionManager = new SessionManager(MainActivity.this);
        progressBar = findViewById(R.id.mainprogress);
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        progressBar.setVisibility(View.INVISIBLE);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputMethodManager=(InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(),0);

                progressBar.setVisibility(View.VISIBLE);

                if (checkConnection(connectivityManager)) {
                    if (name.getText().toString().trim().isEmpty() && password.getText().toString().trim().isEmpty()) {
                        progressBar.setVisibility(View.INVISIBLE);
                        Snackbar snackbar = Snackbar.make(constraintLayout, "Please enter all details", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                    else if (name.getText().toString().trim().isEmpty()){
                        progressBar.setVisibility(View.INVISIBLE);
                        Snackbar snackbar = Snackbar.make(constraintLayout, "Please enter ID", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }else if (password.getText().toString().trim().isEmpty()){
                        progressBar.setVisibility(View.INVISIBLE);
                        Snackbar snackbar = Snackbar.make(constraintLayout, "Please enter password", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                    else {
                        loginUser();
                    }
                } else {
                    progressBar.setVisibility(View.INVISIBLE);
                    createDialog(MainActivity.this);
                }
            }

            private void loginUser() {
                progressBar.setVisibility(View.VISIBLE);
                LoginCredentials loginCredentials = new LoginCredentials(name.getText().toString().trim(),
                        password.getText().toString().trim());
                Call<Token> call = APIClient.getLoginRoute(MainActivity.this).fetchToken(loginCredentials);
                call.enqueue(new Callback<Token>() {
                    @Override
                    public void onResponse(Call<Token> call, Response<Token> response) {
                        if (response.code() == 404) {
                            progressBar.setVisibility(View.INVISIBLE);
                            Snackbar snackbar = Snackbar.make(constraintLayout, "ID incorrect", Snackbar.LENGTH_LONG);
                            snackbar.show();
                        } else if (response.code() == 401) {
                            progressBar.setVisibility(View.INVISIBLE);
                            Snackbar snackbar = Snackbar.make(constraintLayout, "Password incorrect", Snackbar.LENGTH_LONG);
                            snackbar.show();
                        } else if (response.code() == 200) {
                            Token token = response.body();
                            sessionManager.saveToken(token.getToken());
                            Call<UserDetail> call1 = APIClient.getLoginRoute(MainActivity.this).getRole(name.getText().toString().trim());
                            call1.enqueue(new Callback<UserDetail>() {
                                @Override
                                public void onResponse(Call<UserDetail> call, Response<UserDetail> response) {
                                    if (response.code() == 200) {
                                        UserDetail userDetail = response.body();
                                        ROLE = userDetail.getUser().get(0).getRole();
                                        switch (ROLE) {
                                            case "admin":
                                                progressBar.setVisibility(View.INVISIBLE);
                                                startActivity(new Intent(MainActivity.this, AdminPanelOne.class));
                                                break;
                                            case "Manager":
                                                progressBar.setVisibility(View.INVISIBLE);
                                                startActivity(new Intent(MainActivity.this, ManagerPanel.class));
                                                break;
                                            default:
                                                Intent intent = new Intent(MainActivity.this, EmployeePanel.class);
                                                Bundle bundle = new Bundle();
                                                bundle.putString("role", ROLE);
                                                intent.putExtras(bundle);
                                                progressBar.setVisibility(View.INVISIBLE);
                                                startActivity(intent);
//                                                finish();
                                                break;
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<UserDetail> call, Throwable t) {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            });
                        } else {
                            progressBar.setVisibility(View.INVISIBLE);
                            Snackbar snackbar = Snackbar.make(constraintLayout, "Something went wrong", Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Token> call, Throwable t) {
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }


    public boolean checkConnection(ConnectivityManager connectivityManager) {
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
            return true;
        else {
            return false;
        }
    }

    public void createDialog(Context context) {
        AlertDialog.Builder myalert = new AlertDialog.Builder(context);
        myalert.setTitle("Oops!");
        myalert.setMessage("Check your internet connection");
        myalert.setCancelable(true);

        myalert.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = myalert.create();
        alertDialog.show();
    }
}