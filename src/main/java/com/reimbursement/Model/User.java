package com.reimbursement.Model;

public class User {
    private int id;
    private String username;
    private String password;
    private String role = "employee";

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }

    public void setRole(String role) {
	    this.role = role;
    }

    public String getRole() {
	    return this.role;
    }

    public String toString() {
        return "{ id: " + this.id + ", username: " + this.username + ", password: " + this.password + ", role: " + this.role + "}";
    }

    public User() {
        this.username = "";
        this.password = "";
    }
}
