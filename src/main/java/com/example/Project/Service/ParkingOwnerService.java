package com.example.Project.Service;

import com.example.Project.Repository.NotificationRepository;
import com.example.Project.domain.Driver;
import com.example.Project.domain.Notification;
import com.example.Project.domain.ParkingOwner;
import com.example.Project.Repository.ParkingOwnerRepository;
import com.example.Project.domain.ParkingSlot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ParkingOwnerService {
    @Autowired
    private ParkingOwnerRepository parkingOwnerRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    public void save(ParkingOwner a){
        parkingOwnerRepository.save(a);
    }

    public ParkingOwner findByUsername(String username) {
        return parkingOwnerRepository.findByUsername(username);
    }

    public ParkingOwner findByEmail(String email) {
        return parkingOwnerRepository.findByEmail(email);
    }

    public ParkingOwner findById(int ownerId) {
        return parkingOwnerRepository.findById(ownerId);
    }

    public List<ParkingOwner> getAllOwners() {
        return (List<ParkingOwner>) parkingOwnerRepository.findAll();
    }

    public List<String> findUsernamesByUsername(String username) {
        List<ParkingOwner> owners = parkingOwnerRepository.findByUsernameContainingIgnoreCase(username);
        List<String> usernames = new ArrayList<>();
        for (ParkingOwner owner : owners) {
            usernames.add(owner.getUsername());
        }
        return usernames;
    }

    //Notification
    public void createNotification(ParkingOwner owner, String message) {
        Notification notification = new Notification();
        notification.setOwner(owner);
        notification.setMessage(message);
        notification.setRead(false);
        notificationRepository.save(notification);
    }

    public List<Notification> getUnreadNotifications(ParkingOwner owner) {
        return notificationRepository.findByOwnerAndIsRead(owner, false);
    }

    public Notification findByNotificationId(int id) {
        return notificationRepository.findById(id);
    }

    public void saveNotification(Notification notification) {
        notificationRepository.save(notification);
    }
}
