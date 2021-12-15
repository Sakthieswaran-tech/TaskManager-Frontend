package com.example.task_manager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.task_list,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.taskname.setText(tasks.get(position).getTaskName());
//        String formatestStart = DateFormat.getTimeInstance().format(tasks.get(position).getEstimated_start_time());
//        if (tasks.get(position).getEstimated_start_time()!=null) {
//            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("hh:mm a");
////                Date date=simpleDateFormat.parse(time);
////                System.out.println("FWEFWEFWESFwe "+date);
////                System.out.println(new SimpleDateFormat("K:mm").format(date));
//            String date=simpleDateFormat.format(tasks.get(position).getEstimated_start_time());
//            holder.start.setText(date);
//        }
        if (tasks.get(position).getEstimated_start_time()!=null){
            holder.start.setText(tasks.get(position).getEstimated_start_time());
        }
        int statusOfTask=tasks.get(position).getIsCompleted();
        if (statusOfTask==1){
            holder.status.setTextColor(Color.BLUE);
            holder.status.setText("Completed");
        }else{
            holder.status.setTextColor(Color.RED);
            holder.status.setText("Not Completed");
        }
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,TaskInfo.class);
                Bundle bundle=new Bundle();
                bundle.putInt("TASKID",tasks.get(position).getId());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public void setTasks(List<Tasks> tasks){
        this.tasks=tasks;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView taskname,status,start;
        RelativeLayout relativeLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            relativeLayout=itemView.findViewById(R.id.relAdap);
            taskname = itemView.findViewById(R.id.taskname);
            status = itemView.findViewById(R.id.taskstatus);
            start=itemView.findViewById(R.id.starttime);
        }
    }
}
