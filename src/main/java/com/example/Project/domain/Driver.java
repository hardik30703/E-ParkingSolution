package com.example.Project.domain;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;

@Entity
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int DriverID;

    private String username;

    private String password;

    private String firstname;

    private String lastname;

    private String email;

    @OneToMany(mappedBy = "driver")
    @JsonManagedReference
    private List<Vehicle> vehicles;

    @OneToMany(mappedBy = "driver")
    @JsonManagedReference
    private List<Booking_Form_Info> bookings;

    @OneToMany(mappedBy = "driver")
    @JsonManagedReference
    private List<Notification> notifications;


    public Driver() {
    }

    public Driver(String username, String password, String firstname, String lastname, String email) {
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }

    public int getDriverID() {
        return DriverID;
    }

    public void setDriverID(int driverID) {
        DriverID = driverID;
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

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public List<Booking_Form_Info> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking_Form_Info> bookings) {
        this.bookings = bookings;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }
}
