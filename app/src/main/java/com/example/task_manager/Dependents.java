package com.example.task_manager;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Dependents {

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

    private int isCompleted;

    public Dependents(int id, String taskName, String taskID, String depTask, String createdBy, Date createdAt, Date startTime, Date completedAt, int isCompleted) {
        this.id = id;
        this.taskName = taskName;
        this.taskID = taskID;
        this.depTask = depTask;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.startTime = startTime;
        this.completedAt = completedAt;
        this.isCompleted = isCompleted;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
