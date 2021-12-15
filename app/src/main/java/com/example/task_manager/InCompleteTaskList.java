package com.example.task_manager;

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

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class InCompleteTaskList extends Fragment {
    RecyclerView recyclerView;
    View view;
    String role = MainActivity.ROLE;
    TaskDetail taskDetail;
    TaskListAdapter taskListAdapter;
    TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_in_complete_task_list, container, false);
        recyclerView = view.findViewById(R.id.taskincompletefrag);
        taskDetail = new TaskDetail();
        textView = view.findViewById(R.id.noTask1);

        fetchInCompleteTask(role, 0);

        return view;
    }

    private void fetchInCompleteTask(String role, int i) {
        Call<TaskDetail> call = APIClient.getTaskManage(getContext()).getTaskByRole(role, i);
        call.enqueue(new Callback<TaskDetail>() {
            @Override
            public void onResponse(Call<TaskDetail> call, Response<TaskDetail> response) {
                if(response.code() == 404) {
                    textView.setVisibility(View.VISIBLE);
                }
                else if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG).show();
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