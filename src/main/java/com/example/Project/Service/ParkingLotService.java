package com.example.Project.Service;

import com.example.Project.Repository.ParkingLotRepository;
import com.example.Project.Repository.ParkingOwnerRepository;
import com.example.Project.Repository.ParkingSlotRepository;
import com.example.Project.domain.ParkingLot;
import com.example.Project.domain.ParkingSlot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ParkingLotService {
    @Autowired
    private ParkingLotRepository parkinglotRepository;

    @Autowired
    private ParkingSlotRepository parkingSlotRepository;

    //ParkingLot
    public void save(ParkingLot a){
        parkinglotRepository.save(a);
    }

    public List<ParkingLot> getAllParkingLots() {
        return (List<ParkingLot>) parkinglotRepository.findAll();
    }

    public ParkingLot findById(int i) {
        return parkinglotRepository.findById(i).get();
    }

    public void deleteById(int id) {
        parkinglotRepository.deleteById(id);
    }

    public ParkingLot findByName(String name) {
        return parkinglotRepository.findByName(name);
    }


    //Parking Slot
    public List<ParkingSlot> getAllParkingSlots() {
        return (List<ParkingSlot>) parkingSlotRepository.findAll();
    }

    public ParkingSlot findBySlotId(int i) {
        return parkingSlotRepository.findById(i).get();
    }

    public void save(ParkingSlot a){
        parkingSlotRepository.save(a);
    }

    public void delete(ParkingSlot slot) {
        parkingSlotRepository.delete(slot);
    }

    public List<ParkingSlot> findAllByParkingLotId(int lotId) {
        Optional<ParkingLot> parkingLot = parkinglotRepository.findById(lotId);
        return parkingSlotRepository.findAllByParkingLot(parkingLot);
    }

    @Scheduled(cron = "0 0 0 * * ?") // runs every day at midnight
    public void resetBookings() {
        Iterable<ParkingSlot> parkingSlotsIterable = parkingSlotRepository.findAll();
        List<ParkingSlot> parkingSlots = StreamSupport.stream(parkingSlotsIterable.spliterator(), false)
                .collect(Collectors.toList());
        for (ParkingSlot parkingSlot : parkingSlots) {
            parkingSlot.setBooked(false);
            parkingSlotRepository.save(parkingSlot);
        }
    }
}

