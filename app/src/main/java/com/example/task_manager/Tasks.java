package com.example.task_manager;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class Tasks {

    private int id;

    @SerializedName("task_name")
    private String taskName;
    private String taskID;

    @SerializedName("depending_task")
    private String depTask;

    @SerializedName("created_by")
    private String createdBy;

    @SerializedName("created_at")
    private Date createdAt;

    @SerializedName("start_time")
    private Date startTime;

    @SerializedName("completed_at")
    private Date completedAt;

    private Date estimated_start;
    private Date estimated_complete;
    private String estimated_start_time;
    private String estimated_complete_time;

    private String comments;

    private List<Dependents> dependents;

    private int isCompleted;

    public Tasks(int id,String taskName, String taskID, String depTask,
                 String createdBy,
                 String estimated_start_time,String estimated_complete_time,Date createdAt, Date startTime, Date completedAt, int isCompleted,Date estimated_start,Date estimated_complete,String comments) {
        this.estimated_start=estimated_start;
        this.comments=comments;
        this.estimated_complete=estimated_complete;
        this.id = id;
        this.taskName = taskName;
        this.taskID = taskID;
        this.depTask = depTask;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.startTime = startTime;
        this.completedAt = completedAt;
        this.isCompleted = isCompleted;
        this.estimated_start_time=estimated_start_time;
        this.estimated_complete_time=estimated_complete_time;
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

    public Tasks(int id, String taskName, String taskID, String depTask, String createdBy, Date createdAt, Date startTime, Date completedAt, List<Dependents> dependents, int isCompleted) {
        this.id = id;
        this.taskName = taskName;
        this.taskID = taskID;
        this.depTask = depTask;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.startTime = startTime;
        this.completedAt = completedAt;
        this.dependents = dependents;
        this.isCompleted = isCompleted;
    }

    public Tasks(String taskID, String depTask, String taskName) {
        this.taskID= taskID;
        this.depTask = depTask;
        this.taskName=taskName;
    }

    public Date getEstimated_start() {
        return estimated_start;
    }

    public void setEstimated_start(Date estimated_start) {
        this.estimated_start = estimated_start;
    }

    public Date getEstimated_complete() {
        return estimated_complete;
    }

    public void setEstimated_complete(Date estimated_complete) {
        this.estimated_complete = estimated_complete;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskID() {
        return taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    public String getDepTask() {
        return depTask;
    }

    public void setDepTask(String depTask) {
        this.depTask = depTask;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(Date completedAt) {
        this.completedAt = completedAt;
    }

    public int getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(int isCompleted) {
        this.isCompleted = isCompleted;
    }

    public List<Dependents> getDependents() {
        return dependents;
    }

    public void setDependents(List<Dependents> dependents) {
        this.dependents = dependents;
    }
}
