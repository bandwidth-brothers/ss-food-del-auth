package com.ss.scrumptious_auth.controller;

import java.net.URI;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ss.scrumptious_auth.dto.CreateCustomerDto;
import com.ss.scrumptious_auth.entity.User;
import com.ss.scrumptious_auth.service.UserAccountService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class UserAccountController {

	
	private final UserAccountService userAccountService;
	
	@PostMapping("/register")
	public ResponseEntity<UUID> createNewAccountCustomer(@Valid @RequestBody CreateCustomerDto createCustomerDto) {
		User user = userAccountService.createNewAccountCustomer(createCustomerDto);
		UUID userId = user.getUserId();
		return ResponseEntity.created(URI.create("/accounts/register/" + userId)).body(userId);
	}
}
