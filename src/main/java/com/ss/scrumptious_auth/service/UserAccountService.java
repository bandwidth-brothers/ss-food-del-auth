package com.ss.scrumptious_auth.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.ss.scrumptious_auth.dao.CustomerRepository;
import com.ss.scrumptious_auth.dao.RestaurantOwnerRepository;
import com.ss.scrumptious_auth.dao.UserRepository;
import com.ss.scrumptious_auth.dto.CreateUserDto;
import com.ss.scrumptious_auth.entity.Customer;
import com.ss.scrumptious_auth.entity.RestaurantOwner;
import com.ss.scrumptious_auth.entity.User;
import com.ss.scrumptious_auth.entity.UserRole;

@Service
public class UserAccountService {

	private UserRepository userRepository;
	private CustomerRepository customerRepository;
	private RestaurantOwnerRepository restaurantOwnerRepository;
	private BCryptPasswordEncoder passwordEncoder;
	
	public UserAccountService(UserRepository userRepository, CustomerRepository customerRepository, RestaurantOwnerRepository restaurantOwnerRepository, BCryptPasswordEncoder passwordEncoder ) {
		super();
		this.userRepository = userRepository;
		this.customerRepository = customerRepository;
		this.passwordEncoder = passwordEncoder;
		this.restaurantOwnerRepository = restaurantOwnerRepository;
	}


	@Transactional
	public User createNewAccount(@Valid @RequestBody CreateUserDto createUserDto, UserRole role) {
		
		User user = User.builder()
				.email(createUserDto.getEmail())
				.password(createUserDto.getPassword())
				.userRole(role)
				.build();
		
		boolean userExists = userRepository.findByEmail(user.getEmail()).isPresent();
		
		String encodedPass = passwordEncoder.encode(user.getPassword());

		if (userExists) {
			throw new IllegalStateException("Email is already in use");
		}
		
		user.setPassword(encodedPass);
		
		userRepository.save(user);

		switch(role){
			case CUSTOMER:		
				Customer customer = Customer.builder()
						.firstName(createUserDto.getFirstName())
						.lastName(createUserDto.getLastName())
						//.email(createUserDto.getEmail())
						.phone(createUserDto.getPhone())
						.build();
				
				customerRepository.save(customer);

				break;
			case EMPLOYEE:
				RestaurantOwner restaurantOwner = RestaurantOwner.builder()
						.firstName(createUserDto.getFirstName())
						.lastName(createUserDto.getLastName())
						.email(createUserDto.getEmail())
						.phone(createUserDto.getPhone())
						.user(user)
						.build();

				restaurantOwnerRepository.save(restaurantOwner);
				break;
			case ADMIN:
				break;
			default:
				break;
		}
				
		return user;
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
