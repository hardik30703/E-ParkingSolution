package com.example.Project.controller;

import com.example.Project.Service.DriverService;
import com.example.Project.Service.ParkingLotService;
import com.example.Project.domain.*;
import com.stripe.Stripe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Controller
public class DriverController {

    @Autowired
    ParkingLotService pls;

    @Autowired
    DriverService ds;

    @GetMapping("/DriverDashboard")
    public String driverdashboard() {
        return "Driver_Dashboard";
    }

    @GetMapping("/SearchParking")
    public String showParkingLots(Model model) {
        List<ParkingLot> allLots = pls.getAllParkingLots();
        List<ParkingLot> approvedLots = new ArrayList<>();

        for (ParkingLot lots : allLots) {
            if (lots.isApproved()) {
                approvedLots.add(lots);
            }
        }
        model.addAttribute("approvedLots", approvedLots);
        return "SearchParking";
    }

    @GetMapping("/getParkingLots")
    @ResponseBody
    public List<ParkingLot> getParkingLots() {
        List<ParkingLot> parkingLots = pls.getAllParkingLots();
        List<ParkingLot> approvedParkingLots = new ArrayList<>();

        for (ParkingLot lot : parkingLots) {
            if (lot.isApproved()) {
                approvedParkingLots.add(lot);
            }
        }
        return approvedParkingLots;
    }

    @PostMapping("/BookingDetails")
    public String submitBookingForm(@RequestParam String name, @RequestParam String postcode,
                                    @RequestParam String carregnumber, @RequestParam String slottime,
                                    @RequestParam double amount, @RequestParam int hours, HttpSession session) {

        LocalDate currentDate = LocalDate.now();

        ParkingSlot startSlot = pls.findBySlotId(Integer.parseInt(slottime));

        // Get all slots for the specific ParkingLot
        List<ParkingSlot> allSlots = startSlot.getParkingLot().getParkingSlots();

        // Sort the slots by start time
        allSlots.sort(Comparator.comparing(ParkingSlot::getStartTime));

        // Find the index of the starting slot
        int startIndex = allSlots.indexOf(startSlot);

        // Book the required number of consecutive slots
        for (int i = startIndex; i < startIndex + hours; i++) {
            ParkingSlot slot = allSlots.get(i);
            slot.setBooked(true);
            pls.save(slot);  // Save the updated parking slot
        }

        double newamount = amount * hours;

        // Create a new booking for the entire duration
        String formattedSlotTime = startSlot.getStartTime().toString() + " - " + allSlots.get(startIndex + hours - 1).getEndTime().toString();
        Booking_Form_Info booking_form_info = new Booking_Form_Info(name, postcode, carregnumber, currentDate.toString(), formattedSlotTime, newamount, hours);
        booking_form_info.setSlotID(startSlot.getSlotID());
        booking_form_info.setParkingLot(startSlot.getParkingLot());
        ds.savebookform(booking_form_info);

        int driverId = (int) session.getAttribute("driverId"); // Get driver ID from session
        Driver driver = ds.findById(driverId); // Find driver by ID
        booking_form_info.setDriver(driver);
        ds.savebookform(booking_form_info);

        session.setAttribute("booking_form_info", booking_form_info);

        String driverEmail = driver.getEmail();

        // Schedule the email reminders here
        // Create a ScheduledExecutorService instance
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

        LocalTime now = LocalTime.now();

        LocalTime slotStartTime = startSlot.getStartTime();
        LocalTime slotEndTime = allSlots.get(startIndex + hours - 1).getEndTime();

        // Calculate the delay until the slot starts
        long delayUntilStart = ChronoUnit.MINUTES.between(now, slotStartTime);
        long delayUntilEnd = ChronoUnit.MINUTES.between(now, slotEndTime);

        delayUntilStart = TimeUnit.MINUTES.toMillis(delayUntilStart);
        delayUntilEnd = TimeUnit.MINUTES.toMillis(delayUntilEnd);

        // Schedule a task for 15 minutes before the slot starts
        executorService.schedule(() -> {
            ds.sendEmail(driverEmail, "Reminder: Your parking slot will start soon", "Your parking slot will start in 15 minutes.");
            }, delayUntilStart - TimeUnit.MINUTES.toMillis(15), TimeUnit.MILLISECONDS);

        // Schedule a task for the slot end time
        executorService.schedule(() -> {
            ds.sendEmail(driverEmail, "Reminder: Your parking slot will end soon", "Your parking slot will end in 15 minutes.");
            }, delayUntilEnd - TimeUnit.MINUTES.toMillis(15), TimeUnit.MILLISECONDS);

        executorService.shutdown();

        return "Parking_Payment";
    }


