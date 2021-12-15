package com.example.task_manager;

import java.util.List;

public class TaskDetail {

    private List<Tasks> tasks;

    private Tasks task;

    public TaskDetail() {
    }

    public TaskDetail(List<Tasks> tasks) {
        this.tasks = tasks;
    }

    public TaskDetail(Tasks task) {
        this.task = task;
    }

    public Tasks getTask() {
        return task;
    }

    public void setTask(Tasks task) {
        this.task = task;
    }

    public List<Tasks> getTasks() {
        return tasks;
    }

    public void setTasks(List<Tasks> tasks) {
        this.tasks = tasks;
    }
}