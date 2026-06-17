package com.project.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NavController {

//    @GetMapping("/jobs")
//    public String jobs() {
//        return "jobs";
//    }

//    @GetMapping("/companii")
//    public String companii() {
//        return "companii";
//    }

    @GetMapping("/despre")
    public String despre() {
        return "despre";
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }
}
