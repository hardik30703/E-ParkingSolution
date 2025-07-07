package com.example.Project.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
public class Payment_Form {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int PaymentID;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "BookingID")
    @JsonBackReference
    private Booking_Form_Info booking;

    private int DriverID;


    public Payment_Form() {
    }


    public int getPaymentID() {
        return PaymentID;
    }

    public void setPaymentID(int paymentID) {
        PaymentID = paymentID;
    }

    public Booking_Form_Info getBooking() {
        return booking;
    }

    public void setBooking(Booking_Form_Info booking) {
        this.booking = booking;
    }

    public int getDriverID() {
        return DriverID;
    }

    public void setDriverID(int driverID) {
        DriverID = driverID;
    }

}
