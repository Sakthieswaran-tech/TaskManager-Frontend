package com.example.task_manager;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserList extends Fragment {
    RecyclerView recyclerView;
    View view;
    FloatingActionButton floatingActionButton,refreshuser;
    ExtendedFloatingActionButton addRole;
    UserDetail userDetail;
    UserListAdapter userListAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user_list, container, false);
        recyclerView = view.findViewById(R.id.usersList);
        floatingActionButton = view.findViewById(R.id.adduser);
        refreshuser=view.findViewById(R.id.refreshusers);
        addRole=view.findViewById(R.id.addRole);

        Call<UserDetail> call = APIClient.getUserManage(getContext()).getUsers();
        call.enqueue(new Callback<UserDetail>() {
            @Override
            public void onResponse(Call<UserDetail> call, Response<UserDetail> response) {
                userDetail = response.body();
                putdata(userDetail.getUser());
            }

            @Override
            public void onFailure(Call<UserDetail> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), PostUser.class));
            }
        });

        refreshuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<UserDetail> call = APIClient.getUserManage(getContext()).getUsers();
                call.enqueue(new Callback<UserDetail>() {
                    @Override
                    public void onResponse(Call<UserDetail> call, Response<UserDetail> response) {
                        if (!response.isSuccessful()){
                            Toast.makeText(getContext(),"Something went wrong",Toast.LENGTH_LONG).show();
                            return;
                        }
                        userDetail = response.body();
                        userListAdapter.setUserList(userDetail.getUser());
                        Toast.makeText(getContext(),"List updated",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<UserDetail> call, Throwable t) {
                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        addRole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),PostRole.class));
            }
        });

        return view;
    }

    private void putdata(List<User> list) {
        userListAdapter = new UserListAdapter(getContext(), list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.recyclerview_divider));
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(userListAdapter);
    }
}