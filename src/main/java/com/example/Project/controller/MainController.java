package com.example.Project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @RequestMapping("/")
    public String home() {
        return "Homescreen";
    }

    @GetMapping("/")
    public String home1() {
        return "Homescreen";
    }


}