    @PostMapping("/Pay")
    public String submitPaymentForm(HttpSession session) {

        Booking_Form_Info booking_form_info = (Booking_Form_Info) session.getAttribute("booking_form_info");

        // Stripe API Key
        Stripe.apiKey = "pk_test_51P6ALQRwaEgEQqZQTyjk93mTPs3rPLjTxpEJogzHRteT1qeTAINEU8netfLrSae7DDQbIeSScqgLeXCGyqcqpQy8005H1uKGcA";

        Payment_Form payment_form = new Payment_Form();

        int driverId = (int) session.getAttribute("driverId"); // Get driver ID from session
        Driver driver = ds.findById(driverId); // Find driver by ID

        payment_form.setBooking(booking_form_info);
        payment_form.setDriverID(driverId);
        ds.savepayform(payment_form);

        String drivername = driver.getFirstname();
        String driverEmail = driver.getEmail();

        String postcodeWithoutSpaces = booking_form_info.getPostcode().replace(" ", "");
        String googleMapsUrl = "https://www.google.com/maps/search/?api=1&query=" + postcodeWithoutSpaces;

        String content = "Hello " + drivername + "," + "\nHere are the details for your booking: \n\nName: " + booking_form_info.getName() +
                "\nPostcode: " + booking_form_info.getPostcode() + "\nTime: " + booking_form_info.getSlottime() + "\nPrice: £" + booking_form_info.getAmount() + "0" +
                "\nVehicle: " + booking_form_info.getCarregnumber() + "\n\nDirections: " + googleMapsUrl +
                "\n\nThank you for using our service. ";

        ds.sendEmail(driverEmail, "Parking Slot Booking Confirmation", content);


        return "BookingConfirmation";
    }

    @RequestMapping("/HoursBooking/{id}")
    public String hours(@PathVariable int id, Model model) {
        ParkingLot parkingLot = pls.findById(id);
        model.addAttribute("lotID", parkingLot.getLotID());
        return "HoursBooking";
    }

