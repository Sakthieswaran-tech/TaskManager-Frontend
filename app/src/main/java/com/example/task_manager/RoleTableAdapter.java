package com.example.task_manager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveAction;

public class RoleTableAdapter extends RecyclerView.Adapter<RoleTableAdapter.MyViewHolder> {
    List<String> list;
    Context context;

    public RoleTableAdapter(List<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.role_list,parent,false);
        return new MyViewHolder(view);
    }

    public void setRoles(List<String> list){
        this.list=list;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.rolename.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView rolename;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            rolename=itemView.findViewById(R.id.rolename);
        }
    }
}
