package com.ss.scrumptious_auth.customer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.ss.scrumptious_auth.dao.CustomerRepository;
import com.ss.scrumptious_auth.dao.UserRepository;
import com.ss.scrumptious_auth.entity.User;

@DataJpaTest
public class CustomerRepositoryTest {

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private CustomerRepository customerRepo;
	
	@AfterEach
	void tearDown() {
		userRepo.deleteAll();
	}
	
	
	@Test
	void itShouldFindByUsername() {
		String username = "customer";
		User user = User.builder()
				.username(username)
				.password("pass")
				.email("customer@gmail.com")
				.build();
		userRepo.save(user);
		
		User userFound = userRepo.findByUsername(username).get();
		
		assertEquals(user, userFound);
	}
	
	@Test
	void itShouldFindByEmail() {
		String email = "customer@gmail.com";
		User user = User.builder()
				.username("customer")
				.password("pass")
				.email(email)
				.build();
		userRepo.save(user);
		
		User userFound = userRepo.findByEmail(email).get();
		
		assertEquals(user, userFound);
	}
}
