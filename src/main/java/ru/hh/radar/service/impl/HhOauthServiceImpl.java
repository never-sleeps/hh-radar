package ru.hh.radar.service.impl;

import com.github.scribejava.apis.HHApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import ru.hh.radar.model.User;
import ru.hh.radar.service.HhOauthService;

@Data
@Service
public class HhOauthServiceImpl implements HhOauthService {

    private final OAuth20Service service = null;

//    @Value("${oauth.clientId}")
//    private String oauthClientId;
//
//    @Value("${oauth.clientSecret}")
//    private String oauthClientSecret;
//
//    private String authUrl;

    @Autowired
    public HhOauthServiceImpl(Environment env) {
//        service = new ServiceBuilder()
//                .apiKey(env.getProperty("clientId"))
//                .apiSecret(env.getProperty("clientSecret"))
//                .callback(env.getProperty("redirectURI"))
//                .grantType("authorization_code")
//                .build(HHApi.instance());
//
//        authUrl = service.getAuthorizationUrl();
    }

    @Override
    public void authorizationForUser(User user) {
        // TODO
    }

    @Override
    public void refreshToken(User user){
        // TODO
    }

    @Override
    public boolean accessTokenIsValid(User user) {
        OAuth2AccessToken accessToken = new OAuth2AccessToken(user.getClientAccessToken().getAccessToken());
        OAuthRequest request = new OAuthRequest(Verb.GET, "https://api.hh.ru/me", service);
        service.signRequest(accessToken, request);
        Response response = request.send();
        return response.getCode() == 200;
    }
}
