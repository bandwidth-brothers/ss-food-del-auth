package com.ss.scrumptious_auth.security;

import com.ss.scrumptious.common_entities.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ss.scrumptious_auth.dao.UserRepository;


import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class UserDetailServiceImp implements UserDetailsService {

    final private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s){

        User user = userRepository.findByEmail(s).orElseThrow(() -> new UsernameNotFoundException("User account not found."));

        return user;
    }
}
