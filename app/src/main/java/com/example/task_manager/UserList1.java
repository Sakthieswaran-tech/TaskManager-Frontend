package com.example.task_manager;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserList1 extends Fragment {
    View view;
    RecyclerView recyclerView;
    UserDetail userDetail;
    UserListAdapter userListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_user_list1,container,false);
        recyclerView=view.findViewById(R.id.recyleuserslist);

        Call<UserDetail> call=APIClient.getUserManage(getContext()).getUsers();

        call.enqueue(new Callback<UserDetail>() {
            @Override
            public void onResponse(Call<UserDetail> call, Response<UserDetail> response) {
                if (response.isSuccessful()){
                    userDetail=response.body();
                    putdata(userDetail.getUser());
                }
            }



            @Override
            public void onFailure(Call<UserDetail> call, Throwable t) {

            }
        });

        return view;
    }

    private void putdata(List<User> user) {
        userListAdapter = new UserListAdapter(getContext(), user);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.recyclerview_divider));
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(userListAdapter);
    }
}