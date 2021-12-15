package com.example.task_manager;

import com.google.gson.annotations.SerializedName;

public class LoginCredentials {

    @SerializedName("employee_id")
    private String empID;

    private String password;

    public LoginCredentials(String empID, String password) {
        this.empID = empID;
        this.password = password;
    }

    public String getEmpID() {
        return empID;
    }

    public void setEmpID(String empID) {
        this.empID = empID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
