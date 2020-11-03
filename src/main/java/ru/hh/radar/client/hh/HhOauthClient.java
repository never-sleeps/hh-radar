package ru.hh.radar.client.hh;

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
import ru.hh.radar.dto.AccessTokenDTO;

import java.net.URI;

@Slf4j
@Service
@RequiredArgsConstructor
public class HhOauthClient {

    @Value("${headhunter.api.baseUrl}")
    private String baseUrl;

    @Value("${oauth.clientId}")
    private String oauthClientId;

    @Value("${oauth.clientSecret}")
    private String oauthClientSecret;

    @Value("${oauth.redirectUri}")
    private String redirectUri;

    private final RestTemplate restTemplate = new RestTemplate();

    public AccessTokenDTO getAccessTokenForUser(String authorizationCode) {
        URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .path("oauth/token")
                .build()
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("grant_type", "authorization_code");
        map.add("client_id", oauthClientId);
        map.add("client_secret", oauthClientSecret);
        map.add("redirect_uri", redirectUri);
        map.add("code", authorizationCode);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        ResponseEntity<AccessTokenDTO> response = restTemplate.postForEntity(uri, request, AccessTokenDTO.class);

        log.info("Got access_token for authorizationCode: " + authorizationCode);
        return response.getBody();
    }

    public URI getUserAuthorizeURI() {
        return UriComponentsBuilder.fromHttpUrl(baseUrl)
                .path("oauth/authorize")
                .queryParam("response_type", "code")
                .queryParam("client_id", oauthClientId)
                .queryParam("redirect_uri", redirectUri)
                .build()
                .toUri();
    }
}
