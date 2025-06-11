package me.maxhub.hercules.controller.exercise;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import me.maxhub.hercules.dto.ErrorResponseDto;
import me.maxhub.hercules.dto.ExerciseRequestDto;
import me.maxhub.hercules.dto.ExerciseResponseDto;
import me.maxhub.hercules.service.exercise.ExerciseFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("api/v1/exercises")
@RequiredArgsConstructor
@Tag(name = "Exercise API", description = "API that allows to manage exercises")
public class ExerciseController {

    private final ExerciseFacade exerciseFacade;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponse(responseCode = "201", description = "Exercise created successfully")
    public void createExercise(@AuthenticationPrincipal Jwt jwt,
                               @RequestBody @NotNull @Validated ExerciseRequestDto exerciseRequestDto) {
        var subject = jwt.getSubject();
        exerciseFacade.createExercise(subject, exerciseRequestDto);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponse(responseCode = "200", description = "Exercise updated successfully")
    @ApiResponse(
            responseCode = "400",
            description = "Bad request. Parameters are not valid",
            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
    )
    public void updateExercise(@AuthenticationPrincipal Jwt jwt,
                               @PathVariable("id") @NotNull String id,
                               @RequestBody @NotNull ExerciseRequestDto exerciseRequestDto) {
        exerciseFacade.updateExercise(jwt, id, exerciseRequestDto);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponse(responseCode = "200", description = "Exercise deleted successfully")
    @ApiResponse(
            responseCode = "400",
            description = "Bad request. Parameters are not valid",
            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
    )
    public void deleteExercise(@AuthenticationPrincipal Jwt jwt,
                               @PathVariable("id") @NotNull String id) {
        exerciseFacade.deleteExercise(jwt, id);
    }

    @GetMapping(value = "{id}", produces = "application/json")
    @ApiResponse(responseCode = "200", description = "Exercise retrieved successfully")
    @ApiResponse(
            responseCode = "400",
            description = "Bad request. Parameters are not valid",
            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
    )
    public ResponseEntity<ExerciseResponseDto> getExercise(@AuthenticationPrincipal Jwt jwt,
                                                           @PathVariable("id") @NotNull String id) {
        var subject = jwt.getSubject();
        return ResponseEntity.ok(exerciseFacade.getExercise(subject, id));
    }

    @GetMapping
    @ApiResponse(responseCode = "200", description = "Exercises retrieved successfully")
    @ApiResponse(
            responseCode = "400",
            description = "Bad request. Parameters are not valid",
            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
    )
    public ResponseEntity<Collection<ExerciseResponseDto>> getExercises(@AuthenticationPrincipal Jwt jwt) {
        var subject = jwt.getSubject();
        var exercises = exerciseFacade.getExercises(subject);
        if (!exercises.isEmpty()) {
            return ResponseEntity.ok(exercises);
        }
        return ResponseEntity.noContent().build();
    }
}
