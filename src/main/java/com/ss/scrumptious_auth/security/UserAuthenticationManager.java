package com.ss.scrumptious_auth.security;

import java.util.UUID;

import com.ss.scrumptious_auth.entity.User;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;


@Component
public class UserAuthenticationManager {

  public boolean customerEmailMatches(Authentication authentication, String email) {
    try {
        authentication.getPrincipal();
      User principal = (User) authentication.getPrincipal();
      return principal.getEmail().equals(email);
    } catch (ClassCastException ex) {
      return false;
    }
  } 

  public boolean customerIdMatches(Authentication authentication, UUID id) {
    try {
      User principal = (User) authentication.getPrincipal();
      return principal.getUserId().equals(id);
    } catch (ClassCastException ex) {
      return false;
    }
  }

}