package com.tellin.dto.response.gpt;

import lombok.Data;

@Data
public class Chunk {
    private String text;
    private String role;
    private String orderIndex;
}
