package com.example.task_manager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TaskList extends Fragment {
    RecyclerView recyclerView;
    View view;
    FloatingActionButton floatingActionButton, refresh;
    TaskDetail taskDetail = new TaskDetail();
    TaskListAdapter taskListAdapter;
    String role = MainActivity.ROLE;
    ExtendedFloatingActionButton filterfloat;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_task_list, container, false);
        recyclerView = view.findViewById(R.id.taskAdmin);
        floatingActionButton = view.findViewById(R.id.add);
        refresh = view.findViewById(R.id.refreshtask);
        filterfloat = view.findViewById(R.id.filterfloat);

        fetchTasks();

        filterfloat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), FilterTasks.class));
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), PostTask.class));
            }
        });

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<TaskDetail> call = APIClient.getTaskManage(getContext()).getTasks();
                call.enqueue(new Callback<TaskDetail>() {
                    @Override
                    public void onResponse(Call<TaskDetail> call, Response<TaskDetail> response) {
                        if (!response.isSuccessful()) {
                            Toast.makeText(getContext(), (CharSequence) response.errorBody(), Toast.LENGTH_LONG).show();
                        } else {
                            taskDetail = response.body();
                            taskListAdapter.setTasks(taskDetail.getTasks());
                            Toast.makeText(getContext(), "List updated", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<TaskDetail> call, Throwable t) {
                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        return view;
    }

    private void fetchCompleteTasks() {
        Call<TaskDetail> call = APIClient.getTaskManage(getContext()).getTaskByRole(role, 1);
        call.enqueue(new Callback<TaskDetail>() {
            @Override
            public void onResponse(Call<TaskDetail> call, Response<TaskDetail> response) {
                if (response.code() == 404) {
                } else if (!response.isSuccessful()) {
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


    public void fetchTasks() {
        Call<TaskDetail> call = APIClient.getTaskManage(getContext()).getTasks();
        call.enqueue(new Callback<TaskDetail>() {
            @Override
            public void onResponse(Call<TaskDetail> call, Response<TaskDetail> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), (CharSequence) response.errorBody(), Toast.LENGTH_LONG).show();
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
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(taskListAdapter);
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int pos = viewHolder.getAdapterPosition();
            int id = taskDetail.getTasks().get(pos).getId();
            AlertDialog.Builder myalert = new AlertDialog.Builder(getContext());
            myalert.setTitle("Delete task");
            myalert.setMessage("Do you want to delete this task?");
            myalert.setCancelable(true);

            myalert.setNegativeButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Call<TaskDetail> call = APIClient.getTaskManage(getContext()).deleteTask(id);
                    call.enqueue(new Callback<TaskDetail>() {
                        @Override
                        public void onResponse(Call<TaskDetail> call, Response<TaskDetail> response) {
                            if (response.code() == 200) {
                                taskDetail.getTasks().remove(pos);
                                taskListAdapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(getContext(), "Not deleted", Toast.LENGTH_LONG).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<TaskDetail> call, Throwable t) {
                            Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

                }
            });
            myalert.setPositiveButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alertDialog = myalert.create();
            alertDialog.show();
        }
    };
}
