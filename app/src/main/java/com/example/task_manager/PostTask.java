package com.example.task_manager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.internal.concurrent.Task;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostTask extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    TextInputEditText name;
    TextInputLayout namelayout;
    Spinner rolespinner,userspinner;
    CheckBox checkBox;
    Button  createTaskButton, createDepTaskButton,pickstart,pickComplete,pickstartTime,pickCompleteTime;
    LinearLayout linearLayout, linearLayout1;
    TaskDetail taskDetail;
    TextView startDateView,completeDateView,startTimeView,completeTimeView,assign;
    ArrayList<Tasks> tasks=new ArrayList<>();
    ArrayList<Tasks> recurtasks=new ArrayList<>();
    MainActivity mainActivity=new MainActivity();
    String [] rolenames,users,taskname;
    int [] taskId,usersId;
    int a = 0;
    String currentDate,currentComplete;
    Calendar startActiveDate,completeActiveDate;
    String start,complete;
    private ArrayList<String> startDates,completeDates;
    List<Tasks> list;
    UserDetail userDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_task);

        assign=findViewById(R.id.assign);
        namelayout=findViewById(R.id.tasknamelayout);
        pickstartTime=findViewById(R.id.selectstartTime);
        pickCompleteTime=findViewById(R.id.selectcompleteTime);
        startTimeView=findViewById(R.id.startTimePost);
        completeTimeView=findViewById(R.id.completeTimePost);
        pickComplete=findViewById(R.id.selectcompleteDate);
        rolespinner=findViewById(R.id.spinnerrole);
        name = findViewById(R.id.posttaskname);
        userspinner=findViewById(R.id.spinnername);
        checkBox=findViewById(R.id.checkforuser);
//        existtask=findViewById(R.id.existtask);
//        checkBox=findViewById(R.id.rememebertask);
        createTaskButton = findViewById(R.id.createtask);
        linearLayout = findViewById(R.id.linear2);
        createDepTaskButton = findViewById(R.id.adddep);
        pickstart=findViewById(R.id.selectstartDate);
        startDateView=findViewById(R.id.startDatePost);
        linearLayout1 = findViewById(R.id.layout_list);
        completeDateView=findViewById(R.id.completeDatePost);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        setTitle("Create Task");
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (MainActivity.ROLE.equals("admin")) {
            populateDependingTask();
            populateRoles();
            populateUsers();
        }else{
            rolespinner.setVisibility(View.GONE);
            createDepTaskButton.setVisibility(View.GONE);
            assign.setVisibility(View.GONE);
            checkBox.setVisibility(View.GONE);
//            existtask.setVisibility(View.GONE);
//            checkBox.setVisibility(View.GONE);
        }
