package com.tellin.support.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ErrorResponse {
    /*error*/
    private String error;
    private String error_description;
    private int error_code;
}
