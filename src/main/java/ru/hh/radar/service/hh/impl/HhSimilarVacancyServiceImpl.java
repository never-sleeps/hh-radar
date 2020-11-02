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
import ru.hh.radar.dto.ResumeDTO;
import ru.hh.radar.dto.VacanciesResultsDTO;
import ru.hh.radar.dto.VacancyDTO;
import ru.hh.radar.model.entity.SearchParameters;
import ru.hh.radar.model.entity.User;
import ru.hh.radar.service.WebRequestUtils;
import ru.hh.radar.service.hh.HhSimilarVacancyService;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class HhSimilarVacancyServiceImpl implements HhSimilarVacancyService {

    @Value("${headhunter.api.url}")
    private String url;

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public List<VacancyDTO> getSimilarVacancies(ResumeDTO resume, User user) {
        SearchParameters parameters = enrichSearchParameters(user.getSearchParameters(), resume);
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(url)
                .path("/resumes/{resumeId}/similar_vacancies");
        URI uri = WebRequestUtils.applySearchParameters(uriComponentsBuilder, WebRequestUtils.toShortParametersMap(parameters))
                .encode(StandardCharsets.UTF_8)
                .buildAndExpand(resume.getId())
                .toUri();

        HttpHeaders headers = WebRequestUtils.getAuthorizationHttpHeader(user);
        HttpEntity<?> entity = new HttpEntity<Object>("parameters", headers);
        ResponseEntity<VacanciesResultsDTO> response = restTemplate.exchange(uri, HttpMethod.GET, entity, VacanciesResultsDTO.class);

        log.info(response.getBody().getFound() + " found similar vacancies for URI " + uri);
        return response.getBody().getItems();
    }

    private SearchParameters enrichSearchParameters(SearchParameters parameters, ResumeDTO resume) {
        parameters.setText(resume.getTitle());
        parameters.setArea(resume.getArea().getId());
        return parameters;
    }
}
