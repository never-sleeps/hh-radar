package ru.hh.radar.client.hh;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.hh.radar.dto.ResumeDTO;
import ru.hh.radar.dto.VacanciesResultsDTO;
import ru.hh.radar.dto.VacancyDTO;
import ru.hh.radar.model.entity.SearchParameters;
import ru.hh.radar.model.entity.User;
import ru.hh.radar.utils.WebRequestUtils;

import java.net.URI;
import java.nio.charset.StandardCharsets;

@Slf4j
@Service
@RequiredArgsConstructor
public class HhVacancyClient {

    @Value("${headhunter.api.url}")
    private String url;

    private final RestTemplate restTemplate = new RestTemplate();

    public VacancyDTO getVacancy(Long id) {
        URI uri = UriComponentsBuilder.fromHttpUrl(url)
                .path("/vacancies/{id}")
                .buildAndExpand(id)
                .toUri();

        log.info("search vacancy URI: " + uri);
        RequestEntity<?> request = new RequestEntity<>(HttpMethod.GET, uri);
        ResponseEntity<VacancyDTO> response = restTemplate.exchange(request, new ParameterizedTypeReference<>() {});
        return response.getBody();
    }

    public VacanciesResultsDTO getVacancies(SearchParameters parameters) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(url)
                .path("/vacancies");
        URI uri = WebRequestUtils.applySearchParameters(uriComponentsBuilder, WebRequestUtils.toParametersMap(parameters))
                .encode(StandardCharsets.UTF_8)
                .build()
                .toUri();

        log.info("search vacancy URI: " + uri);
        RequestEntity<?> request = new RequestEntity<>(HttpMethod.GET, uri);
        ResponseEntity<VacanciesResultsDTO> response = restTemplate.exchange(request, new ParameterizedTypeReference<>() {});
        return response.getBody();
    }

    public VacanciesResultsDTO getSimilarVacancies(ResumeDTO resume, User user) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(url)
                .path("/resumes/{resumeId}/similar_vacancies");
        URI uri = WebRequestUtils.applySearchParameters(
                uriComponentsBuilder, WebRequestUtils.toShortParametersMap(user.getSearchParameters())
        )
                .encode(StandardCharsets.UTF_8)
                .buildAndExpand(resume.getId())
                .toUri();

        log.info("search vacancy URI: " + uri);
        HttpHeaders headers = WebRequestUtils.getAuthorizationHttpHeader(user);
        HttpEntity<?> entity = new HttpEntity<Object>("parameters", headers);
        ResponseEntity<VacanciesResultsDTO> response = restTemplate.exchange(uri, HttpMethod.GET, entity, VacanciesResultsDTO.class);
        return response.getBody();
    }
}
