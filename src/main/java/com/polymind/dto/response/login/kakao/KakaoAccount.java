package com.polymind.dto.response.login.kakao;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class KakaoAccount {
    @JsonProperty("profile_needs_agreement")
    private Boolean profileNeedsAgreement;
    private Boolean profile_nickname_needs_agreement;
    private Boolean profile_image_needs_agreement;
    private KakaoProfile profile;
    private Boolean name_needs_agreement;
    private String name;
    @JsonProperty("email_needs_agreement")
    private Boolean emailNeedsAgreement;
    private Boolean is_email_valid;
    private Boolean is_email_verified;
    private String email;
    private Boolean age_range_needs_agreement;
    private String age_range;

}
