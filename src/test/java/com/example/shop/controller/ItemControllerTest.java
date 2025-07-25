package com.example.shop.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations="classpath:application-test.properties")
class ItemControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("Admin user can access item creation page")
    @WithMockUser(username = "admin", roles="ADMIN")
    public void itemFormTest() throws Exception {
        mockMvc.perform(get("/admin/item/new"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Normal user can't access item creation page")
    @WithMockUser(username = "user", roles="USER")
    public void itemFormTest2() throws Exception {
        mockMvc.perform(get("/admin/item/new"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

}