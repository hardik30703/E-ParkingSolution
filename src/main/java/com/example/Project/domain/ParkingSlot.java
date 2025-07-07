package com.example.Project.domain;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Entity
public class    ParkingSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int SlotID;

    private LocalTime startTime;

    private LocalTime endTime;

    private Boolean isBooked;

    @ManyToOne
    @JoinColumn(name = "LotID")
    @JsonBackReference
    private ParkingLot parkingLot;


    public int getSlotID() {
        return SlotID;
    }

    public void setSlotID(int slotID) {
        SlotID = slotID;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public Boolean getBooked() {
        return isBooked;
    }

    public void setBooked(Boolean booked) {
        isBooked = booked;
    }

    public ParkingLot getParkingLot() {
        return parkingLot;
    }

    public void setParkingLot(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }
}
