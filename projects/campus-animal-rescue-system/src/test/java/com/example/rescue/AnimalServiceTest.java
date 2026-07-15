package com.example.rescue;

import com.example.rescue.mapper.AnimalMapper;
import com.example.rescue.service.AnimalService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AnimalServiceTest {
    @Test
    void deleteRejectsReferencedAnimal() {
        AnimalMapper animalMapper = mock(AnimalMapper.class);
        when(animalMapper.countReferences(1L)).thenReturn(1);
        AnimalService animalService = new AnimalService(animalMapper);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> animalService.delete(1L));

        assertEquals("动物已有救助或领养记录，不能删除", ex.getMessage());
    }

    @Test
    void deleteRemovesUnreferencedAnimal() {
        AnimalMapper animalMapper = mock(AnimalMapper.class);
        when(animalMapper.countReferences(2L)).thenReturn(0);
        AnimalService animalService = new AnimalService(animalMapper);

        animalService.delete(2L);

        verify(animalMapper).delete(2L);
    }
}
