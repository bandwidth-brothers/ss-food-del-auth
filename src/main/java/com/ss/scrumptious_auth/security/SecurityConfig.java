package com.ss.scrumptious_auth.security;

import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.RequiredArgsConstructor;

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
                .antMatchers(HttpMethod.POST, "/auth/owner/register/**")
                .permitAll()
                .antMatchers(HttpMethod.POST, "/auth/admin/register/**")
                .permitAll()
                .antMatchers(HttpMethod.POST, "/auth/customer/register/**")
                .permitAll()
                .antMatchers(HttpMethod.POST, "/auth/driver/register/**")
                .permitAll()
                .antMatchers("/test/**").permitAll()
                .antMatchers(HttpMethod.POST,"/login").permitAll()
                .antMatchers("/h2-console/*").permitAll()
		.antMatchers("/actuator/health").permitAll()
//                .antMatchers("/api/test/*").permitAll()
//                .antMatchers("/api/management/*").hasRole("MANAGER")
//                .antMatchers("/api/admin/*").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .logout(logout -> logout
                        .permitAll()
                        .logoutSuccessHandler((request, response, authentication) -> {
                            response.setStatus(HttpServletResponse.SC_OK);
                        }
                    ));
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	CorsConfiguration config = new CorsConfiguration();
	config.addAllowedMethod(HttpMethod.PUT);
   	config.addAllowedMethod(HttpMethod.DELETE);
	config.addAllowedOrigin('*');
        source.registerCorsConfiguration("/**", config);
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
