package com.example.task_manager;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PopulateRecyclerView {

    public static void putdata(List<Tasks> tasks, RecyclerView recyclerView, Context context, TaskListAdapter taskListAdapter,
                               Drawable drawable){
        taskListAdapter = new TaskListAdapter(tasks, context);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(drawable);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(taskListAdapter);
    }

    public static void updatedate(List<Tasks> tasks,TaskListAdapter taskListAdapter){
        taskListAdapter=new TaskListAdapter();
        taskListAdapter.setTasks(tasks);
    }
}
