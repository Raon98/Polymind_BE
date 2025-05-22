package com.tellin.support.exception;

import com.tellin.support.response.ApiResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(OAuth2LoginException.class)
    public ResponseEntity<ApiResponseEntity<?>> handleOAuth2Exception(OAuth2LoginException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponseEntity.builder()
                        .data(ex.getError())
                        .isAuthError(true)
                        .build());
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ApiResponseEntity<?>> handleIOException(IOException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponseEntity.builder()
                        .data("IO 오류가 발생했습니다: " + ex.getMessage())
                        .isAuthError(true)
                        .build());
    }

    @ExceptionHandler(InterruptedException.class)
    public ResponseEntity<ApiResponseEntity<?>> handleInterruptedException(InterruptedException ex) {
        Thread.currentThread().interrupt();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponseEntity.builder()
                        .data("작업이 중단되었습니다: " + ex.getMessage())
                        .isAuthError(true)
                        .build());
    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ApiResponseEntity<?>> handleGenericException(Exception ex) {
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                .body(ApiResponseEntity.builder()
//                        .data("예상치 못한 오류가 발생했습니다: " + ex)
//                        .build());
//    }

}
