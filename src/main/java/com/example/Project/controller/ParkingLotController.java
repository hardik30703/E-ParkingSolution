package com.example.Project.controller;

import com.example.Project.Service.DriverService;
import com.example.Project.Service.ParkingOwnerService;
import com.example.Project.domain.*;
import com.example.Project.Service.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ParkingLotController {

    @Autowired
    ParkingLotService pls;

    @Autowired
    ParkingOwnerService pos;

    @Autowired
    DriverService ds;

    @GetMapping("/OwnerDashboard")
    public String ownerDashboard() {
        return "ParkingOwner_Dashboard";
    }

    @GetMapping("/AddParkingLot")
    public String showAddParkingLotForm() {
        return "AddParkingLot";
    }

    @PostMapping("/AddParkingLot")
    public String submitAddParkingLotForm(@RequestParam String name, @RequestParam String address,
                                         @RequestParam String city, @RequestParam String postcode,
                                         @RequestParam double price, HttpSession session) {

        String upperPostcode = postcode.toUpperCase();

        ParkingLot parkingLot = new ParkingLot(name, address, city, upperPostcode, price);
        int ownerId = (int) session.getAttribute("ownerId"); //gets the OwnerID from the session
        ParkingOwner owner = pos.findById(ownerId);
        parkingLot.setOwner(owner);
        parkingLot.setApproved(false);
        pls.save(parkingLot);

        for (int hour = 0; hour < 23; hour++) {
            ParkingSlot parkingSlot = new ParkingSlot();
            parkingSlot.setStartTime(LocalTime.of(hour, 0));
            parkingSlot.setEndTime(LocalTime.of(hour + 1, 0));
            parkingSlot.setBooked(false);
            parkingSlot.setParkingLot(parkingLot);
            pls.save(parkingSlot);
        }

        String username = owner.getUsername();

        Admin admin = ds.findAdminById(1);
        String adminEmail = admin.getEmail();

        String content = username + " needs a decision on their new Parking Lot";

        // Send the confirmation email
        ds.sendEmail(adminEmail, "Parking Lot Decision Needed", content);
        return "redirect:/ManageParkingLot";
    }

    @GetMapping("/ManageParkingLot")
    public String showParkingLots(Model model, HttpSession session) {
        int ownerId = (int) session.getAttribute("ownerId");
        ParkingOwner owner = pos.findById(ownerId);

        List<ParkingLot> allLots = owner.getParkingLots();
        List<ParkingLot> approvedLots = new ArrayList<>();
        List<ParkingLot> pendingLots = new ArrayList<>();

        for (ParkingLot lots : allLots) {
            if (lots.isApproved()) {
                approvedLots.add(lots);
            } else {
                pendingLots.add(lots);
            }
        }

        List<Notification> notifications = pos.getUnreadNotifications(owner);

        model.addAttribute("notifications", notifications);
        model.addAttribute("approvedLots", approvedLots);
        model.addAttribute("pendingLots", pendingLots);
        return "ManageParkingLot";
    }

    @PostMapping("/notifications/{id}/read")
    public String markNotificationAsRead(@PathVariable int id) {
        Notification notification = pos.findByNotificationId(id);
        if (notification != null) {
            notification.setRead(true);
            pos.saveNotification(notification);
        }
        return "redirect:/ManageParkingLot";
    }

    @RequestMapping(value = "/edit/{id}")
    public String edit(Model model, @PathVariable int id){
        ParkingLot parkingLot = pls.findById(id);
        model.addAttribute("lotID", parkingLot.getLotID());
        model.addAttribute("name", parkingLot.getName());
        model.addAttribute("address", parkingLot.getAddress());
        model.addAttribute("postcode", parkingLot.getPostcode());
        model.addAttribute("city", parkingLot.getCity());
        model.addAttribute("price", parkingLot.getPrice());

        return "EditParkingLot";
    }

    @PostMapping(value = "/update")
    public ModelAndView update(ParkingLot parkingLot, HttpSession session){
        int ownerId = (int) session.getAttribute("ownerId");
        ParkingOwner owner = pos.findById(ownerId);
        parkingLot.setOwner(owner);

        // Get the existing parking lot from the database
        ParkingLot existingLot = pls.findById(parkingLot.getLotID());

        // Delete all existing slots for this parking lot
        List<ParkingSlot> existingSlots = existingLot.getParkingSlots();
        for (ParkingSlot slot : existingSlots) {
            pls.delete(slot);
        }

        // Save the updated parking lot
        pls.save(parkingLot);

        for (int hour = 0; hour < 23; hour++) {
            ParkingSlot parkingSlot = new ParkingSlot();
            parkingSlot.setStartTime(LocalTime.of(hour, 0));
            parkingSlot.setEndTime(LocalTime.of(hour + 1, 0));
            parkingSlot.setBooked(false);
            parkingSlot.setParkingLot(parkingLot);
            pls.save(parkingSlot);
        }

        return new ModelAndView("redirect:/ManageParkingLot");
    }


    @RequestMapping(value = "/delete/{id}")
    public ModelAndView delete(@PathVariable int id){
        pls.deleteById(id);
        return new ModelAndView("redirect:/ManageParkingLot");
    }

    @RequestMapping("/viewBookings/{id}")
    public String viewBookings(@PathVariable("id") int lotId, Model model){
        // Fetch the ParkingLot object using the provided ID
        ParkingLot parkingLot = pls.findById(lotId);

        String name = parkingLot.getName();

        // Get the bookings of the parking lot
        List<Booking_Form_Info> booking_form_infos = parkingLot.getBookings();

        // Calculate the total amount
        double totalAmount = booking_form_infos.stream()
                .mapToDouble(Booking_Form_Info::getAmount)
                .sum();

        model.addAttribute("name", name);
        model.addAttribute("booking_form_infos", booking_form_infos);
        model.addAttribute("totalAmount", totalAmount);
        return "ViewBookings";
    }


}
