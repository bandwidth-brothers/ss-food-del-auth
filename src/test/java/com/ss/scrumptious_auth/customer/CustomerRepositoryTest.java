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
	void itShouldFindByEmail() {
		String email = "customer@gmail.com";
		User user = User.builder()
				.password("pass")
				.email(email)
				.build();
		userRepo.save(user);
		
		User userFound = userRepo.findByEmail(email).get();
		
		assertEquals(user, userFound);
	}
}
