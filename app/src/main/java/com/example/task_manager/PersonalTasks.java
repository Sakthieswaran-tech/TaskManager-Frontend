package com.example.task_manager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonalTasks extends AppCompatActivity {
    RecyclerView recyclerView;
    TextView textView;
    TaskDetail taskDetail;
    TaskListAdapter taskListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_tasks);

        recyclerView = findViewById(R.id.personaltasks);
        textView = findViewById(R.id.personal);

        Call<TaskDetail> call = APIClient.getTaskManage(PersonalTasks.this).getFilterTaskByRole(MainActivity.USER_NAME);
        call.enqueue(new Callback<TaskDetail>() {
            @Override
            public void onResponse(Call<TaskDetail> call, Response<TaskDetail> response) {
                if (response.code() == 200) {
                    taskDetail=response.body();
                    putdata(taskDetail.getTasks());
                } else {
                    textView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<TaskDetail> call, Throwable t) {
                Toast.makeText(PersonalTasks.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    private void putdata(List<Tasks> tasks) {
        taskListAdapter = new TaskListAdapter(tasks, PersonalTasks.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(PersonalTasks.this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(PersonalTasks.this, DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.recyclerview_divider));
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(taskListAdapter);
    }
}