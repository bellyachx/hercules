package me.maxhub.hercules.exception;

import org.springframework.http.HttpStatus;

public class ExerciseNotFoundException extends ApiException {
    public static final String CODE = "E101";

    public ExerciseNotFoundException(String id) {
        super(CODE, HttpStatus.BAD_REQUEST, "Exercise with ID [%s] not found!".formatted(id));
    }
}
