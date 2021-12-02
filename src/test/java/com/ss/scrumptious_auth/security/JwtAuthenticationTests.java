package com.ss.scrumptious_auth.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.ss.scrumptious.common_entities.entity.User;
import com.ss.scrumptious.common_entities.entity.UserRole;
import com.ss.scrumptious_auth.dao.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;

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
        when(securityConstants.getAUTHORITY_CLAIM_KEY()).thenReturn("Authorities");
        when(securityConstants.getUSER_ID_CLAIM_KEY()).thenReturn("UserId");

        User user = User.builder()
        		.id(UUID.randomUUID())
        		.email("test@test.com").password(encoder.encode("123"))
                .userRole(UserRole.ADMIN).build();
        // userRepository.save(user);

        String authorites = user.getAuthorities()
        		.stream()
        		.map(GrantedAuthority::getAuthority)
        		.collect(Collectors.joining(","));

        String token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(mockJwtExpireDate)
                .withClaim(securityConstants.getUSER_ID_CLAIM_KEY(), user.getId().toString())
        		.withClaim(securityConstants.getAUTHORITY_CLAIM_KEY(), authorites)
                .sign(Algorithm.HMAC512(securityConstants.getSECRET().getBytes()));

        Authentication mockAuthResult = Mockito.mock(Authentication.class);
        when(mockAuthResult.getPrincipal()).thenReturn(user);
        JwtAuthenticationFilter authenticationFilter = new JwtAuthenticationFilter(authenticationManager, securityConstants);

        class SpyWriter extends PrintWriter {

            public SpyWriter(OutputStream out) {
                super(out);
            }
        }

        // spy on the response body
        SpyWriter spyWriter = new SpyWriter(System.out);
        when(httpServletResponse.getWriter()).thenReturn(spyWriter);

        authenticationFilter.successfulAuthentication(httpServletRequest,
                httpServletResponse,
                Mockito.mock(FilterChain.class),
                mockAuthResult);

        Mockito.verify(httpServletResponse).addHeader("Authorization", "Bearer " + token);
    }
}
