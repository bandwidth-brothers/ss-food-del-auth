package com.ss.scrumptious_auth.controller;


import java.util.UUID;

import javax.validation.Valid;

import com.ss.scrumptious.common_entities.entity.UserRole;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ss.scrumptious_auth.dto.AuthDto;
import com.ss.scrumptious_auth.service.AuthAccountServiceImpl;
import com.ss.scrumptious_auth.service.FileServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthAccountController {
    private final AuthAccountServiceImpl authAccountService;
    private final FileServiceImpl fileService;

    @PostMapping("/owner/register")
    public ResponseEntity<UUID> createNewAccountRestaurantOwner(@Valid @RequestBody AuthDto authDto) {
        UUID uid = authAccountService.createNewAccount(authDto, UserRole.OWNER);
        return ResponseEntity.ok(uid);
    }

    @PostMapping("/admin/register")
    public ResponseEntity<UUID> createNewAccountAdmin(@Valid @RequestBody AuthDto authDto) {
        UUID uid = authAccountService.createNewAccount(authDto, UserRole.ADMIN);
        return ResponseEntity.ok(uid);
    }

    @PostMapping("/customer/register")
    public ResponseEntity<UUID> createNewAccountCustomer(@Valid @RequestBody AuthDto authDto) {
        UUID uid = authAccountService.createNewAccount(authDto, UserRole.CUSTOMER);
        return ResponseEntity.ok(uid);
    }

    @PostMapping("/driver/register")
    public ResponseEntity<UUID> createNewAccountDriver(@Valid @RequestBody AuthDto authDto) {
        UUID uid = authAccountService.createNewAccount(authDto, UserRole.DRIVER);
        return ResponseEntity.ok(uid);
    }

    @PostMapping("/{adminId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Boolean> getPasswordVerification(@PathVariable UUID adminId, @RequestBody String password){
		Boolean verification = authAccountService.getPasswordVerification(adminId, password);

		if(verification) {
			return ResponseEntity.ok(verification);
		} else {
			return ResponseEntity.status(401).body(false);
		}
	}

    @GetMapping(value="/s3-secure-url",produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> getS3BucketSecureURL() {
    	return ResponseEntity.ok(fileService.save(""));
    }
}
