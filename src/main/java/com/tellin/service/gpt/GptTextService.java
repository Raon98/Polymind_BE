package com.tellin.service.gpt;

import com.tellin.dto.request.gpt.GptKeywordRequest;
import com.tellin.dto.request.gpt.GptPromptTemplate;
import com.tellin.support.config.gpt.GptProperties;
import com.tellin.support.exception.ErrorException;
import com.tellin.support.exception.ErrorResponse;
import com.tellin.support.logging.Log;
import com.theokanning.openai.OpenAiHttpException;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GptTextService {
    private final GptProperties gptProperties;
    private OpenAiService openAiService;

    @PostConstruct
    public void init() {
        Log.info("[GPT Key >>>> {}]",gptProperties.secretKey());
       this.openAiService = new OpenAiService(gptProperties.secretKey());
    }

    public String GenerateByKeyword(GptKeywordRequest keywordRequest) {
        Log.info("[START] ::::::: [GenerateByKeyword]");

        String prompt = GptPromptTemplate.CHUNK_ANALYSIS_PROMPT.formatted(keywordRequest.getStyle(),keywordRequest.getLevel(),keywordRequest.getTopic());

        Log.info("[GenerateByKeyword : prompt >>>> {}]",prompt);
        CompletionRequest completionRequest = CompletionRequest.builder()
                .model("gpt-3.5-turbo")
                .prompt(prompt)
                .temperature(0.7)
                .maxTokens(500)
                .build();

        try {
            String response = openAiService.createCompletion(completionRequest)
                    .getChoices().get(0).getText().trim();

            Log.info("[GenerateByKeyword : response >>>> {}]", response);
            return response;

        } catch (OpenAiHttpException e) {
            Log.error("[GPT API Error] Status: {}, Message: {}", e.statusCode, e.getMessage());
            throw new ErrorException(
                    ErrorResponse.builder()
                            .error("gpt_text_error_001" )
                            .error_description("서비스 이용이 불가능합니다.")
                            .error_code(e.statusCode)
                            .build()
            );
        }
    }
}
