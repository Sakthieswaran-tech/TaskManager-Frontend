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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TaskList extends Fragment {
    RecyclerView recyclerView;
    View view;
    FloatingActionButton floatingActionButton, refresh;
    TaskDetail taskDetail = new TaskDetail();
    TaskListAdapter taskListAdapter;
    String role = MainActivity.ROLE;
    ExtendedFloatingActionButton filterfloat;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_task_list, container, false);
        recyclerView = view.findViewById(R.id.taskAdmin);
        floatingActionButton = view.findViewById(R.id.add);
        refresh = view.findViewById(R.id.refreshtask);
        filterfloat=view.findViewById(R.id.filterfloat);

        if (MainActivity.ROLE.equals("admin") || MainActivity.ROLE.equals("Manager")) {
            fetchTasks();
        }else{
            fetchCompleteTasks();
            floatingActionButton.setVisibility(View.GONE);
            refresh.setVisibility(View.GONE);
        }

        filterfloat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),FilterTasks.class));
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), PostTask.class));
            }
        });

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<TaskDetail> call = APIClient.getTaskManage(getContext()).getTasks();
                call.enqueue(new Callback<TaskDetail>() {
                    @Override
                    public void onResponse(Call<TaskDetail> call, Response<TaskDetail> response) {
                        if (!response.isSuccessful()) {
                            Toast.makeText(getContext(), (CharSequence) response.errorBody(), Toast.LENGTH_LONG).show();
                        } else {
                            taskDetail = response.body();
                            taskListAdapter.setTasks(taskDetail.getTasks());
                            Toast.makeText(getContext(), "List updated", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<TaskDetail> call, Throwable t) {
                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        return view;
    }

    private void fetchCompleteTasks() {
        Call<TaskDetail> call = APIClient.getTaskManage(getContext()).getTaskByRole(role, 1);
        call.enqueue(new Callback<TaskDetail>() {
            @Override
            public void onResponse(Call<TaskDetail> call, Response<TaskDetail> response) {
                if(response.code()==404) {
//                    textView.setVisibility(View.VISIBLE);
                }
                else if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                } else{
                    taskDetail = response.body();
                    putdata(taskDetail.getTasks());
                }
            }

            @Override
            public void onFailure(Call<TaskDetail> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


    public void fetchTasks() {
        Call<TaskDetail> call = APIClient.getTaskManage(getContext()).getTasks();
        call.enqueue(new Callback<TaskDetail>() {
            @Override
            public void onResponse(Call<TaskDetail> call, Response<TaskDetail> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), (CharSequence) response.errorBody(), Toast.LENGTH_LONG).show();
                } else {
                    taskDetail = response.body();
                    putdata(taskDetail.getTasks());
                }
            }

            @Override
            public void onFailure(Call<TaskDetail> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void putdata(List<Tasks> tasks) {
        taskListAdapter = new TaskListAdapter(tasks, getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.recyclerview_divider));
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(taskListAdapter);
    }
}
