package com.example.task_manager;

import java.util.Date;

public class GraphData {

    private int id_count;
    private Date completed_count;

    public GraphData(int id_count, Date completed_count) {
        this.id_count = id_count;
        this.completed_count = completed_count;
    }

    public int getId_count() {
        return id_count;
    }

    public void setId_count(int id_count) {
        this.id_count = id_count;
    }

    public Date getCompleted_count() {
        return completed_count;
    }

    public void setCompleted_count(Date completed_count) {
        this.completed_count = completed_count;
    }
}
