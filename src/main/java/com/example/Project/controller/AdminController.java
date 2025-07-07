package com.example.Project.controller;

import com.example.Project.Service.DriverService;
import com.example.Project.Service.ParkingLotService;
import com.example.Project.Service.ParkingOwnerService;
import com.example.Project.domain.Driver;
import com.example.Project.domain.ParkingLot;
import com.example.Project.domain.ParkingOwner;
import com.example.Project.domain.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController {

    @Autowired
    ParkingLotService pls;

    @Autowired
    DriverService ds;

    @Autowired
    ParkingOwnerService pos;

    @GetMapping("/AdminDashboard")
    public String admindashboard() {
        return "Admin_Dashboard";
    }

    //ParkingOwner
    @GetMapping("/ViewOwners")
    public String showOwners(@RequestParam(required = false) String username,Model model) {
        List<ParkingOwner> owners;

        if (username != null && !username.isEmpty()) {
            owners = new ArrayList<>();
            ParkingOwner owner = pos.findByUsername(username);
            if (owner != null) {
                owners.add(owner);
            }
        } else {
            owners = pos.getAllOwners();
        }

        model.addAttribute("owners", owners);
        return "ViewOwners";
    }

    @GetMapping("/SearchOwnerUsernames")
    @ResponseBody
    public List<String> searchOwnerUsernames(@RequestParam String username) {
        return pos.findUsernamesByUsername(username);
    }

    //ParkingLots
    @GetMapping("/ViewParkingLots/{ownerId}")
    public String showParkingLots(Model model, @PathVariable int ownerId) {
        List<ParkingLot> allLots = pls.getAllParkingLots();
        List<ParkingLot> approvedLots = new ArrayList<>();
        List<ParkingLot> pendingLots = new ArrayList<>();

        for (ParkingLot lot : allLots) {
            if (lot.getOwner().getOwnerID() == ownerId) {
                if (lot.isApproved()) {
                    approvedLots.add(lot);
                } else {
                    pendingLots.add(lot);
                }
            }
        }

        model.addAttribute("approvedLots", approvedLots);
        model.addAttribute("pendingLots", pendingLots);
        return "ViewParkingLots";
    }


    @RequestMapping(value = "/approve/{id}")
    public ModelAndView approve(@PathVariable int id){
        ParkingLot parkingLot = pls.findById(id);
        if (parkingLot != null) {
            parkingLot.setApproved(true);
            pls.save(parkingLot);

            ParkingOwner owner = parkingLot.getOwner();
            String ownerEmail = owner.getEmail();
            String ownername = owner.getFirstname();
            String name = parkingLot.getName();

            String content = "Hello " + ownername + "," + "\n\nParking Lot - " + name + " has been approved. " +
                    "\n\nThank you for using our service. ";

            // Send the confirmation email
            ds.sendEmail(ownerEmail, "Parking Lot Approved", content);

            pos.createNotification(owner, "Parking lot: " + name + " has been Approved");
        }
        return new ModelAndView("redirect:/ViewOwners");
    }

    @RequestMapping(value = "/reject/{id}")
    public ModelAndView reject(@PathVariable int id){
        ParkingLot parkingLot = pls.findById(id);
        if (parkingLot != null) {
            pls.deleteById(id);

            ParkingOwner owner = parkingLot.getOwner();
            String ownerEmail = owner.getEmail();
            String ownername = owner.getFirstname();
            String name = parkingLot.getName();

            String content = "Hello " + ownername + "," + "\n\nParking Lot - " + name + " has been rejected. " +
                    "\n\nThank you for using our service. ";

            // Send the confirmation email
            ds.sendEmail(ownerEmail, "Parking Lot Rejected", content);

            pos.createNotification(owner, "Parking lot: " + name + " has been Rejected");
        }
        return new ModelAndView("redirect:/ViewOwners");
    }

    @RequestMapping(value = "/deleteLotAdmin/{id}")
    public ModelAndView delete(@PathVariable int id){
        ParkingLot parkingLot = pls.findById(id);
        if (parkingLot != null) {
            pls.deleteById(id);

            ParkingOwner owner = parkingLot.getOwner();
            String ownerEmail = owner.getEmail();
            String ownername = owner.getFirstname();
            String name = parkingLot.getName();

            String content = "Hello " + ownername + "," + "\n\nParking Lot - " + name + " has been removed by the admin. " +
                    "\n\nThank you for using our service. ";

            // Send the confirmation email
            ds.sendEmail(ownerEmail, "Parking Lot Deleted", content);

            pos.createNotification(owner, "Parking lot: " + name + " has been removed");
        }
        return new ModelAndView("redirect:/ViewOwners");
    }

    //Drivers
    @GetMapping("/ViewDrivers")
    public String showDrivers(@RequestParam(required = false) String username, Model model) {
        List<Driver> drivers;
        if (username != null && !username.isEmpty()) {
            drivers = new ArrayList<>();
            Driver driver = ds.findByUsername(username);
            if (driver != null) {
                drivers.add(driver);
            }
        } else {
            drivers = ds.getAllDrivers();
        }
        model.addAttribute("drivers", drivers);
        return "ViewDrivers";
    }

    @GetMapping("/SearchUsernames")
    @ResponseBody
    public List<String> searchUsernames(@RequestParam String username) {
        return ds.findUsernamesByUsername(username);
    }

    //Vehicles
    @GetMapping("/ViewVehicle/{driverId}")
    public String showVehicle(Model model, @PathVariable int driverId) {
        List<Vehicle> allVehicles = ds.getAllVehicle();
        List<Vehicle> approvedVehicles = new ArrayList<>();
        List<Vehicle> pendingVehicles = new ArrayList<>();

       for (Vehicle vehicle : allVehicles) {
            if (vehicle.getDriver().getDriverID() == driverId) {
                if (vehicle.isApproved()) {
                    approvedVehicles.add(vehicle);
                }
                else {
                    pendingVehicles.add(vehicle);
                }
            }
        }

        model.addAttribute("approvedVehicles", approvedVehicles);
        model.addAttribute("pendingVehicles", pendingVehicles);
        return "ViewVehicle";
    }

    @RequestMapping(value = "/approveVehicle/{id}")
    public ModelAndView approveVehicle(@PathVariable int id) {
        Vehicle vehicle = ds.findVehicleById(id);
        if (vehicle != null) {
            vehicle.setApproved(true);
            ds.saveVehicle(vehicle);

            Driver driver = vehicle.getDriver();
            String driverEmail = driver.getEmail();
            String drivername = driver.getFirstname();

            String carregnumber = vehicle.getRegNumber();

            String content = "Hello " + drivername + "," + "\n\nVehicle - " + carregnumber + " has been approved. \n\nYou can now use the vehicle in the website. " +
                    "\n\nThank you for using our service. ";

            // Send the confirmation email
            ds.sendEmail(driverEmail, "Vehicle Approved", content);

            ds.createDriverNotification(driver, "Vehicle: " + carregnumber + " has been Approved");
        }
        return new ModelAndView("redirect:/ViewDrivers");
    }

    @RequestMapping(value = "/rejectVehicle/{id}")
    public ModelAndView rejectVehicle(@PathVariable int id){
        Vehicle vehicle = ds.findVehicleById(id);
        if (vehicle != null) {
            ds.deleteByVehicleId(id);

            Driver driver = vehicle.getDriver();
            String driverEmail = driver.getEmail();
            String drivername = driver.getFirstname();
            String carregnumber = vehicle.getRegNumber();

            String content = "Hello " + drivername + "," + "\n\nVehicle - " + carregnumber + " has been rejected. "
                    + "\n\nThank you for using our service. ";

            // Send the confirmation email
            ds.sendEmail(driverEmail, "Vehicle Rejected", content);

            ds.createDriverNotification(driver, "Vehicle: " + carregnumber + " has been Rejected");
        }
        return new ModelAndView("redirect:/ViewDrivers");
    }

    @RequestMapping(value = "/deleteVehicleAdmin/{id}")
    public ModelAndView deleteVehicle(@PathVariable int id){
        Vehicle vehicle = ds.findVehicleById(id);
        if (vehicle != null) {
            ds.deleteByVehicleId(id);

            Driver driver = vehicle.getDriver();
            String driverEmail = driver.getEmail();
            String drivername = driver.getFirstname();
            String carregnumber = vehicle.getRegNumber();

            String content = "Hello " + drivername + "," + "\n\nVehicle - " + carregnumber + " has been deleted by the admin. " + "\n\nThank you for using our service. ";

            // Send the confirmation email
            ds.sendEmail(driverEmail, "Vehicle Deleted", content);

            ds.createDriverNotification(driver, "Vehicle: " + carregnumber + " has been Deleted");
        }
        return new ModelAndView("redirect:/ViewDrivers");
    }


}
