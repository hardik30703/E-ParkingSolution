package com.example.Project.Repository;

import com.example.Project.domain.Driver;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface DriverRepository extends CrudRepository<Driver, Integer> {
    Driver findByUsername(String username);

    Driver findByEmail(String email);

    Driver findById(int driverId);

    List<Driver> findByUsernameContainingIgnoreCase(String username);
}
