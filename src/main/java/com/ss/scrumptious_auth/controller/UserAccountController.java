package com.ss.scrumptious_auth.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ss.scrumptious_auth.dto.CreateCustomerDto;
import com.ss.scrumptious_auth.dto.EditUserDto;
import com.ss.scrumptious_auth.entity.User;
import com.ss.scrumptious_auth.security.permissions.GetUserByIdPermission;
import com.ss.scrumptious_auth.service.UserAccountService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


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

	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userAccountService.getAllUsers();
		if (users.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(users);
    }

	@GetUserByIdPermission
	@PutMapping("/{userId}")
	public ResponseEntity<User> editUserByUUID(@Valid @RequestBody EditUserDto editUserDto, @PathVariable UUID userId) {
		Optional<User> user = userAccountService.findUserByUUID(userId);
		if (user.isPresent()) {
			user.get().setEmail(editUserDto.getEmail());
			user.get().setPassword(editUserDto.getPassword());
			return ResponseEntity.ok(userAccountService.updateUser(user.get()));
		}
		return ResponseEntity.notFound().build();
	}
}
