package me.maxhub.hercules.service.exercise;

import jakarta.validation.constraints.NotNull;
import me.maxhub.hercules.dto.ExerciseRequestDto;
import me.maxhub.hercules.dto.ExerciseResponseDto;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;

import java.util.Collection;

@Validated
public interface ExerciseFacade {

    void createExercise(String subject, ExerciseRequestDto exerciseRequestDto);

    void updateExercise(Jwt jwt, String id, ExerciseRequestDto exerciseRequestDto);

    void deleteExercise(Jwt jwt, String id);

    ExerciseResponseDto getExercise(String subject, String id);

    @NotNull
    Collection<ExerciseResponseDto> getExercises(String subject);
}
