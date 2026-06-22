package com.project.demo.service;

import com.project.demo.model.Anunt;
import com.project.demo.repository.AnuntRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Fast. No Spring, no context, no Docker, no nonsense.
class AnuntServiceTest {

    @Mock
    private AnuntRepository anuntRepository;

    @InjectMocks
    private AnunturiService anuntService; // Assumes you have an AnuntService class

    private Anunt sampleAnunt;

    @BeforeEach
    void setUp() {
        sampleAnunt = new Anunt();
        sampleAnunt.setId(100);
        sampleAnunt.setTitlu("Junior Java Dev");
        sampleAnunt.setSalariuMin(4000.0);
        sampleAnunt.setSalariuMax(6000.0);
    }

    @Test
    void testGetAnuntById_Success() {
        when(anuntRepository.findById(100)).thenReturn(Optional.of(sampleAnunt));

        Anunt result = anuntService.getAnuntById(100);

        assertNotNull(result);
        assertEquals("Junior Java Dev", result.getTitlu());
        verify(anuntRepository, times(1)).findById(100);
    }

    @Test
    void testGetAnuntById_NotFound_ShouldThrowException() {
        when(anuntRepository.findById(999)).thenReturn(Optional.empty());

        // Assumes your service throws a custom exception or RuntimeException when missing
        assertThrows(RuntimeException.class, () -> {
            anuntService.getAnuntById(999);
        });
    }
}