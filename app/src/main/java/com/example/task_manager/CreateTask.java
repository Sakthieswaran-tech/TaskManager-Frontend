package com.example.task_manager;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CreateTask {

    private String task_name;
    private String taskID;
    private int[] depending_task_ids;
    private String assigned_to;
    private ArrayList<String> estimated_start;
    private ArrayList<String> estimated_complete;
    private String estimated_start_time;
    private String estimated_complete_time;
    private int recur;

    public CreateTask(String task_name, String taskID, int[] depending_task_ids, String assigned_to,ArrayList<String> estimated_start,
                      ArrayList<String> estimated_complete,String estimated_start_time,String estimated_complete_time,int recur) {
        this.task_name = task_name;
        this.taskID = taskID;
        this.depending_task_ids = depending_task_ids;
        this.assigned_to=assigned_to;
        this.recur=recur;
        this.estimated_start=estimated_start;
        this.estimated_complete=estimated_complete;
        this.estimated_start_time=estimated_start_time;
        this.estimated_complete_time=estimated_complete_time;
    }

    public int getRecur() {
        return recur;
    }

    public void setRecur(int recur) {
        this.recur = recur;
    }

    public CreateTask(String task_name) {
        this.task_name = task_name;
    }

    public String getEstimated_start_time() {
        return estimated_start_time;
    }

    public void setEstimated_start_time(String estimated_start_time) {
        this.estimated_start_time = estimated_start_time;
    }

    public String getEstimated_complete_time() {
        return estimated_complete_time;
    }

    public void setEstimated_complete_time(String estimated_complete_time) {
        this.estimated_complete_time = estimated_complete_time;
    }

    public ArrayList<String> getEstimated_start() {
        return estimated_start;
    }

    public void setEstimated_start(ArrayList<String> estimated_start) {
        this.estimated_start = estimated_start;
    }

    public String getAssigned_to() {
        return assigned_to;
    }

    public void setAssigned_to(String assigned_to) {
        this.assigned_to = assigned_to;
    }

    public String getTask_name() {
        return task_name;
    }

    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }

    public String getTaskID() {
        return taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    public int[] getDepending_task() {
        return depending_task_ids;
    }

    public void setDepending_task(int[] depending_task) {
        this.depending_task_ids = depending_task;
    }

    public ArrayList<String> getEstimated_complete() {
        return estimated_complete;
    }

    public void setEstimated_complete(ArrayList<String> estimated_complete) {
        this.estimated_complete = estimated_complete;
    }
}
