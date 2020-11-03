package ru.hh.radar.utils;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.web.util.UriComponentsBuilder;
import ru.hh.radar.model.SearchParametersType;
import ru.hh.radar.model.entity.ClientAccessToken;
import ru.hh.radar.model.entity.SearchParameters;
import ru.hh.radar.model.entity.User;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class WebRequestUtilsTest {

    @Test
    void shouldReturnAuthorizationHttpHeader() {
        // given
        String accessToken = "abcdefghijklmnopqrstuvwxyz1234567890";
        ClientAccessToken token = ClientAccessToken.builder()
                .accessToken(accessToken)
                .build();
        User user = User.builder()
                .clientAccessToken(token)
                .build();
        // when
        HttpHeaders result = WebRequestUtils.getAuthorizationHttpHeader(user);
        // then
        assertNotNull(result);
    }

    @Test
    void applySearchParameters() {
        // given
        UriComponentsBuilder uriComponentsBuilder =
                UriComponentsBuilder.fromHttpUrl("https://api.ru/").path("/path");
        Map<SearchParametersType, String> parameters = Map.of(SearchParametersType.TEXT, "value");
        // when
        UriComponentsBuilder result = WebRequestUtils.applySearchParameters(uriComponentsBuilder, parameters);
        // then
        assertEquals("https://api.ru/path?text=value", result.toUriString());
    }
}