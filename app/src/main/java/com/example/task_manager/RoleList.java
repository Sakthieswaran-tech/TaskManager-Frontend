package com.example.task_manager;

import java.util.List;

public class RoleList {

    private List<Roles> roles;

    public RoleList(List<Roles> roles) {
        this.roles = roles;
    }

    public List<Roles> getRoles() {
        return roles;
    }

    public void setRoles(List<Roles> roles) {
        this.roles = roles;
    }
}
