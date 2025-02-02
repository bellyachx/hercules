package me.maxhub.hercules.service;

import jakarta.validation.constraints.NotNull;
import me.maxhub.hercules.dto.ExerciseRequestDto;
import me.maxhub.hercules.dto.ExerciseResponseDto;
import org.springframework.validation.annotation.Validated;

import java.util.Collection;

@Validated
public interface ExerciseFacade {

    void createExercise(ExerciseRequestDto exerciseRequestDto);

    void updateExercise(String id, ExerciseRequestDto exerciseRequestDto);

    void deleteExercise(String id);

    ExerciseResponseDto getExercise(String id);

    @NotNull
    Collection<ExerciseResponseDto> getExercises();
}
