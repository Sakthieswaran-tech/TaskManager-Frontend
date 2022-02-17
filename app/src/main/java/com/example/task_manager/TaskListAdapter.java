package com.example.task_manager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.MyViewHolder> {
    List<Tasks> tasks;
    Context context;

    public TaskListAdapter(List<Tasks> tasks, Context context) {
        this.tasks = tasks;
        this.context = context;
    }

    public TaskListAdapter() {
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.taskname.setText(tasks.get(position).getTaskName());
        if (tasks.get(position).getEstimated_start_time() != null) {
            holder.start.setText(tasks.get(position).getEstimated_start_time());
        }
        int statusOfTask = tasks.get(position).getIsCompleted();
        if (statusOfTask == 1) {
            holder.status.setTextColor(Color.BLUE);
            holder.status.setText("Completed");
        } else {
            holder.status.setTextColor(Color.RED);
            holder.status.setText("Not Completed");
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TaskInfo.class);
                Bundle bundle = new Bundle();
                bundle.putInt("TASKID", tasks.get(position).getId());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public void setTasks(List<Tasks> tasks) {
        this.tasks = tasks;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView taskname, status, start;
        RelativeLayout relativeLayout;
        ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            relativeLayout = itemView.findViewById(R.id.relAdap);
            taskname = itemView.findViewById(R.id.taskname);
            status = itemView.findViewById(R.id.taskstatus);
            start = itemView.findViewById(R.id.starttime);
            imageView = itemView.findViewById(R.id.selectitem);
        }
    }
}

