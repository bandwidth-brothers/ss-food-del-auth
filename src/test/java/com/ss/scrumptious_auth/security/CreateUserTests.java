package com.ss.scrumptious_auth.security;

import com.ss.scrumptious_auth.dao.UserRepository;
import com.ss.scrumptious_auth.entity.User;
import com.ss.scrumptious_auth.entity.UserRole;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CreateUserTests {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder encoder;

    @Test
    public void test(){

        User user = User.builder().email("peter@test.com").password(encoder.encode("123")).enabled(true)
                .username("Peter").userRole(UserRole.ADMIN).build();
        System.out.println(user);
        userRepository.save(user);
    }

    @Test
    public void testFind(){
        Optional<User> u = userRepository.findByUsername("Peter");
        System.out.println(u.get());
    }



}
