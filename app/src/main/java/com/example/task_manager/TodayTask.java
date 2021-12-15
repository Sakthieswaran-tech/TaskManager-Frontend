package com.example.task_manager;

import android.nfc.NfcAdapter;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TodayTask extends Fragment {
    RecyclerView recyclerView;
    TaskListAdapter taskListAdapter;
    TaskDetail taskDetail;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_today_task,container,false);
        recyclerView=view.findViewById(R.id.taskToday);
//        Toast.makeText(getContext(),"HGSDUYCSGUYDS",Toast.LENGTH_LONG).show();

        Date date= Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String format=simpleDateFormat.format(date);
        Call<TaskDetail> call=APIClient.getTaskManage(getContext()).getTodaysTask(MainActivity.ROLE,format);
        call.enqueue(new Callback<TaskDetail>() {
            @Override
            public void onResponse(Call<TaskDetail> call, Response<TaskDetail> response) {
                if (response.isSuccessful()){
                    taskDetail=response.body();
                    putdata(taskDetail.getTasks());
                }
                else{
                    Toast.makeText(getContext(),"HGSDUYCSGUYDS",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<TaskDetail> call, Throwable t) {

            }
        });

        return view;
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