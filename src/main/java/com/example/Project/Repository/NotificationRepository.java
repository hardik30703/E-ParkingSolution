package com.example.Project.Repository;

import com.example.Project.domain.Driver;
import com.example.Project.domain.Notification;
import com.example.Project.domain.ParkingOwner;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NotificationRepository extends CrudRepository<Notification, Integer> {

    List<Notification> findByOwnerAndIsRead(ParkingOwner owner, boolean isRead);

    List<Notification> findByDriverAndIsRead(Driver driver, boolean isRead);

    Notification findById(int id);
}
