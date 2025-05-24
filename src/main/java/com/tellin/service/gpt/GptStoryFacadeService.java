package com.tellin.service.gpt;

import com.fasterxml.jackson.core.type.TypeReference;
import com.tellin.dto.request.gpt.GptKeywordRequest;
import com.tellin.dto.request.jwt.JwtUserDto;
import com.tellin.dto.response.gpt.GptSentenceResponse;
import com.tellin.entity.gpt.model.GptStoryKey;
import com.tellin.repository.gpt.GptGeneratedStoryQueryRepository;
import com.tellin.support.utils.ObjectMapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GptStoryFacadeService {

    private final GptGeneratedStoryQueryRepository gptGeneratedStoryQueryRepository;
    private final GptTextService gptTextService;
    public List<GptSentenceResponse> getOrGenerateStory(GptKeywordRequest keywordRequest) {
        JwtUserDto currentUser = (JwtUserDto) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        String userId = currentUser.getUserId();
        GptStoryKey gptStoryKey = new GptStoryKey(
                userId,
                keywordRequest.getStyle(),
                keywordRequest.getLevel(),
                keywordRequest.getTopic()
        );
        return gptGeneratedStoryQueryRepository.findUnseenStory(gptStoryKey)
                .map(story -> ObjectMapperUtils.readValueToList(
                        story.getContent(), new TypeReference<List<GptSentenceResponse>>() {}
                )).orElse(gptTextService.GenerateByKeyword(keywordRequest));
    }
}
