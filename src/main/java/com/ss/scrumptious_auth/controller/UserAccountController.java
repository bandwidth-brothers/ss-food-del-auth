package com.ss.scrumptious_auth.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ss.scrumptious_auth.dto.CreateUserDto;
import com.ss.scrumptious_auth.dto.EditUserDto;
import com.ss.scrumptious_auth.entity.User;
import com.ss.scrumptious_auth.entity.UserRole;
import com.ss.scrumptious_auth.security.permissions.GetUserByIdPermission;
import com.ss.scrumptious_auth.service.AuthAccountServiceImpl;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class UserAccountController {

	
	private final AuthAccountServiceImpl authAccountService;
	

	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = authAccountService.getAllUsers();
		if (users.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(users);
    }

	@GetUserByIdPermission
	@GetMapping("/{userId}")
	public ResponseEntity<User> currentUserName(@PathVariable UUID userId) {
	   	User user = authAccountService.findUserById(userId);
		return ResponseEntity.ok(user);
	}

	@GetUserByIdPermission
	@PutMapping("/{userId}")
	public ResponseEntity<User> editUserByUUID(@Valid @RequestBody EditUserDto editUserDto, @PathVariable UUID userId) {
		User user = authAccountService.findUserById(userId);
		
			user.setEmail(editUserDto.getEmail());
			user.setPassword(editUserDto.getPassword());
		return ResponseEntity.ok(authAccountService.updateUser(user));
	}
}
