package com.example.Project.Repository;

import com.example.Project.domain.Driver;
import com.example.Project.domain.ParkingLot;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingLotRepository extends CrudRepository<ParkingLot, Integer> {

    ParkingLot findByName(String name);

}