    @RequestMapping(value = "/Parking_Booking_Form/{id}/{hours}")
    public String book(@PathVariable int id, @PathVariable int hours, Model model, HttpSession session){
        int driverId = (int) session.getAttribute("driverId"); // Get driver ID from session
        Driver driver = ds.findById(driverId); // Find driver by ID

        List<Vehicle> vehicle = driver.getVehicles(); // Get vehicles of the driver
        model.addAttribute("vehicle", vehicle);

        ParkingLot parkingLot = pls.findById(id);

        model.addAttribute("name", parkingLot.getName());
        model.addAttribute("postcode", parkingLot.getPostcode());
        model.addAttribute("amount", parkingLot.getPrice());

        ParkingLot specificLot = pls.findByName(parkingLot.getName());

        // Get all parking slots for the specific ParkingLot
        List<ParkingSlot> allSlots = specificLot.getParkingSlots();

        LocalTime currentTime = LocalTime.now();

        // Filter out the slots that are already booked or before the current time
        List<ParkingSlot> slots = new ArrayList<>();
        for (int i = 0; i < allSlots.size() - hours; i++) {
            List<ParkingSlot> subList = allSlots.subList(i, i + hours);
            if (subList.stream().allMatch(slot -> !slot.getBooked() && slot.getStartTime().isAfter(currentTime))) {
                slots.add(subList.get(0));  // add the first slot of the consecutive slots to the list
            }
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        Map<Integer, String> slotTimes = new HashMap<>();
        for (ParkingSlot slot : slots) {
            LocalTime endTime = slot.getStartTime().plusHours(hours);
            String timeSlot = slot.getStartTime().format(formatter) + " - " + endTime.format(formatter);
            slotTimes.put(slot.getSlotID(), timeSlot);
        }

        model.addAttribute("slotTimes", slotTimes);
        model.addAttribute("slots", slots);

        return "Parking_Booking_Form";
    }

    @GetMapping("/ParkingHistory")
    public String parkinghistory(Model model, HttpSession session) {
        int driverId = (int) session.getAttribute("driverId"); // Get driver ID from session
        Driver driver = ds.findById(driverId); // Find driver by ID

        //Get booking details for the driver
        List<Booking_Form_Info> booking_form_infos = driver.getBookings();

        // Sort the bookings in descending order by date
        Collections.sort(booking_form_infos, new Comparator<Booking_Form_Info>() {
            public int compare(Booking_Form_Info b1, Booking_Form_Info b2) {
                return b2.getDate().compareTo(b1.getDate());
            }
        });

        LocalDate currentDate = LocalDate.now(); // Get the current date
        String currentDte = currentDate.toString();

        model.addAttribute("currentDte", currentDte); // Add current date to the model
        model.addAttribute("booking_form_infos", booking_form_infos);
        return "ParkingHistory";
    }


    @RequestMapping(value = "/Cancel/{id}")
    public ModelAndView cancel(@PathVariable int id, RedirectAttributes redirectAttributes){
        Booking_Form_Info booking_form_info = ds.findByBookingId(id);
        // Get the slotID and hours from the booking form info
        int startSlotId = booking_form_info.getSlotID();
        int hours = booking_form_info.getHours();

        // Get all slots for the specific ParkingLot
        List<ParkingSlot> allSlots = pls.findAllByParkingLotId(booking_form_info.getParkingLot().getLotID());

        // Sort the slots by start time
        allSlots.sort(Comparator.comparing(ParkingSlot::getStartTime));

        // Find the index of the starting slot
        int startIndex = allSlots.indexOf(pls.findBySlotId(startSlotId));

        LocalDateTime cancelTime = LocalDateTime.now(); // get the current time
        LocalTime startTime = allSlots.get(startIndex).getStartTime(); // get the start time of the first slot

        // Convert LocalTime to LocalDateTime
        LocalDateTime startDateTime = LocalDateTime.of(cancelTime.toLocalDate(), startTime);

        Duration duration = Duration.between(cancelTime, startDateTime);
        long diff = duration.toMinutes();

        if (diff <= 15 && diff >= 0) {
            // apply penalty
            redirectAttributes.addFlashAttribute("penalty", "You are cancelling the ride less than or equal to 15 minutes" +
                    " before the start time. A penalty will be applied of £5.");
        }

        // Cancel the required number of consecutive slots
        for (int i = startIndex; i < startIndex + hours; i++) {
            ParkingSlot slot = allSlots.get(i);
            slot.setBooked(false);
            pls.save(slot);  // Save the updated parking slot
        }

        ds.deleteByBookingId(id);

        return new ModelAndView("redirect:/ParkingHistory");
    }


    @GetMapping("/ManageVehicle")
    public String showVehicle(Model model, HttpSession session) {
        int driverId = (int) session.getAttribute("driverId"); // Get driver ID from session
        Driver driver = ds.findById(driverId); // Find driver by ID

        List<Vehicle> allVehicles = driver.getVehicles(); // Get vehicles of the driver
        List<Vehicle> approvedVehicles = new ArrayList<>();
        List<Vehicle> pendingVehicles = new ArrayList<>();

        // If vehicle is approved - add it to approvedVehicles list
        // otherwise add it to pending Vehicles list
        for (Vehicle vehicle : allVehicles) {
            if (vehicle.isApproved()) {
                approvedVehicles.add(vehicle);
            } else {
                pendingVehicles.add(vehicle);
            }
        }
        List<Notification> notifications = ds.getUnreadDriverNotifications(driver);

        model.addAttribute("notifications", notifications);
        model.addAttribute("approvedVehicles", approvedVehicles);
        model.addAttribute("pendingVehicles", pendingVehicles);
        return "ManageVehicle";
    }

    @PostMapping("/notification/{id}/read")
    public String markNotificationAsRead(@PathVariable int id) {
        Notification notification = ds.findByNotificationId(id);
        if (notification != null) {
            notification.setRead(true);
            ds.saveNotification(notification);
        }
        return "redirect:/ManageVehicle";
    }


    @GetMapping("/AddVehicle")
    public String showAddVehicleForm() {
        return "AddVehicle";
    }

    @PostMapping("/AddVehicle")
    public String submitVehicleForm(@RequestParam String regNumber, @RequestParam String make,
                                    @RequestParam String colour, HttpSession session) {

        String upperCaseRegNumber = regNumber.toUpperCase(); // Convert regNumber to uppercase

        Vehicle vehicle = new Vehicle(upperCaseRegNumber, make, colour);
        int driverId = (int) session.getAttribute("driverId"); // Get driver ID from session
        Driver driver = ds.findById(driverId); // Find driver by ID
        vehicle.setDriver(driver); // Set the driver of the vehicle
        vehicle.setApproved(false); // Vehicle needs to be approved
        ds.saveVehicle(vehicle);

        String username = driver.getUsername();

        Admin admin = ds.findAdminById(1);
        String adminEmail = admin.getEmail();

        String content = username + " needs a decision on their new Vehicle";

        // Send the confirmation email
        ds.sendEmail(adminEmail, "Vehicle Decision Needed", content);

        return "redirect:/ManageVehicle";
    }


    @RequestMapping(value = "/deleteVehicle/{id}")
    public ModelAndView deleteVehicle(@PathVariable int id){
        ds.deleteByVehicleId(id);
        return new ModelAndView("redirect:/ManageVehicle");
    }


}
