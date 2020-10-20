package ru.hh.radar.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import ru.hh.radar.model.entity.ClientAccessToken;
import ru.hh.radar.service.Utils;

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
                .accessToken(Utils.encode(this.getAccessToken()))
                .tokenType(this.getTokenType())
                .refreshToken(Utils.encode(this.getRefreshToken()))
                .expiresIn(this.getExpiresIn())
//                .time(ZonedDateTime.now())
                .build();
    }
}