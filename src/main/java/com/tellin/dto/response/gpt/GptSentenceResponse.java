package com.tellin.dto.response.gpt;

import lombok.Data;

import java.util.List;

@Data
public class GptSentenceResponse {
    private String sentenceEn;
    private String sentenceKo;
    private List<Chunk> chunks;
}
