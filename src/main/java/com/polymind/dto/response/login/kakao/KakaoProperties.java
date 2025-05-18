package com.polymind.dto.response.login.kakao;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class KakaoProperties {
    private String nickname;

    @JsonProperty("profile_image")
    private String profileImage;

    @JsonProperty("thumbnail_image")
    private String thumbnailImage;
}