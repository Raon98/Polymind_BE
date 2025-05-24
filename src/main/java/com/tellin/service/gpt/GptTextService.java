package com.tellin.service.gpt;

import com.fasterxml.jackson.core.type.TypeReference;
import com.tellin.dto.request.gpt.GptKeywordRequest;
import com.tellin.dto.request.gpt.GptPromptTemplate;
import com.tellin.dto.response.gpt.GptSentenceResponse;
import com.tellin.support.config.gpt.GptProperties;
import com.tellin.support.exception.ErrorException;
import com.tellin.support.exception.ErrorResponse;
import com.tellin.support.logging.Log;
import com.tellin.support.utils.ObjectMapperUtils;
import com.theokanning.openai.OpenAiHttpException;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<GptSentenceResponse> GenerateByKeyword(GptKeywordRequest keywordRequest) {
        Log.info("[START] ::::::: [GenerateByKeyword]");

        String prompt = GptPromptTemplate.CHUNK_ANALYSIS_PROMPT.formatted(keywordRequest.getStyle(),keywordRequest.getLevel(),keywordRequest.getTopic());
        List<ChatMessage> messages = List.of(
                new ChatMessage("system", GptPromptTemplate.SYSTEM_PROMPT),
                new ChatMessage("user", prompt)
        );
        Log.info("[GenerateByKeyword : prompt >>>> {}]",prompt);
        ChatCompletionRequest completionRequest = ChatCompletionRequest.builder()
                .model("gpt-3.5-turbo")
                .messages(messages)
                .temperature(0.7)
                .maxTokens(3000)
                .build();

        try {
            String response = openAiService.createChatCompletion(completionRequest)
                    .getChoices().get(0).getMessage().getContent().trim();
            Log.info("[GenerateByKeyword : response >>>> {}]", response);

            List<GptSentenceResponse> sentenceResponseList = ObjectMapperUtils.readValueToList(response, new TypeReference<List<GptSentenceResponse>>() {});

            return sentenceResponseList;

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
