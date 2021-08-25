package com.ss.scrumptious_auth.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ss.scrumptious_auth.dao.UserRepository;
import com.ss.scrumptious_auth.entity.User;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class UserDetailServiceImp implements UserDetailsService {

    final private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s){
        User user = userRepository.findByUsername(s).orElseThrow(() -> new UsernameNotFoundException("user account not found."));

        return user;
    }
}
