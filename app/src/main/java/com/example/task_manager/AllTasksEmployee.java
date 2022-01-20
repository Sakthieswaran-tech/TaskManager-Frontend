package com.example.task_manager;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AllTasksEmployee extends Fragment {
    RecyclerView recyclerView;
    View view;
    String role = MainActivity.ROLE;
    TaskDetail taskDetail;
    TaskListAdapter taskListAdapter;
    TextView textView;
    ExtendedFloatingActionButton floatingActionButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_completed_task_list, container, false);
        recyclerView = view.findViewById(R.id.taskcompletefrag);
        taskDetail = new TaskDetail();
        textView=view.findViewById(R.id.noTask3);
        floatingActionButton=view.findViewById(R.id.filterfloatemployee);
        fetchCompleteTask(role, 1);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),FilterTasks.class));
            }
        });

        return view;
    }

    private void fetchCompleteTask(String role, int i) {
        Call<TaskDetail> call = APIClient.getTaskManage(getContext()).getFilterTaskByRole(role);
        call.enqueue(new Callback<TaskDetail>() {
            @Override
            public void onResponse(Call<TaskDetail> call, Response<TaskDetail> response) {
                if(response.code()==404 || response.body().getTasks().size()<1) {
                    textView.setVisibility(View.VISIBLE);
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
    private void putdata(List<Tasks> tasks) {
        taskListAdapter=new TaskListAdapter(tasks,getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.recyclerview_divider));
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(taskListAdapter);
    }
}