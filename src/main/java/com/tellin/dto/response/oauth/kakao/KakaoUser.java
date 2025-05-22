package com.tellin.dto.response.oauth.kakao;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class KakaoUser {
    private Long id;
    @JsonProperty("connected_at")
    private String connectedAt;
    private KakaoProperties properties;
}
