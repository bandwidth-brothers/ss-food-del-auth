package com.ss.auth;

import com.ss.auth.dao.UserRepository;
import com.ss.auth.entity.User;
import com.ss.auth.entity.UserRole;
import com.ss.auth.secutiry.SecurityConfig;
import com.ss.auth.secutiry.SecurityConstants;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthApplicationTests {


    @Autowired
    UserRepository userRepository;

    @Autowired
    SecurityConstants securityConstants;

    @Test
    public void contextLoads() {
    }

    @Test
    public void addUser(){
        User user = new User();
        user.setEmail("z@gmail.com");
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode("456"));
        user.setUsername("Peter");
        user.setUserRole(UserRole.ADMIN);
        User x = userRepository.save(user);
        System.out.println(x);
    }

    @Test
    public void listUser(){

        List<User> l = userRepository.findAll();
        System.out.println(l);
    }

    @Test
    public void findUserByUsername(){
        User u = userRepository.findByUsername("Peter").get();
        System.out.println(u);
    }

    @Test
    public void propertyTest(){
        System.out.println(securityConstants.toString());
    }

}
