package com.example.Project.domain;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;

@Entity
public class ParkingLot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int LotID;

    private String name;

    private String address;

    private String city;

    private String postcode;

    private double price;

    private boolean isApproved;

    @ManyToOne
    @JoinColumn(name = "OwnerID")
    @JsonBackReference
    private ParkingOwner owner;

    @OneToMany(mappedBy = "parkingLot", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<Booking_Form_Info> bookings;

    @OneToMany(mappedBy = "parkingLot", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<ParkingSlot> parkingSlots;


    public ParkingLot() {
    }


    public ParkingLot(String name, String address, String city, String postcode, double price) {
        this.name = name;
        this.address = address;
        this.city = city;
        this.postcode = postcode;
        this.price = price;
    }

    public int getLotID() {
        return LotID;
    }

    public void setLotID(int lotID) {
        LotID = lotID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }

    public ParkingOwner getOwner() {
        return owner;
    }

    public void setOwner(ParkingOwner owner) {
        this.owner = owner;
    }

    public List<Booking_Form_Info> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking_Form_Info> bookings) {
        this.bookings = bookings;
    }

    public List<ParkingSlot> getParkingSlots() {
        return parkingSlots;
    }

    public void setParkingSlots(List<ParkingSlot> parkingSlots) {
        this.parkingSlots = parkingSlots;
    }
}