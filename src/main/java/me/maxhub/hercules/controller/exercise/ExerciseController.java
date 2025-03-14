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
import me.maxhub.hercules.service.ExerciseFacade;
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
    public void createExercise(@RequestBody @NotNull @Validated ExerciseRequestDto exerciseRequestDto) {
        exerciseFacade.createExercise(exerciseRequestDto);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponse(responseCode = "200", description = "Exercise updated successfully")
    @ApiResponse(
            responseCode = "400",
            description = "Bad request. Parameters are not valid",
            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
    )
    public void updateExercise(@PathVariable("id") @NotNull String id,
                               @RequestBody @NotNull @Validated ExerciseRequestDto exerciseRequestDto) {
        exerciseFacade.updateExercise(id, exerciseRequestDto);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponse(responseCode = "200", description = "Exercise deleted successfully")
    @ApiResponse(
            responseCode = "400",
            description = "Bad request. Parameters are not valid",
            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
    )
    public void deleteExercise(@PathVariable("id") @NotNull String id) {
        exerciseFacade.deleteExercise(id);
    }

    @GetMapping(value = "{id}", produces = "application/json")
    @ApiResponse(responseCode = "200", description = "Exercise retrieved successfully")
    @ApiResponse(
            responseCode = "400",
            description = "Bad request. Parameters are not valid",
            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
    )
    public ResponseEntity<ExerciseResponseDto> getExercise(@PathVariable("id") @NotNull String id) {
        return ResponseEntity.ok(exerciseFacade.getExercise(id));
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
        // todo get exercises by user id
        var exercises = exerciseFacade.getExercises();
        if (!exercises.isEmpty()) {
            return ResponseEntity.ok(exercises);
        }
        return ResponseEntity.noContent().build();
    }
}
