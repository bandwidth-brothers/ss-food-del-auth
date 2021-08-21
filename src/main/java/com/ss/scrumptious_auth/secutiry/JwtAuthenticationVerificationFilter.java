package com.ss.scrumptious_auth.secutiry;

import com.auth0.jwt.JWT;
import com.ss.scrumptious_auth.dao.UserRepository;
import com.ss.scrumptious_auth.entity.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

public class JwtAuthenticationVerificationFilter extends BasicAuthenticationFilter {
    private UserRepository userRepository;
    private final SecurityConstants securityConstants;

    public JwtAuthenticationVerificationFilter(AuthenticationManager authenticationManager,
                                               UserRepository userRepository,
                                               SecurityConstants securityConstants) {
        super(authenticationManager);
        this.userRepository = userRepository;
        this.securityConstants = securityConstants;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(securityConstants.getHEADER_STRING());

        // If header does not contain BEARER or is null delegate to Spring impl and exit
        if (header == null || !header.startsWith(securityConstants.getTOKEN_PREFIX())) {
            chain.doFilter(request, response);
            return;
        }

        // If header is present, try grab user principal from database and perform authorization
        Authentication authentication = getAuthenticationToken(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Continue filter execution
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthenticationToken(HttpServletRequest request){
        String token = request.getHeader(securityConstants.getHEADER_STRING())
                .replace(securityConstants.getTOKEN_PREFIX(),"");
        if (token == null){
            return null;
        }

        // parse the token and validate it
        String userName = JWT.require(HMAC512(securityConstants.getSECRET().getBytes()))
                .build()
                .verify(token)
                .getSubject();

        if (userName == null){
            return null;
        }

        // Search in the DB if we find the user by token subject (username)
        // If so, then grab user details and create spring auth token using username, pass, authorities/roles
        User user = userRepository.findByUsername(userName).get();
        return new UsernamePasswordAuthenticationToken(userName, null, user.getAuthorities());
    }
}
