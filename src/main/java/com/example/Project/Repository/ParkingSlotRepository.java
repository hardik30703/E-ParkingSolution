package com.example.Project.Repository;

import com.example.Project.domain.ParkingLot;
import com.example.Project.domain.ParkingSlot;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParkingSlotRepository extends CrudRepository<ParkingSlot, Integer> {

    List<ParkingSlot> findAllByParkingLot(Optional<ParkingLot> lotId);

}
