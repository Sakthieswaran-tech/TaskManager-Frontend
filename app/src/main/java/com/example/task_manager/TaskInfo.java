package com.example.task_manager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TaskInfo extends AppCompatActivity {
    Button startbutton, finishbutton;
    TextInputLayout textInputLayout;
    TextInputEditText comments;
    TextView taskname, deptask, starttime, createdby, createdat, completedat, status, estStart, estComplete, commentView;
    RelativeLayout relativeLayout;
    LinearLayout linearLayout;
    Set<Integer> set = new HashSet<>();
    MainActivity mainActivity = new MainActivity();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_info);

        textInputLayout = findViewById(R.id.comment);
        commentView = findViewById(R.id.com);
        comments = findViewById(R.id.postcomments);
        estStart = findViewById(R.id.eststart);
        estComplete = findViewById(R.id.estcomplete);
        startbutton = findViewById(R.id.startbutton);
        finishbutton = findViewById(R.id.finishbutton);
        taskname = findViewById(R.id.flexiblename);
        deptask = findViewById(R.id.flexibledeptask);
        starttime = findViewById(R.id.flexiblestart);
        createdby = findViewById(R.id.flexiblecreatedby);
        createdat = findViewById(R.id.flexiblecreatedat);
        completedat = findViewById(R.id.flexiblecompletedat);
        relativeLayout = findViewById(R.id.parentRelative);
        linearLayout = findViewById(R.id.rowLinear);
        status = findViewById(R.id.flexiblestatus);

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        Bundle bundle = getIntent().getExtras();
        int taskid = bundle.getInt("TASKID");

        Call<TaskDetail> call = APIClient.getTaskManage(TaskInfo.this).getOneTask(taskid);
        call.enqueue(new Callback<TaskDetail>() {
            @Override
            public void onResponse(Call<TaskDetail> call, Response<TaskDetail> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(TaskInfo.this, "Something went wrong", Toast.LENGTH_LONG).show();
                } else {
                    TaskDetail taskDetail = response.body();
                    taskname.setText(taskDetail.getTask().getTaskName());
                    if (taskDetail.getTask().getEstimated_start()==null){
                        estStart.setText("Null");
                    }else {
                        String formatestStart = DateFormat.getDateInstance().format(taskDetail.getTask().getEstimated_start());
                        estStart.setText(formatestStart);
                    }

                    String commentss = taskDetail.getTask().getComments();
                    if (commentss!=null) {
                        commentView.setText("Comment: " + commentss);
                        comments.setVisibility(View.INVISIBLE);
                        textInputLayout.setVisibility(View.INVISIBLE);
                    } else {
                        commentView.setVisibility(View.INVISIBLE);
                    }
                    if (taskDetail.getTask().getEstimated_complete()==null){
                        estComplete.setText("Null");
                    }else {
                        String formatEstComplete = DateFormat.getDateInstance().format(taskDetail.getTask().getEstimated_complete());
                        estComplete.setText(formatEstComplete);
                    }
                    if (taskDetail.getTask().getStartTime() == null) {
                        starttime.setText("Task not started");
                    } else {
                        String formatStart = DateFormat.getDateTimeInstance().format(taskDetail.getTask().getStartTime());
                        starttime.setText(formatStart.toString());
                    }
                    if (taskDetail.getTask().getCompletedAt() == null) {
                        completedat.setText("Task not completed");
                    } else {
                        String formatComplete = DateFormat.getDateTimeInstance().format(taskDetail.getTask().getCompletedAt());
                        completedat.setText(formatComplete.toString());
                    }
                    createdby.setText(taskDetail.getTask().getCreatedBy());
                    String formatCreatedAt = DateFormat.getDateTimeInstance().format(taskDetail.getTask().getCreatedAt());
                    createdat.setText(formatCreatedAt.toString());
                    int stat = taskDetail.getTask().getIsCompleted();
                    if (stat == 0) {
                        status.setText("Not completed");
                    } else {
                        status.setText("Completed");
                    }

                    int deplen = taskDetail.getTask().getDependents().size();
                    deptask.setText(String.valueOf(deplen));
                    if (deplen > 0) {
                        for (int i = 0; i < deplen; i++) {
                            View view = getLayoutInflater().inflate(R.layout.row_dep_task, null, false);
                            TextView dependingTaskName = view.findViewById(R.id.listdeptask);
                            TextView dependingTaskStatus = view.findViewById(R.id.listdepstatus);

                            dependingTaskName.setText(taskDetail.getTask().getDependents().get(i).getTaskName() + ":");
                            int depStat = taskDetail.getTask().getDependents().get(i).getIsCompleted();
                            set.add(depStat);
                            if (depStat == 1) {
                                dependingTaskStatus.setText(" Completed");
                            } else {
                                dependingTaskStatus.setText(" Not completed");
                            }
                            linearLayout.addView(view);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<TaskDetail> call, Throwable t) {
                Toast.makeText(TaskInfo.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        startbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mainActivity.checkConnection(connectivityManager)) {
                    String start = starttime.getText().toString();
                    String complete = completedat.getText().toString();
                    if (start != "Task not started" && complete != "Task not completed") {
                        Snackbar snackbar = Snackbar.make(relativeLayout, "Task already completed", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    } else if (start != "Task not started" && complete == "Task not completed") {
                        Snackbar snackbar = Snackbar.make(relativeLayout, "Task started already", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    } else if (start == "Task not started" && complete == "Task not completed") {
                        if (set.size() > 0) {
                            if (set.contains(0)) {
                                Snackbar snackbar = Snackbar.make(relativeLayout, "Please wait until dependent tasks are completed", Snackbar.LENGTH_LONG);
                                snackbar.show();
                            } else startThisTask();
                        } else {
                            startThisTask();
                        }
                    }
                }else mainActivity.createDialog(TaskInfo.this);
            }

            private void startThisTask() {
                Date currentTime = Calendar.getInstance().getTime();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                String formattedStart = simpleDateFormat.format(currentTime);
                StartTask startTask = new StartTask(formattedStart);
                Call<StartTask> call1 = APIClient.getTaskManage(TaskInfo.this).startTask(taskid, startTask);
                call1.enqueue(new Callback<StartTask>() {
                    @Override
                    public void onResponse(Call<StartTask> call, Response<StartTask> response) {
                        if (!response.isSuccessful()) {
                            Toast.makeText(TaskInfo.this, "Something went wrong", Toast.LENGTH_LONG).show();
                        } else {
                            Date date = Calendar.getInstance().getTime();
                            String currentStart = DateFormat.getDateTimeInstance().format(date);
                            starttime.setText(currentStart.toString());
                            Snackbar snackbar = Snackbar.make(relativeLayout, "Task started", Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }
                    }

                    @Override
                    public void onFailure(Call<StartTask> call, Throwable t) {
                        Toast.makeText(TaskInfo.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        finishbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mainActivity.checkConnection(connectivityManager)) {
                    String start = starttime.getText().toString();
                    String complete = completedat.getText().toString();
                    if (start != "Task not started" && complete != "Task not completed") {
                        Snackbar snackbar = Snackbar.make(relativeLayout, "Task already completed", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    } else if (start == "Task not started" && complete == "Task not completed") {
                        Snackbar snackbar = Snackbar.make(relativeLayout, "You should start the task first", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    } else if (start != "Task not started" && complete == "Task not completed") {
                        if (set.size() > 0) {
                            if (set.contains(0)) {
                                Snackbar snackbar = Snackbar.make(relativeLayout, "Please wait until dependent tasks are completed", Snackbar.LENGTH_LONG);
                                snackbar.show();
                            } else completeThisTask();
                        } else {
                            completeThisTask();
                        }
                    }
                }else mainActivity.createDialog(TaskInfo.this);
            }

            private void completeThisTask() {
                StartTask startTask = new StartTask(true, comments.getText().toString().trim());
                Call<StartTask> call1 = APIClient.getTaskManage(TaskInfo.this).completeTask(taskid, startTask);
                call1.enqueue(new Callback<StartTask>() {
                    @Override
                    public void onResponse(Call<StartTask> call, Response<StartTask> response) {
                        if (!response.isSuccessful()) {
                            Toast.makeText(TaskInfo.this, "Something went wrong", Toast.LENGTH_LONG).show();
                        } else {
                            Date date = Calendar.getInstance().getTime();
                            String currentComplete = DateFormat.getDateTimeInstance().format(date);
                            completedat.setText(currentComplete.toString());
                            status.setText("Completed");
                            Snackbar snackbar = Snackbar.make(relativeLayout, "Task completed", Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }
                    }

                    @Override
                    public void onFailure(Call<StartTask> call, Throwable t) {
                        Toast.makeText(TaskInfo.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}