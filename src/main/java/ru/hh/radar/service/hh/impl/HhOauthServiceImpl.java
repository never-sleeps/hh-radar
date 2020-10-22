package ru.hh.radar.service.hh.impl;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.hh.radar.dto.AccessTokenDTO;
import ru.hh.radar.model.entity.User;
import ru.hh.radar.service.common.UserService;
import ru.hh.radar.service.hh.HhOauthService;
import ru.hh.radar.telegram.service.TelegramService;

import java.net.URI;

@Slf4j
@Data
@Service
@RequiredArgsConstructor
public class HhOauthServiceImpl implements HhOauthService {

    @Value("${headhunter.api.baseUrl}")
    private String baseUrl;

    @Value("${oauth.clientId}")
    private String oauthClientId;

    @Value("${oauth.clientSecret}")
    private String oauthClientSecret;

    @Value("${oauth.redirectUri}")
    private String redirectUri;

    private final RestTemplate restTemplate = new RestTemplate();
    private final UserService userService;
    private final TelegramService telegramService;

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
        return UriComponentsBuilder.fromHttpUrl(baseUrl)
                .path("oauth/authorize")
                .queryParam("response_type", "code")
                .queryParam("client_id", oauthClientId)
                .queryParam("redirect_uri", redirectUri)
                .build()
                .toUri();
    }

    @Override
    public User authorizeUser(Update update) throws TelegramApiException {
        User user = userService.save(update);
        if(user.getAuthorizationCode() != null) {
            AccessTokenDTO token = getAccessTokenForUser(user);
            user.setClientAccessToken(token.toObject());
            user = userService.save(user);
        }
        log.info(String.format("%s: user connected: %s", user.getUsername(), user));
        return user;
    }

    private AccessTokenDTO getAccessTokenForUser(User user) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("grant_type", "authorization_code");
        map.add("client_id", oauthClientId);
        map.add("client_secret", oauthClientSecret);
        map.add("redirect_uri", redirectUri);
        map.add("code", user.getAuthorizationCode());

        URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl).path("oauth/token").build().toUri();
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        ResponseEntity<AccessTokenDTO> response = restTemplate.postForEntity(uri, request, AccessTokenDTO.class);
        log.info(String.format("%s: got access_token", user.getUsername()));
        return response.getBody();
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
