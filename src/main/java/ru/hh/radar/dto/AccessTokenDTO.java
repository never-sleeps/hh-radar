package ru.hh.radar.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import ru.hh.radar.model.entity.ClientAccessToken;

@Data
public class AccessTokenDTO {

    @JsonProperty(value = "access_token")
    private String accessToken;

    @JsonProperty(value = "token_type")
    private String tokenType;

    @JsonProperty(value = "refresh_token")
    private String refreshToken;

    @JsonProperty(value = "expires_in")
    private Integer expiresIn;

    public ClientAccessToken toObject() {
        return ClientAccessToken.builder()
                .accessToken(this.getAccessToken())
                .tokenType(this.getTokenType())
                .refreshToken(this.getRefreshToken())
                .expiresIn(this.getExpiresIn())
//                .time(ZonedDateTime.now())
                .build();
    }
}