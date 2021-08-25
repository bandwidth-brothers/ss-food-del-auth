package com.smoothstack.scrumptious_auth.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.smoothstack.scrumptious_auth.entity.User;
import com.smoothstack.scrumptious_auth.repository.UserRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class UserDetailServiceImp implements UserDetailsService {

    final private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s){
        User user = userRepository.findByUsername(s).orElseThrow(() -> new UsernameNotFoundException("User account not found."));

        return user;
    }
}
