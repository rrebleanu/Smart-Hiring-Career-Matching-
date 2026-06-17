package com.project.demo.controller;

import com.project.demo.model.Anunt;
import com.project.demo.model.Candidat;
import com.project.demo.service.AnunturiService;
import com.project.demo.service.AplicareService;
import com.project.demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AnuntController.class)
public class AnuntControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // You have to mock every dependency injected in the constructor
    @MockBean
    private AnunturiService anuntService;

    @MockBean
    private UserService userService;

    @MockBean
    private AplicareService aplicareService;

    @Test
    @WithMockUser // Satisfies the Spring Security configuration barrier
    public void testListaAnunturi_AsCandidat() throws Exception {
        // Arrange
        Candidat dummyCandidat = new Candidat();
        Mockito.when(anuntService.getAll()).thenReturn(Collections.emptyList());
        Mockito.when(userService.getCurrentUser()).thenReturn(dummyCandidat);
        Mockito.when(aplicareService.anunturiAplicate(dummyCandidat)).thenReturn(Collections.emptySet());

        // Act & Assert
        mockMvc.perform(get("/anunturi"))
                .andExpect(status().isOk())
                .andExpect(view().name("anunturi"))
                .andExpect(model().attributeExists("anunturi"))
                .andExpect(model().attributeExists("anunturiAplicate"));
    }
}
