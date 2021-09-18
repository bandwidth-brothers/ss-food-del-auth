package com.ss.scrumptious_auth.customer;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

//	@Mock
//	UserRepository userRepository;
//	
//	@Mock
//	CustomerRepository customerRepository;
//	
//	@Mock
//	RestaurantOwnerRepository restaurantOwnerRepository;
//	
//	@Mock
//	AdminRepository adminRepository;
//	
//	@Autowired
//	BCryptPasswordEncoder encoder;
//	
//	private UserAccountService userAccountService;
//
//
//	
//	@BeforeEach
//	void setUp() {
//		userAccountService = new UserAccountService(userRepository, customerRepository, restaurantOwnerRepository, adminRepository, encoder);
//	}
//
//
//
//	@Test
//	void createNewUserCustomerTest() {
//		CreateUserDto customerDto = CreateUserDto.builder()
//				.firstName("Bruno")
//				.lastName("Rebaza")
//				.email("customer@gmail.com")
//				.phone("111-222-3333")
//				.password("pass")
//				.build();
//		
//		User user = User.builder()
//				.email(customerDto.getEmail())
//				.password(customerDto.getPassword())
//				.userRole(UserRole.CUSTOMER)
//				.build();
//		
//		Customer customer = Customer.builder()
//				.firstName(customerDto.getFirstName())
//				.lastName(customerDto.getLastName())
//				.email(customerDto.getEmail())
//				.phone(customerDto.getPhone())
//				.user(user)
//				.build();
//		
//		
//		userAccountService.createNewAccount(customerDto, UserRole.CUSTOMER);
//		
//		ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
//		ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);
//
//		verify(userRepository).save(userArgumentCaptor.capture());
//		verify(customerRepository).save(customerArgumentCaptor.capture());
//
//		User capturedUser = userArgumentCaptor.getValue();
//		Customer capturedCustomer = customerArgumentCaptor.getValue();
//		
//		assertEquals(user, capturedUser);
//		assertEquals(customer, capturedCustomer);
//
//	}
//	
//	@Test
//	void createNewUserRestaurantOwnerTest() {
//		CreateUserDto restaurantOwnerDto = CreateUserDto.builder()
//				.firstName("Bruno")
//				.lastName("Rebaza")
//				.email("restaurantOwner@gmail.com")
//				.phone("111-222-3333")
//				.password("pass")
//				.build();
//		
//		User user = User.builder()
//				.email(restaurantOwnerDto.getEmail())
//				.password(restaurantOwnerDto.getPassword())
//				.userRole(UserRole.EMPLOYEE)
//				.build();
//		
//		RestaurantOwner restaurantOwner = RestaurantOwner.builder()
//				.firstName(restaurantOwnerDto.getFirstName())
//				.lastName(restaurantOwnerDto.getLastName())
//				.email(restaurantOwnerDto.getEmail())
//				.phone(restaurantOwnerDto.getPhone())
//				.user(user)
//				.build();
//		
//		
//		userAccountService.createNewAccount(restaurantOwnerDto, UserRole.EMPLOYEE);
//		
//		ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
//		ArgumentCaptor<RestaurantOwner> restaurantOwnerArgumentCaptor = ArgumentCaptor.forClass(RestaurantOwner.class);
//
//		verify(userRepository).save(userArgumentCaptor.capture());
//		verify(restaurantOwnerRepository).save(restaurantOwnerArgumentCaptor.capture());
//
//		User capturedUser = userArgumentCaptor.getValue();
//		RestaurantOwner capturedRestaurantOwner= restaurantOwnerArgumentCaptor.getValue();
//		
//		assertEquals(user, capturedUser);
//		assertEquals(restaurantOwner, capturedRestaurantOwner);
//
//	}
//	
//	@Test
//	@WithMockUser(roles = {"ADMIN"})
//	void createNewUserAdminTest() {
//		CreateUserDto adminDto = CreateUserDto.builder()
//				.email("admin@gmail.com")
//				.password("pass")
//				.build();
//		
//		User user = User.builder()
//				.email(adminDto.getEmail())
//				.password(adminDto.getPassword())
//				.userRole(UserRole.ADMIN)
//				.build();
//		
//		Admin admin = Admin.builder()
//				.firstName(adminDto.getFirstName())
//				.lastName(adminDto.getLastName())
//				.email(adminDto.getEmail())
//				.phone(adminDto.getPhone())
//				.user(user)
//				.build();
//		
//		userAccountService.createNewAccount(adminDto, UserRole.ADMIN);
//		
//		ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
//		ArgumentCaptor<Admin> adminArgumentCaptor = ArgumentCaptor.forClass(Admin.class);
//
//		verify(userRepository).save(userArgumentCaptor.capture());
//		verify(adminRepository).save(adminArgumentCaptor.capture());
//
//		User capturedUser = userArgumentCaptor.getValue();
//		Admin capturedAdmin= adminArgumentCaptor.getValue();
//
//		assertEquals(user, capturedUser);
//		assertEquals(admin, capturedAdmin);
//
//	}
}
