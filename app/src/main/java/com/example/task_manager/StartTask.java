package com.example.task_manager;

import java.util.Date;

public class StartTask {

    private String start_time;
    private String completed_at;
    private boolean isCompleted;
    private String comments;

    public StartTask(String start_time) {
        this.start_time = start_time;
    }

    public StartTask(boolean isCompleted,String comments)
    {
        this.comments=comments;
        this.isCompleted=isCompleted;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getCompleted_at() {
        return completed_at;
    }

    public void setCompleted_at(String completed_at) {
        this.completed_at = completed_at;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}
