package com.ss.scrumptious_auth.service;

import java.util.UUID;

import com.ss.scrumptious_auth.dto.AuthDto;
import com.ss.scrumptious_auth.entity.UserRole;

public interface AuthAccountService {

	UUID createNewAccount(AuthDto authDto, UserRole employee);
}
