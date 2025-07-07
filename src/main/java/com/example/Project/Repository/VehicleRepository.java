package com.example.Project.Repository;

import com.example.Project.domain.Vehicle;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends CrudRepository<Vehicle, Integer> {
    Vehicle findByRegNumber(String carregnumber);
}
