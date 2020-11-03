package ru.hh.radar.client.hh;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.hh.radar.dto.ErrorDTO;
import ru.hh.radar.dto.ResumeDTO;
import ru.hh.radar.dto.ResumeStatusDTO;
import ru.hh.radar.dto.ResumesResultsDTO;
import ru.hh.radar.model.entity.User;
import ru.hh.radar.utils.WebRequestUtils;

import java.net.URI;

@Slf4j
@Service
@RequiredArgsConstructor
public class HhResumeClient {

    @Value("${headhunter.api.url}")
    private String url;

    private final RestTemplate restTemplate = new RestTemplate();

    public ResumesResultsDTO getAllResume(User user) {
        URI uri = UriComponentsBuilder.fromHttpUrl(url)
                .path("/resumes/mine")
                .build()
                .toUri();

        HttpHeaders headers = WebRequestUtils.getAuthorizationHttpHeader(user);
        HttpEntity<?> entity = new HttpEntity<Object>("parameters", headers);
        ResponseEntity<ResumesResultsDTO> response = restTemplate.exchange(uri, HttpMethod.GET, entity, ResumesResultsDTO.class);
        return response.getBody();
    }

    public ResumeDTO getResume(String resumeId, User user) {
        URI uri = UriComponentsBuilder.fromHttpUrl(url)
                .path("/resumes/{resume_id}")
                .buildAndExpand(resumeId)
                .toUri();

        HttpHeaders headers = WebRequestUtils.getAuthorizationHttpHeader(user);
        HttpEntity<?> entity = new HttpEntity<Object>("parameters", headers);
        ResponseEntity<ResumeDTO> response = restTemplate.exchange(uri, HttpMethod.GET, entity, ResumeDTO.class);
        return response.getBody();
    }

    public ResumeStatusDTO getStatusResume(String resumeId, User user) {
        URI uri = UriComponentsBuilder.fromHttpUrl(url)
                .path("/resumes/{resume_id}/status")
                .buildAndExpand(resumeId)
                .toUri();

        HttpHeaders headers = WebRequestUtils.getAuthorizationHttpHeader(user);
        HttpEntity<?> entity = new HttpEntity<Object>("parameters", headers);
        ResponseEntity<ResumeStatusDTO> response = restTemplate.exchange(uri, HttpMethod.GET, entity, ResumeStatusDTO.class);
        return response.getBody();
    }

    public boolean publishResume(String resumeId, User user) {
        URI uri = UriComponentsBuilder.fromHttpUrl(url)
                .path("/resumes/{resume_id}/publish")
                .buildAndExpand(resumeId)
                .toUri();

        HttpHeaders headers = WebRequestUtils.getAuthorizationHttpHeader(user);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(null, headers);
        ResponseEntity<ErrorDTO> response = restTemplate.postForEntity(uri, request, ErrorDTO.class);

        boolean is2xxSuccessful = response.getStatusCode().is2xxSuccessful();
        if(!is2xxSuccessful) {
            log.error(
                    String.format("%s: error published resume %s. StatusCode: %s. Cause: %s",
                            user.getUsername(), resumeId, response.getStatusCode(),
                            (response.getBody() != null) ? response.getBody().toString() : "?")
            );
        }
        return is2xxSuccessful;
    }
}
