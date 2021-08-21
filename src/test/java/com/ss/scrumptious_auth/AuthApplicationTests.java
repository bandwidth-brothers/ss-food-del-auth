package com.ss.scrumptious_auth;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Test
    public void test(){
        System.out.println(new Date(System.currentTimeMillis() + 864_000_000));
    }

}
