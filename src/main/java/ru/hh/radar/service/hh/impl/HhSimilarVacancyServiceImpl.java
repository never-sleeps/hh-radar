package ru.hh.radar.service.hh.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
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

    private void setUtf8encoding() {
        restTemplate.getMessageConverters()
                .add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
    }

    @Override
    public List<VacancyDTO> getSimilarVacancies(ResumeDTO resume, User user) {
        setUtf8encoding();

        SearchParameters parameters = enrichSearchParameters(user.getSearchParameters(), resume);
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(url)
                .path("/resumes/{resumeId}/similar_vacancies");
        URI uri = WebRequestUtils.applySearchParameters(
                uriComponentsBuilder, WebRequestUtils.toShortParametersMap(parameters)
        ).buildAndExpand(resume.getId()).toUri();

        log.info("search similar vacancies URI: " + uri);

        HttpHeaders headers = WebRequestUtils.getAuthorizationHttpHeader(user);
        HttpEntity<?> entity = new HttpEntity<Object>("parameters", headers);
        ResponseEntity<VacanciesResultsDTO> response = restTemplate.exchange(uri, HttpMethod.GET, entity, VacanciesResultsDTO.class);

        log.info("response url: " + response.getBody().getAlternateUrl());
        return response.getBody().getItems();
    }

    private SearchParameters enrichSearchParameters(SearchParameters parameters, ResumeDTO resume) {
        parameters.setText(resume.getTitle().replaceAll(" ", "-"));
        parameters.setArea(resume.getArea().getId());
        return parameters;
    }
}
