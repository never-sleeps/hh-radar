package ru.hh.radar.service.hh.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.hh.radar.dto.HhUserDTO;
import ru.hh.radar.model.entity.User;
import ru.hh.radar.service.Utils;
import ru.hh.radar.service.hh.HhUserService;

import java.net.URI;

@Slf4j
@RequiredArgsConstructor
@Service
public class HhUserServiceImpl implements HhUserService {

    @Value("${headhunter.api.url}")
    private String url;

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public HhUserDTO getHhUserInfo(User user) {
        HttpHeaders headers = Utils.getAuthorizationHttpHeader(user);

        URI uri = UriComponentsBuilder.fromHttpUrl(url).path("/me").build().toUri();
        HttpEntity<?> entity = new HttpEntity<Object>("parameters", headers);

        ResponseEntity<HhUserDTO> res = restTemplate.exchange(uri, HttpMethod.GET, entity, HhUserDTO.class);
        HhUserDTO authorizedUser = res.getBody();
        log.info(String.format("%s: Authorized user: %s", user.getUsername(), authorizedUser));
        return authorizedUser;
    }
}
