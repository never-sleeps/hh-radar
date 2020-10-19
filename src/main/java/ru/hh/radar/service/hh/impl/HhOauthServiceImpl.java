package ru.hh.radar.service.hh.impl;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.hh.radar.service.hh.HhOauthService;

import java.net.URI;

@Data
@Service
@RequiredArgsConstructor
public class HhOauthServiceImpl implements HhOauthService {

    @Value("${headhunter.api.baseUrl}")
    private String url;

    @Value("${oauth.clientId}")
    private String oauthClientId;

    @Value("${oauth.clientSecret}")
    private String oauthClientSecret;

    @Value("${oauth.redirectUri}")
    private String redirectUri;

    private RestTemplate restTemplate = new RestTemplate();

    /**
     *     https://hh.ru/oauth/authorize?
     *         response_type=code&
     *         client_id={client_id}&
     *         state={state}&
     *         redirect_uri={redirect_uri}
     *
     *         Обязательные параметры:
     *         response_type - указание на способ получения авторизации, используя authorization code,
     *         client_id - идентификатор, полученный при создании приложения
     */
    @Override
    public URI getUserAuthorizeURI() {
        return UriComponentsBuilder.fromHttpUrl(url)
                .path("oauth/authorize")
                .queryParam("response_type", "code")
                .queryParam("client_id", oauthClientId)
                .queryParam("redirect_uri", redirectUri)
                .build()
                .toUri();
    }




//    /**
//     * Метод обновления токена.
//     * https://github.com/hhru/api/blob/master/docs/authorization_for_user.md#refresh_token
//     */
//    public void updateTokens(Student student){
//        ClientAccessToken oldToken = student.getClientAccessToken();
//        OAuth2AccessToken newToken = service.refreshAccessToken(oldToken.getRefreshToken());
//        oldToken.setAccessToken(newToken.getAccessToken());
//        oldToken.setRefreshToken(newToken.getRefreshToken());
//        oldToken.setTime(ZonedDateTime.now());
//        clientAccessTokenService.update(oldToken);
//    }

//    /**
//     * Проверяет валидность токена студента
//     * https://github.com/hhru/api/blob/master/docs/authorization.md#check-access_token
//     */
//    public boolean accessTokenIsValid(Student student) {
//        OAuth2AccessToken accessToken = new OAuth2AccessToken(student.getClientAccessToken().getAccessToken());
//        OAuthRequest request = new OAuthRequest(Verb.GET, "https://api.hh.ru/me", service);
//        service.signRequest(accessToken, request);
//        Response response = request.send();
//        return response.getCode() == 200;
//    }
}
