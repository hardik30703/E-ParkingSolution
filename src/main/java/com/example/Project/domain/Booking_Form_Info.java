package com.example.Project.domain;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;

@Entity
public class Booking_Form_Info {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int BookingID;

    private String name;

    private String postcode;

    private String carregnumber;

    private String date;

    private String slottime;

    private double amount;

    private int hours;

    @ManyToOne
    @JoinColumn(name = "DriverID")
    @JsonBackReference
    private Driver driver;

    @ManyToOne
    @JoinColumn(name = "LotID")
    @JsonBackReference
    private ParkingLot parkingLot;

    @OneToOne(mappedBy = "booking", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private Payment_Form payment;

    private int SlotID;

    public Booking_Form_Info() {
    }

    public Booking_Form_Info(String name, String postcode, String carregnumber, String date, String slottime, double amount, int hours) {
        this.name = name;
        this.carregnumber = carregnumber;
        this.date = date;
        this.slottime = slottime;
        this.postcode = postcode;
        this.amount = amount;
        this.hours = hours;
    }

    public int getBookingID() {
        return BookingID;
    }

    public void setBookingID(int bookingID) {
        BookingID = bookingID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCarregnumber() {
        return carregnumber;
    }

    public void setCarregnumber(String carregnumber) {
        this.carregnumber = carregnumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSlottime() {
        return slottime;
    }

    public void setSlottime(String slottime) {
        this.slottime = slottime;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public ParkingLot getParkingLot() {
        return parkingLot;
    }

    public void setParkingLot(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }

    public Payment_Form getPayment() {
        return payment;
    }

    public void setPayment(Payment_Form payment) {
        this.payment = payment;
    }

    public int getSlotID() {
        return SlotID;
    }

    public void setSlotID(int slotID) {
        SlotID = slotID;
    }
}


