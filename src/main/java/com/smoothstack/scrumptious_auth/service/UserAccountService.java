package com.smoothstack.scrumptious_auth.service;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.smoothstack.scrumptious_auth.dto.CreateCustomerDto;
import com.smoothstack.scrumptious_auth.entity.Customer;
import com.smoothstack.scrumptious_auth.entity.User;
import com.smoothstack.scrumptious_auth.repository.CustomerRepository;
import com.smoothstack.scrumptious_auth.repository.UserRepository;
import com.smoothstack.scrumptious_auth.security.PasswordEncoder;

@Service
public class UserAccountService {

	private UserRepository userRepository;
	private CustomerRepository customerRepository;
	private PasswordEncoder passwordEncoder;
	
	public UserAccountService(UserRepository userRepository, CustomerRepository customerRepository, PasswordEncoder passwordEncoder ) {
		super();
		this.userRepository = userRepository;
		this.customerRepository = customerRepository;
		this.passwordEncoder = passwordEncoder;
	}


	@Transactional
	public User createNewAccountCustomer(@Valid @RequestBody CreateCustomerDto createCustomerDto) {
		User user = User.builder()
				.username(createCustomerDto.getUsername())
				.email(createCustomerDto.getEmail())
				.password(createCustomerDto.getPassword())
				.build();

		boolean userExists = userRepository.findByEmail(user.getEmail()).isPresent();

		if (userExists) {
			throw new IllegalStateException("Email is already in use");
		}
		
		String encodedPass = passwordEncoder.bCryptPasswordEncoder().encode(user.getPassword());
		
		user.setPassword(encodedPass);
		
		userRepository.save(user);
		
		Customer customer = Customer.builder()
				.firstName(createCustomerDto.getFirstName())
				.lastName(createCustomerDto.getLastName())
				.email(createCustomerDto.getEmail())
				.phone(createCustomerDto.getPhone())
				.user(user)
				.build();

		customerRepository.save(customer);
		
		// TODO Send JWT Token
		
		return user;
	}
}
