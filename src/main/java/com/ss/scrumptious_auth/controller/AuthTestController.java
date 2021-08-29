package com.ss.scrumptious_auth.controller;

import com.ss.scrumptious_auth.dao.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> adminOnly() {
        return ResponseEntity.ok("hello admin");
    }

    @PreAuthorize("hasRole('DRIVER')")
    @GetMapping("/driverOnly")
    public ResponseEntity<String> driverOnly() {
        return ResponseEntity.ok("hello driver");
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/customerOnly")
    public ResponseEntity<String> customerOnly() {
        return ResponseEntity.ok("hello customer");
    }

    @PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN')")
    @GetMapping("/customerAdmin")
    public ResponseEntity<String> customerAdmin() {
        return ResponseEntity.ok("hello customer/Admin");
    }

}
