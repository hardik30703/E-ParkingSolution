package com.example.Project.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int VehicleID;

    private String regNumber;

    private String make;

    private String colour;

    private boolean isApproved;

    @ManyToOne
    @JoinColumn(name = "DriverID")
    @JsonBackReference
    private Driver driver;

    public Vehicle(){
    }

    public Vehicle(String cleanedRegNumber, String make, String colour) {
        regNumber = cleanedRegNumber;
        this.make = make;
        this.colour = colour;
    }

    public int getVehicleID() {
        return VehicleID;
    }

    public void setVehicleID(int vehicleID) {
        VehicleID = vehicleID;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }
}
