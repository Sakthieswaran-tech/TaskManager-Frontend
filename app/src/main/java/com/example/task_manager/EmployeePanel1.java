package com.example.task_manager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeePanel1 extends AppCompatActivity {
    RecyclerView recyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_panel1);

        recyclerview = findViewById(R.id.taskEmployee);

        Bundle bundle = getIntent().getExtras();
        String role = bundle.getString("role");

//        Call<TaskDetail> call = APIClient.getTaskManage(EmployeePanel1.this).getTaskByRole(role);
//        call.enqueue(new Callback<TaskDetail>() {
//            @Override
//            public void onResponse(Call<TaskDetail> call, Response<TaskDetail> response) {
//                if (!response.isSuccessful()) {
//                    Toast.makeText(EmployeePanel1.this, (CharSequence) response.errorBody(), Toast.LENGTH_LONG).show();
//                } else {
//                    TaskDetail taskDetail = response.body();
//                    putdata(taskDetail.getTasks());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<TaskDetail> call, Throwable t) {
//                Toast.makeText(EmployeePanel1.this, t.getMessage(), Toast.LENGTH_LONG).show();
//            }
//        });
//    }
//
//
//
//    private void putdata(List<Tasks> tasks) {
//        TaskListAdapter taskListAdapter=new TaskListAdapter(tasks,EmployeePanel1.this);
//        recyclerview.setLayoutManager(new LinearLayoutManager(EmployeePanel1.this));
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(EmployeePanel1.this, DividerItemDecoration.VERTICAL);
//        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.recyclerview_divider));
//        recyclerview.addItemDecoration(dividerItemDecoration);
//        recyclerview.setAdapter(taskListAdapter);
//    }
    }
}