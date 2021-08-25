package com.smoothstack.scrumptious_auth.customer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.smoothstack.scrumptious_auth.dto.CreateCustomerDto;
import com.smoothstack.scrumptious_auth.entity.Customer;
import com.smoothstack.scrumptious_auth.entity.User;
import com.smoothstack.scrumptious_auth.repository.CustomerRepository;
import com.smoothstack.scrumptious_auth.repository.UserRepository;
import com.smoothstack.scrumptious_auth.security.PasswordEncoder;
import com.smoothstack.scrumptious_auth.service.UserAccountService;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

	@Mock
	UserRepository userRepository;
	
	@Mock
	CustomerRepository customerRepository;
	
	@Autowired
	PasswordEncoder encoder;
	
	private UserAccountService userAccountService;


	
	@BeforeEach
	void setUp() {
		userAccountService = new UserAccountService(userRepository, customerRepository, encoder);
	}



	@Test
	void createNewUserCustomerTest() {
		CreateCustomerDto customerDto = CreateCustomerDto.builder()
				.firstName("Bruno")
				.lastName("Rebaza")
				.username("customer")
				.email("customer@gmail.com")
				.phone("111-222-3333")
				.password("pass")
				.build();
		
		User user = User.builder()
				.username(customerDto.getUsername())
				.email(customerDto.getEmail())
				.password(customerDto.getPassword())
				.build();
		
		Customer customer = Customer.builder()
				.firstName(customerDto.getFirstName())
				.lastName(customerDto.getLastName())
				.email(customerDto.getEmail())
				.phone(customerDto.getPhone())
				.user(user)
				.build();
		
		
		userAccountService.createNewAccountCustomer(customerDto);
		
		ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
		ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);

		verify(userRepository).save(userArgumentCaptor.capture());
		verify(customerRepository).save(customerArgumentCaptor.capture());

		User capturedUser = userArgumentCaptor.getValue();
		Customer capturedCustomer = customerArgumentCaptor.getValue();
		
		assertEquals(user, capturedUser);
		assertEquals(customer, capturedCustomer);

	}
	
}
