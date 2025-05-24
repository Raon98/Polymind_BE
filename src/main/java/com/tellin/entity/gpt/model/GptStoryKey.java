package com.tellin.entity.gpt.model;

public record GptStoryKey(
        String userId,
        String style,
        String level,
        String topic
) {}