package ru.hh.radar.service.hh.impl;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.hh.radar.client.hh.HhOauthClient;
import ru.hh.radar.dto.AccessTokenDTO;
import ru.hh.radar.model.entity.User;
import ru.hh.radar.service.common.UserService;
import ru.hh.radar.service.hh.HhOauthService;

import java.net.URI;

@Slf4j
@Data
@Service
@RequiredArgsConstructor
public class HhOauthServiceImpl implements HhOauthService {

    private final UserService userService;
    private final HhOauthClient hhOauthClient;

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
        return hhOauthClient.getUserAuthorizeURI();
    }

    @Override
    public User authorizeUser(String userName, String command) {
        User user = userService.save(userName, command);
        if(user.getAuthorizationCode() != null) {
            AccessTokenDTO token = hhOauthClient.getAccessTokenForUser(user.getAuthorizationCode());
            user.setClientAccessToken(token.toObject());
            user = userService.save(user);
        }
        log.info(String.format("%s: user connected: %s", user.getUsername(), user));
        return user;
    }
}
