package com.example.Project.Service;

import com.example.Project.Repository.*;
import com.example.Project.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DriverService {
    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private BookingFormRepository bookingFormRepository;

    @Autowired
    private PaymentFormRepository paymentFormRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private JavaMailSender mailSender;

    //Driver Reg/Login
    public void save(Driver a){
        driverRepository.save(a);
    }

    public Driver findByUsername(String username) {
        return driverRepository.findByUsername(username);
    }

    public Driver findByEmail(String email) {
        return driverRepository.findByEmail(email);
    }

    public Driver findById(int driverId) {
        return driverRepository.findById(driverId);
    }

    public List<Driver> getAllDrivers() {
        return (List<Driver>) driverRepository.findAll();
    }

    public List<String> findUsernamesByUsername(String username) {
        List<Driver> drivers = driverRepository.findByUsernameContainingIgnoreCase(username);
        List<String> usernames = new ArrayList<>();
        for (Driver driver : drivers) {
            usernames.add(driver.getUsername());
        }
        return usernames;
    }


    //Booking Form
    public void savebookform(Booking_Form_Info b){
        bookingFormRepository.save(b);
    }

    public void deleteByBookingId(int id) {
        bookingFormRepository.deleteById(id);
    }

    public List<Booking_Form_Info> getAllParkingHistory() {
        return (List<Booking_Form_Info>) bookingFormRepository.findAll();
    }

    public Booking_Form_Info findByBookingId(int id) {
        return bookingFormRepository.findById(id).get();
    }

    //Payment Form
    public void savepayform(Payment_Form c){
        paymentFormRepository.save(c);
    }

    //Vehicle
    public Vehicle findVehicleById(int id) {
        return vehicleRepository.findById(id).get();
    }
    public List<Vehicle> getAllVehicle() {
        return (List<Vehicle>) vehicleRepository.findAll();
    }

    public void saveVehicle(Vehicle a){
        vehicleRepository.save(a);
    }

    public void deleteByVehicleId(int id) {
        vehicleRepository.deleteById(id);
    }


    //Email
    public void sendEmail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        mailSender.send(message);
    }
    //Notification
    public void createDriverNotification(Driver driver, String message) {
        Notification notification = new Notification();
        notification.setDriver(driver);
        notification.setMessage(message);
        notification.setRead(false);
        notificationRepository.save(notification);
    }

    public List<Notification> getUnreadDriverNotifications(Driver driver) {
        return notificationRepository.findByDriverAndIsRead(driver, false);
    }

    public Notification findByNotificationId(int id) {
        return notificationRepository.findById(id);
    }

    public void saveNotification(Notification notification) {
        notificationRepository.save(notification);
    }

    //Admin
    public Admin findAdminById(int i) {
        return adminRepository.findById(i).get();
    }


}
