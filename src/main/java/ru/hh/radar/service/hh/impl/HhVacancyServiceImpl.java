package ru.hh.radar.service.hh.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.hh.radar.model.SearchParameters;
import ru.hh.radar.dto.VacanciesSearchResultsDTO;
import ru.hh.radar.dto.VacancyDTO;
import ru.hh.radar.service.hh.HhVacancyService;

import java.net.URI;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class HhVacancyServiceImpl implements HhVacancyService {

    @Value("${headhunter.api.url}")
    private String url;

    private final RestTemplate restTemplate = new RestTemplate();

    @Cacheable("vacancy")
    @Override
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

    @Override
    public VacanciesSearchResultsDTO getVacancies(SearchParameters searchParameters) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(url)
                .path("/vacancies");
        URI uri = applySearchParameters(uriComponentsBuilder, searchParameters)
                .build()
                .toUri();
        log.info("search vacancies URI: " + uri);

        RequestEntity<?> request = new RequestEntity<>(HttpMethod.GET, uri);
        ResponseEntity<VacanciesSearchResultsDTO> response = restTemplate.exchange(request, new ParameterizedTypeReference<>() {});
        return response.getBody();
    }

    private UriComponentsBuilder applySearchParameters(
            UriComponentsBuilder uriComponentsBuilder,
            SearchParameters searchParameters
    ) {
        for(Map.Entry<SearchParameters.SearchParam, String> param : searchParameters.get().entrySet()) {
            uriComponentsBuilder = uriComponentsBuilder
                    .queryParam(
                            param.getKey().name().toLowerCase(),
                            param.getValue()
                    );
        }
        return uriComponentsBuilder
                .queryParam("order_by", "publication_time");
    }
}
