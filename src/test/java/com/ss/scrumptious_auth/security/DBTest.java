package com.ss.scrumptious_auth.security;

import com.ss.scrumptious.common_entities.entity.User;
import com.ss.scrumptious.common_entities.entity.UserRole;
import com.ss.scrumptious_auth.dao.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DBTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Before
    public void test(){
        User user = User.builder().email("test2@test.com").password(encoder.encode("123"))
                .userRole(UserRole.ADMIN).build();
        System.out.println(user);
        User u = userRepository.save(user);
    }

    @Test
    public void fetchUser(){
        Optional<User> u = userRepository.findByEmail("test2@test.com");
        System.out.println(u.get());
    }
}
