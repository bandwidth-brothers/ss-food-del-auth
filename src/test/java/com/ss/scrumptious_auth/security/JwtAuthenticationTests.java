package com.ss.scrumptious_auth.security;

import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.ss.scrumptious_auth.dao.UserRepository;
import com.ss.scrumptious_auth.entity.User;
import com.ss.scrumptious_auth.entity.UserRole;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JwtAuthenticationTests {
    @Mock
    static SecurityConstants securityConstants;

    static Date mockJwtExpireDate = new Date(System.currentTimeMillis() + 1_000L);

    @Mock
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Mock
    HttpServletRequest httpServletRequest;

    @Mock
    HttpServletResponse httpServletResponse;

    @Autowired
    BCryptPasswordEncoder encoder;

    @Test
    public void authTest() throws IOException, ServletException {
        when(securityConstants.getSECRET()).thenReturn("MercuryExploration");
        when(securityConstants.getTOKEN_PREFIX()).thenReturn("Bearer ");
        when(securityConstants.getHEADER_STRING()).thenReturn("Authorization");
        when(securityConstants.getExpirationDate()).thenReturn(mockJwtExpireDate);

        User user = User.builder().email("test@test.com").password(encoder.encode("123"))
                .userRole(UserRole.ADMIN).build();
        // userRepository.save(user);

        String token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(mockJwtExpireDate)
                .sign(Algorithm.HMAC512(securityConstants.getSECRET().getBytes()));

        Authentication mockAuthResult = Mockito.mock(Authentication.class);
        when(mockAuthResult.getPrincipal()).thenReturn(user);
        JwtAuthenticationFilter authenticationFilter = new JwtAuthenticationFilter(authenticationManager, securityConstants);

        authenticationFilter.successfulAuthentication(httpServletRequest, 
                httpServletResponse,
                Mockito.mock(FilterChain.class), 
                mockAuthResult);

        Mockito.verify(httpServletResponse).addHeader("Authorization", "Bearer " + token);

    }

}
