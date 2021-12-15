package com.example.task_manager;

public class CreateUser {

    private String employee_name;
    private String employee_id;
    private String employee_reg_no;
    private String password;
    private String email;
    private String role;

    public CreateUser(String employee_name, String employee_id, String employee_reg_no, String password, String email, String role) {
        this.employee_name = employee_name;
        this.employee_id = employee_id;
        this.employee_reg_no = employee_reg_no;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public String getEmployee_name() {
        return employee_name;
    }

    public void setEmployee_name(String employee_name) {
        this.employee_name = employee_name;
    }

    public String getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(String employee_id) {
        this.employee_id = employee_id;
    }

    public String getEmployee_reg_no() {
        return employee_reg_no;
    }

    public void setEmployee_reg_no(String employee_reg_no) {
        this.employee_reg_no = employee_reg_no;
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
