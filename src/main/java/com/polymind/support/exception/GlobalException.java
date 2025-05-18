package com.polymind.support.exception;

import com.polymind.support.response.ApiResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

@ControllerAdvice
public class GlobalException {
    @ExceptionHandler(IOException.class)
    public ResponseEntity<ApiResponseEntity<?>> handleIOException(IOException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponseEntity.builder()
                        .data("IO 오류가 발생했습니다: " + ex.getMessage())
                        .build());
    }

    @ExceptionHandler(InterruptedException.class)
    public ResponseEntity<ApiResponseEntity<?>> handleInterruptedException(InterruptedException ex) {
        Thread.currentThread().interrupt();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponseEntity.builder()
                        .data("작업이 중단되었습니다: " + ex.getMessage())
                        .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseEntity<?>> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponseEntity.builder()
                        .data("예상치 못한 오류가 발생했습니다: " + ex.getMessage())
                        .build());
    }
}
