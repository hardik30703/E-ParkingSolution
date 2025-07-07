package com.example.Project.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.persistence.*;


@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "OwnerID")
    @JsonBackReference
    private ParkingOwner owner;

    @ManyToOne
    @JoinColumn(name = "DriverID")
    @JsonBackReference
    private Driver driver;

    private String message;

    private boolean isRead;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ParkingOwner getOwner() {
        return owner;
    }

    public void setOwner(ParkingOwner owner) {
        this.owner = owner;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

}

