package com.polymind.controller.gpt;

import com.polymind.support.response.ApiResponseEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/gpt")
public class GptTextController {

    @PostMapping("/generate")
    public ResponseEntity<ApiResponseEntity<?>> generateByKeyword(){
        return ResponseEntity.ok(
                ApiResponseEntity.builder()
                .data("generateByKeyword")
                .build());
    }
}
