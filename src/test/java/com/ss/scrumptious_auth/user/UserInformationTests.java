package com.ss.scrumptious_auth.user;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.ss.scrumptious_auth.dao.UserRepository;
import com.ss.scrumptious_auth.entity.User;
import com.ss.scrumptious_auth.entity.UserRole;
import com.ss.scrumptious_auth.security.SecurityConstants;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserInformationTests {

    @Mock
    static SecurityConstants securityConstants;

    static Date mockJwtExpireDate = new Date(System.currentTimeMillis() + 1_000L);

    @Mock
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;


    @Autowired
    BCryptPasswordEncoder encoder;

    @Autowired
    WebApplicationContext wac;

    MockMvc mvc;


    @Before
    public void beforeEach() {
        mvc = MockMvcBuilders.webAppContextSetup(wac).apply(springSecurity()).build();
        when(securityConstants.getSECRET()).thenReturn("MercuryExploration");
        when(securityConstants.getTOKEN_PREFIX()).thenReturn("Bearer ");
        when(securityConstants.getHEADER_STRING()).thenReturn("Authorization");
        when(securityConstants.getExpirationDate()).thenReturn(mockJwtExpireDate);
    }

    @After
    public void afterEach() {
        userRepository.deleteAll();
    }
	
    @Test
    public void currentAccountTest() throws Exception {


        User user = User.builder()
			.email("test@test.com")
			.password(encoder.encode("123"))
            .userRole(UserRole.ADMIN)
			.build();
		
        userRepository.save(user);

        String token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(securityConstants.getExpirationDate())
                .sign(Algorithm.HMAC512(securityConstants.getSECRET().getBytes()));

		mvc.perform(get("/accounts/me").header(securityConstants.getHEADER_STRING(), securityConstants.getTOKEN_PREFIX() + token))
		.andExpect(status().isOk())
        .andExpect(jsonPath("$.email").value(user.getEmail()))
        .andExpect(jsonPath("$.password").value(user.getPassword()))
        .andExpect(jsonPath("$.userId").value(user.getUserId().toString()));

    }

    @Test
    public void allAccountsTest() throws Exception {


        User adminUser = User.builder()
			.email("test@test.com")
			.password(encoder.encode("123"))
            .userRole(UserRole.ADMIN)
			.build();

        User user1 = User.builder()
			.email("ted@test.com")
			.password(encoder.encode("teddy"))
            .userRole(UserRole.DRIVER)
			.build();
    
        User user2 = User.builder()
			.email("mike@test.com")
			.password(encoder.encode("aesdcfj"))
            .userRole(UserRole.CUSTOMER)
			.build();  
		
        userRepository.save(adminUser);
        userRepository.save(user1);
        userRepository.save(user2);

        String token = JWT.create()
                .withSubject(adminUser.getUsername())
                .withExpiresAt(securityConstants.getExpirationDate())
                .sign(Algorithm.HMAC512(securityConstants.getSECRET().getBytes()));

		mvc.perform(get("/accounts").header(securityConstants.getHEADER_STRING(), securityConstants.getTOKEN_PREFIX() + token))
		.andExpect(status().isOk())
        .andExpect(jsonPath("$.[0]email").value(adminUser.getEmail()))
        .andExpect(jsonPath("$.[0]password").value(adminUser.getPassword()))
        .andExpect(jsonPath("$.[0]userId").value(adminUser.getUserId().toString()))
        .andExpect(jsonPath("$.[1]email").value(user1.getEmail()))
        .andExpect(jsonPath("$.[1]password").value(user1.getPassword()))
        .andExpect(jsonPath("$.[1]userId").value(user1.getUserId().toString()))
        .andExpect(jsonPath("$.[2]email").value(user2.getEmail()))
        .andExpect(jsonPath("$.[2]password").value(user2.getPassword()))
        .andExpect(jsonPath("$.[2]userId").value(user2.getUserId().toString()));  

    }

}
