package com.finn.identity_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.Getter;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(1, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(2, "Invalid message key", HttpStatus.BAD_REQUEST),
    USER_ALREADY_EXISTS(3, "User already exists", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(4, "User not found", HttpStatus.NOT_FOUND),
    INVALID_USERNAME(5, "Username must be at least {min} characters", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(6, "Password must be at least {min} characters", HttpStatus.BAD_REQUEST),
    INVALID_DOB(7, "User must be at least {min} years old", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(8, "Authentication is required", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(9, "You do not have permission to access this resource", HttpStatus.FORBIDDEN),
    ;

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }
}
