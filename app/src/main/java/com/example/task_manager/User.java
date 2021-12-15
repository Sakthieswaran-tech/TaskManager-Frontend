package com.example.task_manager;

import com.google.gson.annotations.SerializedName;

public class User {

    private int id;

    @SerializedName("employee_name")
    private String name;

    @SerializedName("employee_id")
    private String empID;

    @SerializedName("employee_reg_no")
    private String empNO;

    private String password;
    private String email;
    private String role;

    public User(int id, String name, String empID, String empNO, String password, String email, String role) {
        this.id = id;
        this.name = name;
        this.empID = empID;
        this.empNO = empNO;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public User(String name, String empID) {
        this.name=name;
        this.empID=empID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmpID() {
        return empID;
    }

    public void setEmpID(String empID) {
        this.empID = empID;
    }

    public String getEmpNO() {
        return empNO;
    }

    public void setEmpNO(String empNO) {
        this.empNO = empNO;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
