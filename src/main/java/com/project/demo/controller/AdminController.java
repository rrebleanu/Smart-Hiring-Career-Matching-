package com.project.demo.controller;

import com.project.demo.model.Admin;
import com.project.demo.repository.AdminRepository;
import org.springframework.web.bind.annotation.*;

@RestController // Controller for Admin logic
@RequestMapping(path="/api/admin")
public class AdminController {

    private final AdminRepository adminRepository;

    // Constructor injection
    public AdminController(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @PostMapping(path="/add")
    public String addNewAdmin (@RequestBody Admin newAdmin) {
        // Saves the admin entity (and automatically linking the User ID if provided in JSON)
        adminRepository.save(newAdmin);
        return "Admin Saved Successfully";
    }

    @GetMapping(path="/all")
    public Iterable<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }
}