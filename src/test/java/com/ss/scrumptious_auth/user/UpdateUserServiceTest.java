package com.ss.scrumptious_auth.user;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class UpdateUserServiceTest {

//	@Autowired
//	UserRepository userRepository;
//	
//	@Autowired
//	CustomerRepository customerRepository;
//	
//	@Autowired
//	RestaurantOwnerRepository restaurantOwnerRepository;
//	
//	@Autowired
//	AdminRepository adminRepository;
//	
//    @Autowired
//    BCryptPasswordEncoder encoder;
//	
//	private UserAccountService userAccountService;
//	
//	@Before
//	public void setUp() {
//		userAccountService = new UserAccountService(userRepository, customerRepository, restaurantOwnerRepository, adminRepository, encoder);
//	}
//
//	@Test
//	public void updateUserTest() {
//
//		String oldEmail = "test@test.com";
//		
//		User user = User.builder()
//			.userId(UUID.randomUUID())
//			.email("test@test.com")
//			.password(encoder.encode("test"))
//			.userRole(UserRole.ADMIN)
//			.build();
//		
//		user = userRepository.save(user);
//		
//		user.setEmail("test2@test.com");
//
//		User newUser = userAccountService.updateUser(user);
//
//		String newEmail = user.getEmail();
//
//		assertNotEquals(oldEmail, newEmail);
//				
//		assertEquals(user.getEmail(), newUser.getEmail());
//		assertEquals(user.getUserId(), newUser.getUserId());
//		assertEquals(user.getPassword(), newUser.getPassword());
//
//		// everything is equal accept for this (which makes sense)
//		assertNotEquals(user.getLastModifiedDateTime(), newUser.getLastModifiedDateTime());
//
//	}
	
}
