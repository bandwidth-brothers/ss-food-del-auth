package com.ss.scrumptious_auth.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.UUID;

import com.ss.scrumptious_auth.dao.CustomerRepository;
import com.ss.scrumptious_auth.dao.UserRepository;
import com.ss.scrumptious_auth.entity.User;
import com.ss.scrumptious_auth.entity.UserRole;
import com.ss.scrumptious_auth.service.UserAccountService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class UpdateUserServiceTest {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	CustomerRepository customerRepository;
	
    @Autowired
    BCryptPasswordEncoder encoder;
	
	private UserAccountService userAccountService;
	
	@Before
	public void setUp() {
		userAccountService = new UserAccountService(userRepository, customerRepository, encoder);
	}

	@Test
	public void updateUserTest() {

		String oldEmail = "test@test.com";
		
		User user = User.builder()
			.userId(UUID.randomUUID())
			.email("test@test.com")
			.password(encoder.encode("test"))
			.userRole(UserRole.ADMIN)
			.build();
		
		user = userRepository.save(user);
		
		user.setEmail("test2@test.com");

		User newUser = userAccountService.updateUser(user);

		String newEmail = user.getEmail();

		assertNotEquals(oldEmail, newEmail);
				
		assertEquals(user.getEmail(), newUser.getEmail());
		assertEquals(user.getUserId(), newUser.getUserId());
		assertEquals(user.getPassword(), newUser.getPassword());

		// everything is equal accept for this (which makes sense)
		assertNotEquals(user.getLastModifiedDateTime(), newUser.getLastModifiedDateTime());

	}
	
}
