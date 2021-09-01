package com.ss.scrumptious_auth.dto;

import javax.validation.constraints.Email;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class CreateAdminDto {
	
	@Email(message="Email is not valid")
	private final String email;
	private final String password;
}