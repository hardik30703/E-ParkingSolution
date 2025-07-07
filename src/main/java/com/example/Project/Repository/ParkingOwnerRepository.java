package com.example.Project.Repository;

import com.example.Project.domain.Driver;
import com.example.Project.domain.ParkingOwner;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ParkingOwnerRepository extends CrudRepository<ParkingOwner, Integer> {

    ParkingOwner findByUsername(String username);

    ParkingOwner findById(int ownerId);

    ParkingOwner findByEmail(String email);

    List<ParkingOwner> findByUsernameContainingIgnoreCase(String username);
}