//        recurtaskID=new int[recurtasks.size()];
//        recurtasksname=new String[recurtasks.size()];
//        for (int i=0;i<recurtasks.size();i++){
//            recurtaskID[i]=recurtasks.get(i).getId();
//            recurtasksname[i]=recurtasks.get(i).getTaskName();
//        }
//        ArrayAdapter arrayAdapter1 = new ArrayAdapter(PostTask.this, android.R.layout.simple_spinner_item, recurtasksname);
//        taskspinner.setAdapter(arrayAdapter1);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    rolespinner.setVisibility(View.GONE);
                    userspinner.setVisibility((View.VISIBLE));
                }else{
                    rolespinner.setVisibility(View.VISIBLE);
                    userspinner.setVisibility((View.GONE));
                }
            }
        });

        pickCompleteTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment=new PickTime();
                dialogFragment.show(getSupportFragmentManager(),"complete picker");
            }
        });

        pickstartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment=new PickTime();
                dialogFragment.show(getSupportFragmentManager(),"time picker");
            }
        });

        createDepTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mainActivity.checkConnection(connectivityManager))
                    mainActivity.createDialog(PostTask.this);
                else addView();

            }
        });

        startActiveDate=Calendar.getInstance();
        pickstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(PostTask.this,ChooseDates.class),1);
            }
        });

        completeActiveDate=Calendar.getInstance();
        pickComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(PostTask.this,ChooseDates.class),2);
            }
        });


        createTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mainActivity.checkConnection(connectivityManager))
                    mainActivity.createDialog(PostTask.this);
                else createTask(v);
            }
        });
    }

    private void populateUsers() {
        Call<UserDetail> call = APIClient.getUserManage(PostTask.this).getUsers();
        call.enqueue(new Callback<UserDetail>() {
            @Override
            public void onResponse(Call<UserDetail> call, Response<UserDetail> response) {
                userDetail = response.body();
                users=new String[userDetail.getUser().size()];
                usersId=new int[userDetail.getUser().size()];
                for(int i=0;i<userDetail.getUser().size();i++){
                    users[i]=userDetail.getUser().get(i).getName();
                    usersId[i]=userDetail.getUser().get(i).getId();
                }
                ArrayAdapter arrayAdapter1 = new ArrayAdapter(PostTask.this, android.R.layout.simple_spinner_item, users);
                userspinner.setAdapter(arrayAdapter1);
            }

            @Override
            public void onFailure(Call<UserDetail> call, Throwable t) {
                Toast.makeText(PostTask.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void createTask(View v) {
        InputMethodManager inputMethodManager=(InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(),0);
        if (name.getText().toString().trim().isEmpty()){
            Toast.makeText(PostTask.this, "Please enter task name", Toast.LENGTH_LONG).show();
        }
        else {
            if (startDates==null || startDates.size()==0){
                Snackbar snackbar = Snackbar.make(linearLayout, "Please select a start date", Snackbar.LENGTH_LONG);
                snackbar.show();
                return;
            }
            if (completeDates==null || completeDates.size()==0){
                Snackbar snackbar = Snackbar.make(linearLayout, "Please select a complete date", Snackbar.LENGTH_LONG);
                snackbar.show();
                return;
            }
            if (startDates.size()!=completeDates.size()){
                Snackbar snackbar = Snackbar.make(linearLayout, "Please provide same count of dates", Snackbar.LENGTH_LONG);
                snackbar.show();
                return;
            }
            if (startTimeView.getText().toString().isEmpty()){
                Snackbar snackbar = Snackbar.make(linearLayout, "Please select a start time", Snackbar.LENGTH_LONG);
                snackbar.show();
                return;
            }
            if (completeTimeView.getText().toString().isEmpty()){
                Snackbar snackbar = Snackbar.make(linearLayout, "Please select a complete time", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
            else{

                String taskname=name.getText().toString().trim();
                String role;
                if (MainActivity.ROLE.equals("admin"))
                    role=checkBox.isChecked()?userspinner.getSelectedItem().toString():rolespinner.getSelectedItem().toString();
                else
                    role=MainActivity.ROLE;
//                    MainActivity.ROLE.equals("admin")?rolespinner.getSelectedItem().toString():MainActivity.ROLE;
                CreateTask createTask = new CreateTask(taskname, null, checkIsDataGiven(),
                        role, startDates,completeDates,start,complete,0);
                Call<CreateTask> createTaskCall = APIClient.getTaskManage(PostTask.this).createTask(createTask);
                createTaskCall.enqueue(new Callback<CreateTask>() {
                    @Override
                    public void onResponse(Call<CreateTask> call, Response<CreateTask> response) {
                        if (response.code() == 201) {
                            Snackbar snackbar = Snackbar.make(linearLayout, "Task created", Snackbar.LENGTH_LONG);
                            snackbar.show();
                            name.setText("");
                            name.clearFocus();
                            linearLayout1.removeAllViews();
                        }
                    }

                    @Override
                    public void onFailure(Call<CreateTask> call, Throwable t) {
                        Toast.makeText(PostTask.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
//                if(checkBox.isChecked() && !existtask.isChecked()){
//                    CreateTask createTask1=new CreateTask(taskname);
//                    Call<CreateTask> createTaskCall1=APIClient.getTaskManage(PostTask.this).addmastertask(createTask1);
//                    createTaskCall1.enqueue(new Callback<CreateTask>() {
//                        @Override
//                        public void onResponse(Call<CreateTask> call, Response<CreateTask> response) {
//                            if (response.code()!=200){
//                                Toast.makeText(PostTask.this,String.valueOf(response.code()),Toast.LENGTH_LONG).show();
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<CreateTask> call, Throwable t) {
//                            Toast.makeText(PostTask.this,t.getMessage(),Toast.LENGTH_LONG).show();
//                        }
//                    });
//                }
            }
        }
    }

    public void populateRoles() {
        Call<RoleList> call1=APIClient.getRoleManage(PostTask.this).getAllRoles();
        call1.enqueue(new Callback<RoleList>() {
            @Override
            public void onResponse(Call<RoleList> call, Response<RoleList> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(PostTask.this, "Something went wrong", Toast.LENGTH_LONG).show();
                }
                else {
                    RoleList roleList=response.body();
                    rolenames=new String[roleList.getRoles().size()];
                    for (int i=0;i<roleList.getRoles().size();i++){
                        rolenames[i]=roleList.getRoles().get(i).getRole();
                    }
                    ArrayAdapter arrayAdapter1 = new ArrayAdapter(PostTask.this, android.R.layout.simple_spinner_item, rolenames);
                    rolespinner.setAdapter(arrayAdapter1);
                }
            }

            @Override
            public void onFailure(Call<RoleList> call, Throwable t) {
                Toast.makeText(PostTask.this,t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void populateDependingTask() {
        Call<TaskDetail> call = APIClient.getTaskManage(PostTask.this).getTasks();
        call.enqueue(new Callback<TaskDetail>() {
            @Override
            public void onResponse(Call<TaskDetail> call, Response<TaskDetail> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(PostTask.this, "Something went wrong", Toast.LENGTH_LONG).show();
                } else {
                    taskDetail = response.body();
                    for (int i = 0; i < taskDetail.getTasks().size(); i++) {
                        if (taskDetail.getTasks().get(i).getIsCompleted()==0) {
                            tasks.add(taskDetail.getTasks().get(i));
                        }
//                        if(taskDetail.getTasks().get(i).getRecur()==1){
//                            recurtasks.add(taskDetail.getTasks().get(i));
//                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<TaskDetail> call, Throwable t) {
                Toast.makeText(PostTask.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private int[] checkIsDataGiven() {
        int [] seletecTaskId=new int[linearLayout1.getChildCount()];
        for (int i = 0; i < linearLayout1.getChildCount(); i++) {
            View view = linearLayout1.getChildAt(i);
            Spinner spinner = view.findViewById(R.id.spinner);
            seletecTaskId[i]=taskId[spinner.getSelectedItemPosition()];
        }
        return seletecTaskId;
    }

    private void addView() {
        View nextView = getLayoutInflater().inflate(R.layout.row_add_deptask, null, false);
        ImageView imageView = nextView.findViewById(R.id.close);
        Spinner spinner = nextView.findViewById(R.id.spinner);

        taskId=new int[tasks.size()];
        taskname=new String[tasks.size()];
        for (int i=0;i<tasks.size();i++){
            taskId[i]=tasks.get(i).getId();
            taskname[i]=tasks.get(i).getTaskName();
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, taskname);
        spinner.setAdapter(arrayAdapter);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeView(nextView);
            }
        });

        linearLayout1.addView(nextView);
    }

    private void removeView(View v) {
        linearLayout1.removeView(v);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
        calendar.set(Calendar.MINUTE,minute);
        String formats=new SimpleDateFormat("hh:mm a").format(calendar.getTimeInMillis());
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("hh:mm:ss");
        if (startTimeView.getText().toString().isEmpty()) {
            startTimeView.setText(formats);
            start=hourOfDay+":"+minute+":"+00;
            formats=null;
        }else{
            complete=hourOfDay+":"+minute+":"+00;
            completeTimeView.setText(formats);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==2 && requestCode==1){
            startDates=data.getExtras().getStringArrayList("dates");
            startDateView.setText(String.valueOf(startDates.size()));
        }else if(resultCode==2 && requestCode==2){
            completeDates=data.getExtras().getStringArrayList("dates");
            completeDateView.setText(String.valueOf(completeDates.size()));
        }
    }
}