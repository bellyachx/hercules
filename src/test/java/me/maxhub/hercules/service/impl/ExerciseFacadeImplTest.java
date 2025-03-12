package me.maxhub.hercules.service.impl;

import me.maxhub.hercules.dto.ExerciseRequestDto;
import me.maxhub.hercules.dto.ExerciseResponseDto;
import me.maxhub.hercules.entity.exercise.*;
import me.maxhub.hercules.exception.ExerciseNotFoundException;
import me.maxhub.hercules.mapper.ExerciseMapper;
import me.maxhub.hercules.repo.DifficultyRepository;
import me.maxhub.hercules.repo.ExerciseRepository;
import me.maxhub.hercules.repo.ExerciseTypeRepository;
import me.maxhub.hercules.repo.MuscleGroupRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExerciseFacadeImplTest {

    @Mock
    private ExerciseRepository exerciseRepository;

    @Mock
    private DifficultyRepository difficultyRepository;

    @Mock
    private ExerciseTypeRepository exerciseTypeRepository;

    @Mock
    private MuscleGroupRepository muscleGroupRepository;

    @Mock
    private ExerciseMapper mapper;

    @InjectMocks
    private ExerciseFacadeImpl exerciseFacade;

    private ExerciseRequestDto exerciseRequestDto;
    private ExerciseResponseDto exerciseResponseDto;
    private ExerciseEntity exerciseEntity;
    private String exerciseId;

    @BeforeEach
    void setUp() {
        exerciseId = UUID.randomUUID().toString();

        exerciseRequestDto = new ExerciseRequestDto();
        exerciseRequestDto.setDifficulty("Medium");
        exerciseRequestDto.setExerciseType("Cardio");
        exerciseRequestDto.setMuscleGroups(Set.of("Legs", "Core"));

        exerciseResponseDto = new ExerciseResponseDto();
        exerciseResponseDto.setId(exerciseId);

        exerciseEntity = new ExerciseEntity();
        exerciseEntity.setId(exerciseId);
    }

    @Test
    void createExercise_ShouldSaveEntity() {
        when(difficultyRepository.findByDifficultyName(any())).thenReturn(Optional.empty());
        when(difficultyRepository.save(any())).thenReturn(new DifficultyEntity("Medium"));

        when(exerciseTypeRepository.findByExerciseTypeName(any())).thenReturn(Optional.empty());
        when(exerciseTypeRepository.save(any())).thenReturn(new ExerciseTypeEntity("Cardio"));

        when(mapper.toEntity(any(), any(), any())).thenReturn(exerciseEntity);

        exerciseFacade.createExercise(exerciseRequestDto);

        verify(exerciseRepository, times(1)).save(any(ExerciseEntity.class));
    }

    @Test
    void updateExercise_ShouldUpdateExistingExercise() {
        when(exerciseRepository.findById(exerciseId)).thenReturn(Optional.of(exerciseEntity));
        when(mapper.toEntity(any(), any(), any())).thenReturn(exerciseEntity);

        exerciseFacade.updateExercise(exerciseId, exerciseRequestDto);

        verify(exerciseRepository, times(1)).save(any(ExerciseEntity.class));
    }

    @Test
    void updateExercise_ShouldThrowException_WhenExerciseNotFound() {
        when(exerciseRepository.findById(exerciseId)).thenReturn(Optional.empty());

        assertThrows(ExerciseNotFoundException.class, () -> exerciseFacade.updateExercise(exerciseId, exerciseRequestDto));

        verify(exerciseRepository, never()).save(any());
    }

    @Test
    void deleteExercise_ShouldDelete_WhenExerciseExists() {
        when(exerciseRepository.existsById(exerciseId)).thenReturn(true);

        exerciseFacade.deleteExercise(exerciseId);

        verify(exerciseRepository, times(1)).deleteById(exerciseId);
    }

    @Test
    void deleteExercise_ShouldThrowException_WhenExerciseNotFound() {
        when(exerciseRepository.existsById(exerciseId)).thenReturn(false);

        assertThrows(ExerciseNotFoundException.class, () -> exerciseFacade.deleteExercise(exerciseId));

        verify(exerciseRepository, never()).deleteById(any());
    }

    @Test
    void getExercise_ShouldReturnExercise_WhenFound() {
        when(exerciseRepository.findById(exerciseId)).thenReturn(Optional.of(exerciseEntity));
        when(mapper.toDto(exerciseEntity)).thenReturn(exerciseResponseDto);

        var result = exerciseFacade.getExercise(exerciseId);

        assertNotNull(result);
        assertEquals(exerciseId, result.getId());
    }

    @Test
    void getExercise_ShouldThrowException_WhenNotFound() {
        when(exerciseRepository.findById(exerciseId)).thenReturn(Optional.empty());

        assertThrows(ExerciseNotFoundException.class, () -> exerciseFacade.getExercise(exerciseId));
    }

    @Test
    void getExercises_ShouldReturnAllExercises() {
        when(exerciseRepository.findAll()).thenReturn(List.of(exerciseEntity));
        when(mapper.toDto(any())).thenReturn(exerciseResponseDto);

        var result = exerciseFacade.getExercises();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(exerciseRepository, times(1)).findAll();
    }
}
