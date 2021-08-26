package com.ss.scrumptious_auth;

import com.ss.scrumptious_auth.dao.UserRepository;
import com.ss.scrumptious_auth.entity.User;
import com.ss.scrumptious_auth.entity.UserRole;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthApplicationTests {



    @Test
    public void contextLoads() {
    }



}
