package ru.hh.radar.client.hh;

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
import ru.hh.radar.utils.WebRequestUtils;

import java.net.URI;

@Slf4j
@Service
@RequiredArgsConstructor
public class HhUserClient {

    @Value("${headhunter.api.url}")
    private String url;

    private final RestTemplate restTemplate = new RestTemplate();

    public HhUserDTO getHhUserInfo(User user) {
        URI uri = UriComponentsBuilder.fromHttpUrl(url)
                .path("/me")
                .build()
                .toUri();

        HttpHeaders headers = WebRequestUtils.getAuthorizationHttpHeader(user);
        HttpEntity<?> entity = new HttpEntity<Object>("parameters", headers);
        ResponseEntity<HhUserDTO> res = restTemplate.exchange(uri, HttpMethod.GET, entity, HhUserDTO.class);
        return res.getBody();
    }
}
