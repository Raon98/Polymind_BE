package com.tellin.service.gpt;

import com.tellin.dto.request.gpt.GptKeywordRequest;
import com.tellin.dto.request.gpt.GptPromptTemplate;
import com.tellin.support.config.gpt.GptProperties;
import com.tellin.support.logging.Log;
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

        String response = openAiService.createCompletion(completionRequest).getChoices().get(0).getText().trim();
        Log.info("[GenerateByKeyword : response >>>> {}]",response);

        return response;
    }


}
