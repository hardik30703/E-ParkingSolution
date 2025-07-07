package com.example.Project.controller;

import com.example.Project.Service.AdminService;
import com.example.Project.Service.DriverService;
import com.example.Project.Service.ParkingOwnerService;
import com.example.Project.domain.Admin;
import com.example.Project.domain.Driver;
import com.example.Project.domain.ParkingOwner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;


@Controller
public class LoginController {
    @Autowired
    ParkingOwnerService ps;
    @Autowired
    DriverService ds;

    @Autowired
    AdminService as;

    @GetMapping("/DriverLogin")
    public String showLoginForm() {
        return "DriverLogin";
    }

    @PostMapping("/DriverLogin")
    public String submitLoginForm(@RequestParam String username, @RequestParam String password,
                                  HttpSession session, Model model) {
        Driver driver = ds.findByUsername(username);
        if (driver != null && driver.getPassword().equals(password)) {
            session.setAttribute("driverId", driver.getDriverID()); // Store driver ID in session
            return "SearchParking";
        }
        model.addAttribute("errorMessage", "Invalid Username and Password");
        return "DriverLogin";
    }


    @GetMapping("/ParkingOwnerLogin")
    public String showPOLoginForm() {
        return "ParkingOwnerLogin";
    }

    @PostMapping("/ParkingOwnerLogin")
    public String submitPOLoginForm(@RequestParam String username, @RequestParam String password,
                                    HttpSession session, Model model) {
        ParkingOwner parkingowner = ps.findByUsername(username);
        if (parkingowner != null && parkingowner.getPassword().equals(password)) {
            session.setAttribute("ownerId", parkingowner.getOwnerID());
            return "ParkingOwner_Dashboard";
        }
        model.addAttribute("errorMessage", "Invalid Username and Password");
        return "ParkingOwnerLogin";
    }

    @GetMapping("/AdminLogin")
    public String showAdminLoginForm() {
        return "AdminLogin";
    }

    @PostMapping("/AdminLogin")
    public String submitAdminLoginForm(@RequestParam String username, @RequestParam String password,
                                       Model model) {
        Admin admin = as.findByUsername(username);
        if (admin != null && admin.getPassword().equals(password)) {
            return "Admin_Dashboard";
        }
        model.addAttribute("errorMessage", "Invalid Username and Password");
        return "AdminLogin";
    }

}

