package com.tellin.repository.gpt;

import com.tellin.entity.gpt.GptGeneratedStory;
import com.tellin.entity.gpt.model.GptStoryKey;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface GptGeneratedStoryQueryCustomRepository {
    Optional<GptGeneratedStory> findUnseenStory(
            @Param("userId") String userId,
            @Param("style") String style,
            @Param("level") String level,
            @Param("topic") String topic
    );

    default Optional<GptGeneratedStory>findUnseenStory(GptStoryKey storyKey){
        return findUnseenStory(storyKey.userId(),storyKey.style(),storyKey.level(),storyKey.topic());
    }
}
