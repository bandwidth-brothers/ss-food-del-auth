package com.ss.scrumptious_auth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import com.ss.scrumptious_auth.dao.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
	private PasswordEncoder passwordEncoder;

    private final UserDetailServiceImp userDetailServiceImp;
    private final SecurityConstants securityConstants;


    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/swagger-ui/**", "/v3/api-docs/**");
    }

    @Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().cors()
                .and()
                .addFilter(new JwtAuthenticationFilter(authenticationManager(), securityConstants))
                .addFilter(new JwtAuthenticationVerificationFilter(authenticationManager(), securityConstants))
                .authorizeRequests()
                .antMatchers("/accounts/register/**").permitAll()
                .antMatchers("/test/**").permitAll()
                .antMatchers(HttpMethod.POST,"/login").permitAll()
                .antMatchers("/h2-console/*").permitAll()
//                .antMatchers("/api/test/*").permitAll()
//                .antMatchers("/api/management/*").hasRole("MANAGER")
//                .antMatchers("/api/admin/*").hasRole("ADMIN")
                .anyRequest().authenticated();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }



	/*
	 * @Bean PasswordEncoder passwordEncoder(){ return new BCryptPasswordEncoder();
	 * }
	 */

    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setHideUserNotFoundExceptions(false);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder.bCryptPasswordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailServiceImp);
        return daoAuthenticationProvider;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
