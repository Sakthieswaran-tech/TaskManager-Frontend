package com.example.task_manager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManagerPanel extends AppCompatActivity {
    RecyclerView recyclerView;
    FloatingActionButton add,refresh;
    TaskDetail taskDetail=new TaskDetail();
    TaskListAdapter taskListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_panel);

        recyclerView=findViewById(R.id.taskManager);
        add=findViewById(R.id.addManager);
        refresh=findViewById(R.id.refreshtaskManager);

        fetchTasks();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManagerPanel.this,PostTask.class));
            }
        });

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<TaskDetail> call=APIClient.getTaskManage(ManagerPanel.this).getTasks();
                call.enqueue(new Callback<TaskDetail>() {
                    @Override
                    public void onResponse(Call<TaskDetail> call, Response<TaskDetail> response) {
                        if (!response.isSuccessful()){
                            Toast.makeText(ManagerPanel.this, (CharSequence) response.errorBody(),Toast.LENGTH_LONG).show();
                        }
                        else {
                            taskDetail=response.body();
                            taskListAdapter.setTasks(taskDetail.getTasks());
                            Toast.makeText(ManagerPanel.this,"List updated",Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<TaskDetail> call, Throwable t) {
                        Toast.makeText(ManagerPanel.this,t.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    private void fetchTasks() {
        Call<TaskDetail> call=APIClient.getTaskManage(ManagerPanel.this).getTasks();
        call.enqueue(new Callback<TaskDetail>() {
            @Override
            public void onResponse(Call<TaskDetail> call, Response<TaskDetail> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(ManagerPanel.this, (CharSequence) response.errorBody(),Toast.LENGTH_LONG).show();
                }
                else {
                    taskDetail=response.body();
                    putdata(taskDetail.getTasks());
                }
            }

            @Override
            public void onFailure(Call<TaskDetail> call, Throwable t) {
                Toast.makeText(ManagerPanel.this,t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void putdata(List<Tasks> tasks) {
        taskListAdapter=new TaskListAdapter(tasks,ManagerPanel.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(ManagerPanel.this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(ManagerPanel.this, DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.recyclerview_divider));
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(taskListAdapter);
    }
}