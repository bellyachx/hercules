package me.maxhub.hercules.service.impl;

import me.maxhub.hercules.dto.ExerciseRequestDto;
import me.maxhub.hercules.dto.ExerciseResponseDto;
import me.maxhub.hercules.entity.exercise.DifficultyEntity;
import me.maxhub.hercules.entity.exercise.ExerciseEntity;
import me.maxhub.hercules.entity.exercise.ExerciseTypeEntity;
import me.maxhub.hercules.exception.ExerciseNotFoundException;
import me.maxhub.hercules.mapper.ExerciseMapper;
import me.maxhub.hercules.repo.exercise.DifficultyRepository;
import me.maxhub.hercules.repo.exercise.ExerciseRepository;
import me.maxhub.hercules.repo.exercise.ExerciseTypeRepository;
import me.maxhub.hercules.repo.exercise.MuscleGroupRepository;
import me.maxhub.hercules.service.exercise.impl.ExerciseFacadeImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.*;

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

        when(mapper.toEntity(any(), any(), any(), any())).thenReturn(exerciseEntity);

        exerciseFacade.createExercise("0", exerciseRequestDto);

        verify(exerciseRepository, times(1)).save(any(ExerciseEntity.class));
    }

    @Test
    void updateExercise_ShouldUpdateExistingExercise() {
        when(exerciseRepository.findByIdAndUserIdIn(eq(exerciseId), anyList())).thenReturn(Optional.of(exerciseEntity));
        when(mapper.toEntity(any(), any(), any(), any())).thenReturn(exerciseEntity);

        var jwt = mock(Jwt.class);
        Mockito.when(jwt.getSubject()).thenReturn("0");
        Mockito.when(jwt.getClaim(any())).thenReturn(Collections.emptyMap());
        exerciseFacade.updateExercise(jwt, exerciseId, exerciseRequestDto);

        verify(exerciseRepository, times(1)).save(any(ExerciseEntity.class));
    }

    @Test
    void updateExercise_ShouldThrowException_WhenExerciseNotFound() {
        when(exerciseRepository.findByIdAndUserIdIn(eq(exerciseId), anyList())).thenReturn(Optional.empty());

        var jwt = mock(Jwt.class);
        Mockito.when(jwt.getSubject()).thenReturn("0");
        Mockito.when(jwt.getClaim(any())).thenReturn(Collections.emptyMap());
        assertThrows(ExerciseNotFoundException.class, () -> exerciseFacade.updateExercise(jwt, exerciseId, exerciseRequestDto));

        verify(exerciseRepository, never()).save(any());
    }

    @Test
    void deleteExercise_ShouldDelete_WhenExerciseExists() {
        when(exerciseRepository.existsByIdAndUserIdIn(eq(exerciseId), anyList())).thenReturn(true);

        var jwt = mock(Jwt.class);
        Mockito.when(jwt.getSubject()).thenReturn("0");
        Mockito.when(jwt.getClaim(any())).thenReturn(Collections.emptyMap());
        exerciseFacade.deleteExercise(jwt, exerciseId);

        verify(exerciseRepository, times(1)).deleteById(exerciseId);
    }

    @Test
    void deleteExercise_ShouldThrowException_WhenExerciseNotFound() {
        when(exerciseRepository.existsByIdAndUserIdIn(eq(exerciseId), anyList())).thenReturn(false);

        var jwt = mock(Jwt.class);
        Mockito.when(jwt.getSubject()).thenReturn("0");
        Mockito.when(jwt.getClaim(any())).thenReturn(Collections.emptyMap());
        assertThrows(ExerciseNotFoundException.class, () -> exerciseFacade.deleteExercise(jwt, exerciseId));

        verify(exerciseRepository, never()).deleteById(any());
    }

    @Test
    void getExercise_ShouldReturnExercise_WhenFound() {
        when(exerciseRepository.findByIdAndUserIdIn(eq(exerciseId), anyList())).thenReturn(Optional.of(exerciseEntity));
        when(mapper.toDto(exerciseEntity)).thenReturn(exerciseResponseDto);

        var result = exerciseFacade.getExercise("0", exerciseId);

        assertNotNull(result);
        assertEquals(exerciseId, result.getId());
    }

    @Test
    void getExercise_ShouldThrowException_WhenNotFound() {
        when(exerciseRepository.findByIdAndUserIdIn(eq(exerciseId), anyList())).thenReturn(Optional.empty());

        assertThrows(ExerciseNotFoundException.class, () -> exerciseFacade.getExercise("0", exerciseId));
    }

    @Test
    void getExercises_ShouldReturnAllExercises() {
        when(exerciseRepository.findAllByUserIdIn(anyList())).thenReturn(List.of(exerciseEntity));
        when(mapper.toDto(any())).thenReturn(exerciseResponseDto);

        var result = exerciseFacade.getExercises("0");

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(exerciseRepository, times(1)).findAllByUserIdIn(anyList());
    }
}
