package com.ss.scrumptious_auth.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ss.scrumptious_auth.dao.UserRepository;
import com.ss.scrumptious_auth.dto.AuthDto;
import com.ss.scrumptious_auth.entity.User;
import com.ss.scrumptious_auth.entity.UserRole;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthAccountServiceImpl implements AuthAccountService {

	private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public UUID createNewAccount(AuthDto authDto, UserRole role) {

        boolean userExists = userRepository.findByEmail(authDto.getEmail()).isPresent();
        if (userExists) {
            throw new IllegalStateException("Email is already in use");
        }

        String encodedPass = passwordEncoder.encode(authDto.getPassword());
        User user = User.builder()
                .email(authDto.getEmail())
                .password(encodedPass)
                .userRole(role)
                .build();

        User u = userRepository.save(user);
        return u.getUserId();
    }

    
    public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public Optional<User> findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public Optional<User> findUserByUUID(UUID uuid) {
		return userRepository.findById(uuid);
	}

	public User updateUser(User user) {
		String encodedPass = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPass);
		return userRepository.save(user);
	}
}
