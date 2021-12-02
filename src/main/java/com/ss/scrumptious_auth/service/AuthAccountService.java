package com.ss.scrumptious_auth.service;

import java.util.UUID;

import com.ss.scrumptious.common_entities.entity.UserRole;
import com.ss.scrumptious_auth.dto.AuthDto;


public interface AuthAccountService {

	UUID createNewAccount(AuthDto authDto, UserRole employee);
}
