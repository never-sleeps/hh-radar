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
import ru.hh.radar.dto.ResumeResultsDTO;
import ru.hh.radar.model.entity.User;
import ru.hh.radar.service.Utils;
import ru.hh.radar.service.hh.HhResumeService;

import java.net.URI;

@Slf4j
@RequiredArgsConstructor
@Service
public class HhResumeServiceImpl implements HhResumeService {

    @Value("${headhunter.api.url}")
    private String url;

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public ResumeResultsDTO getHhUserResume(User user) {
        HttpHeaders headers = Utils.getAuthorizationHttpHeader(user);

        URI uri = UriComponentsBuilder.fromHttpUrl(url).path("/resumes/mine").build().toUri();
        HttpEntity<?> entity = new HttpEntity<Object>("parameters", headers);

        ResponseEntity<ResumeResultsDTO> response = restTemplate.exchange(uri, HttpMethod.GET, entity, ResumeResultsDTO.class);
        ResumeResultsDTO results = response.getBody();
        log.info(results.getFound() + " resumes received for: " + user.getUsername());
        return results;
    }
}
