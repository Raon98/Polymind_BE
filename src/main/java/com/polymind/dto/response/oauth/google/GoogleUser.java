package com.polymind.dto.response.oauth.google;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GoogleUser {
    @JsonProperty("sub")
    private String id;
    private String email;
    private Boolean verified_email;
    private String name;
    private String picture;
    private String locale;
}
