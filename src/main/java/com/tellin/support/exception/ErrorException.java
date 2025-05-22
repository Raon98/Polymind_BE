package com.tellin.support.exception;

import lombok.Getter;

@Getter
public class ErrorException extends RuntimeException{
    private final ErrorResponse error;

    public ErrorException(ErrorResponse error) {
        super(error.getError_description());
        this.error = error;
    }
}
