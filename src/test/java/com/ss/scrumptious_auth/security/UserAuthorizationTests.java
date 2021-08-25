package com.ss.scrumptious_auth.security;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class UserAuthorizationTests {

    @Autowired
    WebApplicationContext wac;

    MockMvc mvc;

    @Before
    public void beforeEach() {
        mvc = MockMvcBuilders.webAppContextSetup(wac).apply(springSecurity()).build();

    }

    @Test
    @WithMockUser(roles = { "ADMIN" })
    public void test_OnlyAllowedByAdmin_Ok() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/adminOnly").accept(MediaType.ALL)).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = { "DRIVER" })
    public void test_OnlyAllowedByAdmin_Forbidden() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/adminOnly").accept(MediaType.ALL))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = { "DRIVER" })
    public void test_OnlyAllowedByDriver_Ok() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/driverOnly").accept(MediaType.ALL)).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = { "CUSTOMER" })
    public void test_OnlyAllowedByDriver_Forbidden() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/driverOnly").accept(MediaType.ALL))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = { "CUSTOMER" })
    public void test_OnlyAllowedByCustomer_Ok() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/customerOnly").accept(MediaType.ALL)).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = { "DRIVER" })
    public void test_OnlyAllowedByCustomer_Forbidden() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/customerOnly").accept(MediaType.ALL))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = { "ADMIN" })
    public void test_OnlyAllowedByCustomerAdmin_Ok1() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/customerAdmin").accept(MediaType.ALL)).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = { "CUSTOMER" })
    public void test_OnlyAllowedByCustomerAdmin_Ok2() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/customerAdmin").accept(MediaType.ALL)).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = { "DRIVER" })
    public void test_OnlyAllowedByCustomerAdmin_Forbidden() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/customerAdmin").accept(MediaType.ALL))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = {})
    public void test_OnlyAllowedByAll() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/users").accept(MediaType.ALL)).andExpect(status().isOk());
    }
}
