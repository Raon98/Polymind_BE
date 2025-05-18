package com.polymind.dto.response.login.kakao;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class KakaoAccount {
    @JsonProperty("profile_needs_agreement")
    private Boolean profileNeedsAgreement;
    private KakaoProfile profile;
    @JsonProperty("email_needs_agreement")
    private Boolean emailNeedsAgreement;
    private String email;
}
