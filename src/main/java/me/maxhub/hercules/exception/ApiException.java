package me.maxhub.hercules.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class ApiException extends RuntimeException {
    private final String errorCode;
    private final Integer statusCode;

    protected ApiException(String errorCode, Integer statusCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.statusCode = statusCode;
    }

    protected ApiException(String errorCode, HttpStatus statusCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.statusCode = statusCode.value();
    }

    protected ApiException(String errorCode, Integer statusCode) {
        this.errorCode = errorCode;
        this.statusCode = statusCode;
    }

    protected ApiException(String errorCode, HttpStatus statusCode) {
        this.errorCode = errorCode;
        this.statusCode = statusCode.value();
    }
}

