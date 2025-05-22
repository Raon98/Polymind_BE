package com.tellin.dto.request.gpt;

import lombok.Getter;

@Getter
public class GptKeywordRequest {
    private String level; // 난이도
    private String style; // 스타일 ㅜ제
    private String topic; //이야기 키워드
}
