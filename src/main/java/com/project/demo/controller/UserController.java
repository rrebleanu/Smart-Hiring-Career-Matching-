package com.project.demo.controller;

import com.project.demo.model.User;
import com.project.demo.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

@RestController // Combines @Controller and @ResponseBody for modern APIs
@RequestMapping(path="/api/user") // Base URL for all user-related requests (Matches the others)
public class UserController {

    private final UserRepository userRepository; // Final field for safety

    // Constructor injection is the modern standard (replaces @Autowired)
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping(path="/add") // Endpoint to CREATE a new user
    public String addNewUser (@RequestBody User newUser) {
        // @RequestBody automatically converts the incoming JSON data into a User object
        // This is much safer and simpler than using multiple @RequestParam annotations

        userRepository.save(newUser); // Save the entire object to the database
        return "User Saved Successfully";
    }

    @GetMapping(path="/all") // Endpoint to READ all users
    public Iterable<User> getAllUsers() {
        // Fetch all users from the database and return them automatically as JSON
        return userRepository.findAll();
    }
}