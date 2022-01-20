package com.example.task_manager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilterTasks extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    TextView start, to;
    Spinner spinner;
    RecyclerView recyclerView;
    int flag;
    RoleList roleList;
    String[] rolenames;
    Button button;
    String starts, end;
    Date stardate, enddate;
    RelativeLayout relativeLayout;
    TaskDetail taskDetail;
    TaskListAdapter taskListAdapter;
    CheckBox checkBox;
    List<TaskDetail> list = new ArrayList<>();
    String role = MainActivity.ROLE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_tasks);

        start = findViewById(R.id.fromdate);
        to = findViewById(R.id.todate);
        spinner = findViewById(R.id.filterByRole);
        recyclerView = findViewById(R.id.filteredtask);
        button = findViewById(R.id.getfilteredtask);
        relativeLayout = findViewById(R.id.filterrelative);
        checkBox = findViewById(R.id.checkbox);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        if (role.equals("admin") || role.equals("Manager")) {
            fetchRoles();
        } else {
            spinner.setVisibility(View.INVISIBLE);
            checkBox.setVisibility(View.INVISIBLE);
        }

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datepicker = new PickDate();
                datepicker.show(getSupportFragmentManager(), "date picker");
                flag = 0;
            }
        });

        to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datepicker = new PickDate();
                datepicker.show(getSupportFragmentManager(), "date picker");
                flag = 1;
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Date date = Calendar.getInstance().getTime();
                    SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    String format = simpleDateFormat.format(date);
                    Call<TaskDetail> call;
                    if (role.equals("admin") || role.equals("Manager")) {
                        if (!checkBox.isChecked()) {
                            if (starts == null && end != null) {
                                end = simpleDateFormat1.format(enddate);
                                call = APIClient.getTaskManage(FilterTasks.this)
                                        .getFilterTasks(spinner.getSelectedItem().toString(), format, end);
                            } else if (starts != null && end == null) {
                                starts = simpleDateFormat1.format(stardate);
                                call = APIClient.getTaskManage(FilterTasks.this)
                                        .getFilterTasks(spinner.getSelectedItem().toString(), starts, format);
                            } else if (starts == null && end == null) {
                                call = APIClient.getTaskManage(FilterTasks.this)
                                        .getFilterTaskByRole(spinner.getSelectedItem().toString());
                            } else {
                                starts = simpleDateFormat1.format(stardate);
                                end = simpleDateFormat1.format(enddate);
                                call = APIClient.getTaskManage(FilterTasks.this)
                                        .getFilterTasks(spinner.getSelectedItem().toString(), starts, end);
                            }
                        } else {
                            starts = simpleDateFormat1.format(stardate);
                            end = simpleDateFormat1.format(enddate);
                            call = APIClient.getTaskManage(FilterTasks.this).getTasksByDateOnly(starts, end);
                        }
                    } else {
                        if (starts == null && end != null) {
                            end = simpleDateFormat1.format(enddate);
                            call = APIClient.getTaskManage(FilterTasks.this)
                                    .getFilterTasks(role, format, end);
                        } else if (starts != null && end == null) {
                            starts = simpleDateFormat1.format(stardate);
                            call = APIClient.getTaskManage(FilterTasks.this)
                                    .getFilterTasks(role, starts, format);
                        } else if (starts == null && end == null) {
                            call = APIClient.getTaskManage(FilterTasks.this)
                                    .getFilterTaskByRole(role);
                        } else {
                            starts = simpleDateFormat1.format(stardate);
                            end = simpleDateFormat1.format(enddate);
                            call = APIClient.getTaskManage(FilterTasks.this)
                                    .getFilterTasks(role, starts, end);
                        }
                    }
                    Snackbar snackbar = Snackbar.make(relativeLayout, "Filter applied", Snackbar.LENGTH_LONG);
                    snackbar.show();
                    call.enqueue(new Callback<TaskDetail>() {
                        @Override
                        public void onResponse(Call<TaskDetail> call, Response<TaskDetail> response) {
                            if (response.code() == 200) {
                                if (taskDetail != null) {
                                    taskDetail = response.body();
                                    taskListAdapter.setTasks(taskDetail.getTasks());
                                } else {
                                    taskDetail = response.body();
                                    putdata(taskDetail.getTasks());
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<TaskDetail> call, Throwable t) {

                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(FilterTasks.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void putdata(List<Tasks> tasks) {
        taskListAdapter = new TaskListAdapter(tasks, FilterTasks.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(FilterTasks.this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(FilterTasks.this, DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.recyclerview_divider));
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(taskListAdapter);
    }

    private void fetchRoles() {
        Call<RoleList> call = APIClient.getRoleManage(FilterTasks.this).getAllRoles();
        call.enqueue(new Callback<RoleList>() {
            @Override
            public void onResponse(Call<RoleList> call, Response<RoleList> response) {
                if (response.code() == 200) {
                    roleList = response.body();
                    rolenames = new String[roleList.getRoles().size()];
                    for (int i = 0; i < rolenames.length; i++) {
                        rolenames[i] = roleList.getRoles().get(i).getRole();
                    }
                    ArrayAdapter arrayAdapter1 = new ArrayAdapter(FilterTasks.this, android.R.layout.simple_spinner_item, rolenames);
                    spinner.setAdapter(arrayAdapter1);
                }
            }

            @Override
            public void onFailure(Call<RoleList> call, Throwable t) {
                Toast.makeText(FilterTasks.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        if (flag == 0) {
            stardate = calendar.getTime();
            starts = DateFormat.getDateInstance().format(calendar.getTime());
            start.setText(starts);
        } else {
            enddate = calendar.getTime();
            end = DateFormat.getDateInstance().format(calendar.getTime());
            to.setText(end);

        }

    }
}