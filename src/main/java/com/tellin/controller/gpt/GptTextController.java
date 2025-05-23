package com.tellin.controller.gpt;

import com.tellin.dto.request.gpt.GptKeywordRequest;
import com.tellin.dto.response.gpt.GptSentenceResponse;
import com.tellin.service.gpt.GptTextService;
import com.tellin.support.response.ApiResponseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/api/gpt")
@RequiredArgsConstructor
public class GptTextController {

    private final GptTextService gptTextService;
    @PostMapping("/generate")
    public ResponseEntity<ApiResponseEntity<?>> GenerateByKeyword(@RequestBody GptKeywordRequest request){

        List<GptSentenceResponse> sentenceResponseList = gptTextService.GenerateByKeyword(request);

        return ResponseEntity.ok(
                ApiResponseEntity.builder()
                .data(sentenceResponseList)
                .build());
    }
}
