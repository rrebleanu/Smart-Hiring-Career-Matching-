//package com.project.demo.controller;
//
//import com.project.demo.model.Angajator;
//import com.project.demo.model.Anunt;
//import com.project.demo.repository.AngajatorRepository;
//import com.project.demo.service.AnunturiService;
//import com.project.demo.service.AplicareService;
//import com.project.demo.service.UserService;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
//import org.springframework.test.context.bean.override.mockito.MockitoBean;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.web.servlet.MockMvc;
//import com.project.demo.config.SecurityConfig;
//import com.project.demo.service.CustomUserDetailsService;
//import org.springframework.context.annotation.Import;
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
//
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@WebMvcTest(AngajatorController.class)
//@Import(SecurityConfig.class)
//public class AngajatorControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockitoBean
//    private AnunturiService anunturiService;
//
//    @MockitoBean
//    private CustomUserDetailsService customUserDetailsService;
//
//    @MockitoBean
//    private UserService userService;
//
//    @MockitoBean
//    private AplicareService aplicareService;
//
//    @MockitoBean
//    private AngajatorRepository angajatorRepository; // Unused by this method, but mandatory for context loading
//
//    @Test
//    @WithMockUser
//    public void testSalveazaAnunt_RedirectsOnSuccess() throws Exception {
//        // Arrange
//        Angajator dummyAngajator = new Angajator();
//        Mockito.when(userService.getCurrentUser()).thenReturn(dummyAngajator);
//
//        // Act & Assert
//        mockMvc.perform(post("/angajator/anunturi/adauga")
//                        .with(csrf()) // Fixes the 403 Forbidden caused by missing CSRF
//                        .with(user("employerUser").roles("ANGAJATOR")) // Ensures proper roles if secured by URL patterns
//                        .param("titlu", "Junior Engineer Profile")
//                        .param("descriereJob", "Requires 10 years experience"))
//                .andExpect(status().is3xxRedirection());
//
//        // Verify the database layer/service was actually commanded to write data
//        Mockito.verify(anunturiService, Mockito.times(1)).save(any(Anunt.class));
//    }
//}