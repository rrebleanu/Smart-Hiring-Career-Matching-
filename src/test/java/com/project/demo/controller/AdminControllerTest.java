package com.project.demo.controller;

import com.project.demo.model.Admin;
import com.project.demo.repository.AdminRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdminController.class)
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // You MUST mock the repository, otherwise the application context fails to load
    @MockitoBean
    private AdminRepository adminRepository;

    @Test
    @WithMockUser // Bypasses Spring Security 401 Unauthorized errors
    void addNewAdmin() throws Exception {
        // Act & Assert
        mockMvc.perform(post("/api/admin/add")
                        .with(csrf()) // Required by Spring Security for POST requests
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"numeUser\":\"AdminUser\", \"email\":\"admin@system.com\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Admin Saved Successfully"));

        // Verify that the repository's save method was actually called once
        Mockito.verify(adminRepository, Mockito.times(1)).save(any(Admin.class));
    }

    @Test
    @WithMockUser
    void getAllAdmins() throws Exception {
        // Arrange: Tell the mock repository to return an empty list when asked
        Mockito.when(adminRepository.findAll()).thenReturn(Collections.emptyList());

        // Act & Assert
        mockMvc.perform(get("/api/admin/all"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]")); // Expects an empty JSON array
    }
}