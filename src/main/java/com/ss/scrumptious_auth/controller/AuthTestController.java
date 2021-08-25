package com.ss.scrumptious_auth.controller;

import java.util.List;

import com.ss.scrumptious_auth.dao.UserRepository;
import com.ss.scrumptious_auth.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class AuthTestController {

    @Autowired
    UserRepository userRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/adminOnly")
    public String adminOnly() {
        return "hello admin";
    }

    @PreAuthorize("hasRole('DRIVER')")
    @GetMapping("/driverOnly")
    public String driverOnly() {
        return "hello driver";
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/customerOnly")
    public String customerOnly() {
        return "hello customer";
    }

    @PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN')")
    @GetMapping("/customerAdmin")
    public String customerAdmin() {
        return "hello customer/Admin";
    }

    // enabled globally in SecurityConfig (and it works!)
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

}
