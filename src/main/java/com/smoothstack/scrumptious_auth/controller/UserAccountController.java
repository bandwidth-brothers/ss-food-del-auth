package com.smoothstack.scrumptious_auth.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smoothstack.scrumptious_auth.dto.CreateCustomerDto;
import com.smoothstack.scrumptious_auth.entity.User;
import com.smoothstack.scrumptious_auth.service.UserAccountService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class UserAccountController {

	
	private final UserAccountService userAccountService;
	
	@PostMapping("/register")
	public ResponseEntity<Integer> createNewAccountCustomer(@Valid @RequestBody CreateCustomerDto createCustomerDto) {
		User user = userAccountService.createNewAccountCustomer(createCustomerDto);
		Integer userId = user.getUserId();
		return ResponseEntity.created(URI.create("/accounts/register/" + userId)).body(userId);
	}
}
