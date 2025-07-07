package com.example.Project.controller;

import com.example.Project.Service.DriverService;
import com.example.Project.Service.ParkingOwnerService;
import com.example.Project.domain.Driver;
import com.example.Project.domain.ParkingOwner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class RegistrationController {
    @Autowired
    DriverService ds;
    @Autowired
    ParkingOwnerService ps;

    @GetMapping("/DriverRegister")
    public String showRegistrationForm() {
        return "DriverRegister";
    }

    @PostMapping("/DriverRegister")
    public String submitRegistrationForm(@RequestParam String username, @RequestParam String password,
                                         @RequestParam String firstname, @RequestParam String lastname,
                                         @RequestParam String email, HttpSession session, Model model) {
        Driver existingDriver = ds.findByEmail(email);
        Driver exDriver = ds.findByUsername(username);
        if (existingDriver != null || exDriver != null) {
            model.addAttribute("errorMessage", "Email or Username already exist");
            return "DriverRegister";
        }

        Driver driver = new Driver(username, password, firstname, lastname, email);
        ds.save(driver);
        session.setAttribute("driverId", driver.getDriverID());
        return "SearchParking";
    }

    @GetMapping("/ParkingOwnerRegister")
    public String showPORegistrationForm() {
        return "ParkingOwnerRegister";
    }

    @PostMapping("/ParkingOwnerRegister")
    public String submitPORegistrationForm(@RequestParam String username, @RequestParam String password,
                                           @RequestParam String firstname, @RequestParam String lastname,
                                           @RequestParam String email, HttpSession session, Model model) {
        ParkingOwner existingOwner = ps.findByEmail(email);
        ParkingOwner exOwner = ps.findByUsername(username);
        if (existingOwner != null || exOwner != null) {
            model.addAttribute("errorMessage", "Email or Username already exist");
            return "ParkingOwnerRegister";
        }

        ParkingOwner parkingOwner = new ParkingOwner(username, password, firstname, lastname, email);
        ps.save(parkingOwner);
        session.setAttribute("ownerId", parkingOwner.getOwnerID());
        return "ParkingOwner_Dashboard";
    }

}
