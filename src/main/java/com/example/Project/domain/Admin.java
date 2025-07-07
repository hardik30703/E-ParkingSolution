package com.example.Project.domain;
import javax.persistence.*;

@Entity
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int AdminID;

    private String username;

    private String password;

    private String email;

    public Admin() {
    }

    public int getAdminID() {
        return AdminID;
    }

    public void setAdminID(int adminID) {
        AdminID = adminID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
}
