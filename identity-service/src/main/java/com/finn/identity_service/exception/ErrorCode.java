package com.finn.identity_service.exception;

public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(400, "Uncategorized error"),
    INVALID_KEY(400, "Invalid message key"),
    USER_ALREADY_EXISTS(400, "User already exists"),
    USER_NOT_FOUND(404, "User not found"),
    INVALID_USERNAME(400, "Username must be at least 3 characters"),
    INVALID_PASSWORD(400, "Password must be at least 8 characters"),
    INVALID_DOB(400, "Date of birth must be in the past"),
    UNAUTHENTICATED(401, "Unauthenticated")
    ;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
