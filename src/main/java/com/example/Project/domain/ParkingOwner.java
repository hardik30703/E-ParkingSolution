package com.example.Project.domain;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;

@Entity
public class ParkingOwner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int OwnerID;

    private String username;

    private String password;

    private String firstname;

    private String lastname;

    private String email;

    @OneToMany(mappedBy = "owner")
    @JsonManagedReference
    private List<ParkingLot> parkingLots;

    @OneToMany(mappedBy = "owner")
    @JsonManagedReference
    private List<Notification> notifications;


    public ParkingOwner() {
    }

    public ParkingOwner(String username, String password, String firstname, String lastname, String email) {
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }

    public int getOwnerID() {
        return OwnerID;
    }

    public void setOwnerID(int ownerID) {
        OwnerID = ownerID;
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

    public List<ParkingLot> getParkingLots() {
        return parkingLots;
    }

    public void setParkingLots(List<ParkingLot> parkingLots) {
        this.parkingLots = parkingLots;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }
}
